package com.youlai.laboratory.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author zc
 * @date 2022-10-27 00:59
 */
@Service
@Slf4j
public class OrderProducer {
    /**
     * 测试发送将参数topic定死，实际开发写入到配置文件
     */
    @Resource
    RocketMQTemplate rocketMQTemplate;

    private String topic = "order_topic";
    public boolean order(String create, String pay, String deliver) {
        boolean flag = true;
        try {
            TimeUnit.MILLISECONDS.sleep(50);

        String text1 = "发送订单生成消息:" + create;
        log.info(text1);
        SendResult sendResult1 = rocketMQTemplate.syncSendOrderly(topic, text1,"order");
        if(!SendStatus.SEND_OK.equals(sendResult1.getSendStatus())){
            flag = false;
        }

        TimeUnit.MILLISECONDS.sleep(50);
        String text2 = "发送支付消息:" + pay;
        log.info(text2);
        SendResult sendResult2 = rocketMQTemplate.syncSendOrderly(topic, text2,"order");
        if(!SendStatus.SEND_OK.equals(sendResult2.getSendStatus())){
            flag = false;
        }
        TimeUnit.MILLISECONDS.sleep(50);
        String text3 = "发送发货消息:" + deliver;
        log.info(text3);
        SendResult sendResult3 = rocketMQTemplate.syncSendOrderly(topic, text3,"order");
        if(!SendStatus.SEND_OK.equals(sendResult3.getSendStatus())){
            flag = false;
        }
        return  flag;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

