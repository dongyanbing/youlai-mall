package com.youlai.laboratory.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zc
 * @date 2022-10-27 00:56
 */
@Service
@Slf4j
public class BaseProducer {
    /**
     * 测试发送将参数topic定死，实际开发写入到配置文件
     */
    @Resource
    RocketMQTemplate rocketMQTemplate;

    /**
     * 这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知。
     */
    public boolean sync(String message1, String message2, String message3) {
        boolean flag = true;
        String text1 = "发送消息1:" + message1;
        log.info(text1);
        SendResult sendResult1 = rocketMQTemplate.syncSend("base_topic", text1);
        if(!SendStatus.SEND_OK.equals(sendResult1.getSendStatus())){
          flag = false;
        }
        log.info("同步响应1:"+sendResult1.getSendStatus().toString());
        String text2 = "发送消息2:" + message2;
        log.info(text2);
        SendResult sendResult2 = rocketMQTemplate.syncSend("base_topic", text2);
        if(!SendStatus.SEND_OK.equals(sendResult2.getSendStatus())){
            flag = false;
        }
        log.info("同步响应2:"+sendResult2.getSendStatus().toString());
        String text3 = "发送消息3:" + message3;
        log.info(text3);
        SendResult sendResult3 = rocketMQTemplate.syncSend("base_topic", text3);
        if(!SendStatus.SEND_OK.equals(sendResult3.getSendStatus())){
            flag = false;
        }
        log.info("同步响应3:"+sendResult3.getSendStatus().toString());
        return  flag;
    }

    /**
     * 异步消息通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应。
     */
    public boolean async(String message1, String message2, String message3) {
        String text1 = "发送消息1:" + message1;
        log.info(text1);
        rocketMQTemplate.asyncSend("base_topic", text1 , new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("消息1异步响应发送成功");
            }
            @Override
            public void onException(Throwable throwable) {
                log.info("消息1异步响应发送失败");
            }
        });

        String text2 = "发送消息2:" + message2;
        log.info(text2);
        rocketMQTemplate.asyncSend("base_topic", text2 , new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("消息2异步响应发送成功");
            }
            @Override
            public void onException(Throwable throwable) {
                log.info("消息2异步响应发送失败");
            }
        });

        String text3 = "发送消息3:" + message3;
        log.info(text3);
        rocketMQTemplate.asyncSend("base_topic", text3 , new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("消息3异步响应发送成功");
            }
            @Override
            public void onException(Throwable throwable) {
                log.info("消息3异步响应发送失败");
            }
        });


        return true;
    }

    /**
     * 这种方式主要用在不特别关心发送结果的场景，例如日志发送。
     */
    public boolean oneWay(String message1, String message2, String message3) {
        String text1 = "发送消息1:" + message1;
        log.info(text1);
        rocketMQTemplate.sendOneWay("base_topic", text1);
        log.info("单向发送-已发送1...");
        String text2 = "发送消息2:" + message2;
        log.info(text2);
        rocketMQTemplate.sendOneWay("base_topic", text2);
        log.info("单向发送-已发送2...");
        String text3 = "发送消息3:" + message3;
        log.info(text3);
        rocketMQTemplate.sendOneWay("base_topic", text3);
        log.info("单向发送-已发送3...");
        return true;
    }

}
