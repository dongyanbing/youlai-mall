package com.youlai.laboratory.rocketmq.producer;

/**
 * @author zc
 * @date 2022-10-27 00:57
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class BatchProducer {
    /**
     * 测试发送将参数topic定死，实际开发写入到配置文件
     */
    @Resource
    RocketMQTemplate rocketMQTemplate;

    public void batch(String message1,String message2,String message3,String message4,String message5) {
        List<Message> messageList = new ArrayList<>();
        messageList.add(MessageBuilder.withPayload(message1).build());
        messageList.add(MessageBuilder.withPayload(message2).build());
        messageList.add(MessageBuilder.withPayload(message3).build());
        messageList.add(MessageBuilder.withPayload(message4).build());
        messageList.add(MessageBuilder.withPayload(message5).build());
        log.info("开始发送...");
        SendResult result = rocketMQTemplate.syncSend("batch_topic", messageList);
        log.info("已发送...");
    }
}

