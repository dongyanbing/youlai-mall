package com.youlai.laboratory.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author zc
 * @date 2022-11-29 06:37
 */
@Component
@RocketMQMessageListener( consumeMode= ConsumeMode.ORDERLY, topic = "order_topic", consumerGroup = "order_group")
@Slf4j
public class OrderConsumer implements RocketMQListener<String> {

    /**
     * 测试接收将参数topic定死，实际开发写入到配置文件
     *
     * @param message
     */
    @Override
    public void onMessage(String message) {
        log.info("接收到顺序消息:" + message);
    }
}

