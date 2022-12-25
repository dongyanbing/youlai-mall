package com.youlai.laboratory.rocketmq.producer.tx;

import cn.hutool.core.bean.BeanUtil;
import com.youlai.common.result.Result;
import com.youlai.laboratory.seata.pojo.vo.SeataVO;
import com.youlai.mall.oms.api.OrderFeignClient;
import com.youlai.mall.oms.dto.OrderInfoDTO;
import com.youlai.mall.oms.dto.SeataOrderDTO;
import com.youlai.mall.oms.enums.OrderStatusEnum;
import com.youlai.mall.pms.api.SkuFeignClient;
import com.youlai.mall.pms.pojo.dto.SkuInfoDTO;
import com.youlai.mall.ums.api.MemberFeignClient;
import com.youlai.mall.ums.dto.MemberInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zc
 * @date 2022-10-27 00:56
 */
@Slf4j
@RocketMQTransactionListener
@RequiredArgsConstructor
public class TxProducerListener implements RocketMQLocalTransactionListener {

    private final OrderFeignClient orderFeignClient;
    /**
     * 记录各个事务Id的状态:1-正在执行，2-执行成功，3-执行失败
     */
    private ConcurrentHashMap<String, Integer> transMap = new ConcurrentHashMap<>();

    /**
     * 执行本地事务
     *
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        long orderId =Long.parseLong(msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID).toString());
        try{
            Result<String> result = orderFeignClient.payOrder(orderId, (SeataOrderDTO) arg);
            log.info("支付成功,扣减库存");
            return RocketMQLocalTransactionState.COMMIT;
        }catch (Exception e){
            log.info("支付失败,取消扣减库存");
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }


    /**
     * 事务超时，回查方法
     * 检查本地事务,如果RocketMQ长时间(1分钟左右)没有收到本地事务的返回结果，则会定时主动执行改方法，查询本地事务执行情况。
     *
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        long orderId =Long.parseLong(msg.getHeaders().get(RocketMQHeaders.TRANSACTION_ID).toString());
        OrderInfoDTO orderInfoDTO = orderFeignClient.getOrderInfo(orderId).getData();
        if(orderInfoDTO.getStatus().equals(OrderStatusEnum.WAIT_SHIPPING.getValue())){
            return RocketMQLocalTransactionState.COMMIT;
        }else if(orderInfoDTO.getStatus().equals(OrderStatusEnum.WAIT_PAY.getValue())){
           return RocketMQLocalTransactionState.UNKNOWN;
        }else{
          return RocketMQLocalTransactionState.ROLLBACK;
        }

    }
}
