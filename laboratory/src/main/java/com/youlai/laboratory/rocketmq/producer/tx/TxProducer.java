package com.youlai.laboratory.rocketmq.producer.tx;

import com.youlai.laboratory.seata.pojo.form.SeataForm;
import com.youlai.mall.oms.api.OrderFeignClient;
import com.youlai.mall.oms.dto.SeataOrderDTO;
import com.youlai.mall.pms.api.SkuFeignClient;
import com.youlai.mall.ums.api.MemberFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author zc
 * @date 2022-10-27 00:56
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TxProducer {

    private final SkuFeignClient skuFeignClient;
    private final OrderFeignClient orderFeignClient;
    private final MemberFeignClient memberFeignClient;


    private static Long skuId = 1l; // SkuID
    private static Long memberId = 1l; // 会员ID
    private static Long orderId = 1l;// 订单ID



    /**
     * 测试发送将参数topic定死，实际开发写入到配置文件
     * 一个RocketMQTemplate只能注册一个事务监听器，
     * 如果存在多个事务监听器监听不同的`Producer`，
     * 需要通过注解`@ExtRocketMQTemplateConfiguration`定义不同的RocketMQTemplate
     */
    @Resource
    RocketMQTemplate rocketMQTemplate;

    public void tx(SeataForm seataForm) {
        SeataOrderDTO seataOrderDTO = new SeataOrderDTO(
                memberId,
                skuId,
                seataForm.getAmount(),
                seataForm.isOpenEx()
        );
        Message<Long> message =  MessageBuilder.withPayload(skuId)
                // 设置事务Id
                .setHeader(RocketMQHeaders.TRANSACTION_ID,orderId)
                .build();
        rocketMQTemplate.sendMessageInTransaction("pms:deductStock", message, seataOrderDTO);
    }
}
