package com.youlai.laboratory.rocketmq.ext;

/**
 * @author zc
 * @date 2023-04-15 12:31
 */
import org.apache.rocketmq.spring.annotation.ExtRocketMQTemplateConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

@ExtRocketMQTemplateConfiguration
public class TxRocketMQTemplate extends RocketMQTemplate {

}

