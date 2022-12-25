package com.youlai.mall.pms.laboratory;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.service.IPmsSkuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author zc
 * @date 2022-12-25 21:11
 */
@Component
@RocketMQMessageListener(topic = "pms", consumerGroup = "pms",selectorExpression = "deductStock")
@Slf4j
@RequiredArgsConstructor
public class RocketmqListener implements RocketMQListener<Long> {

    private final IPmsSkuService skuService;
    /**
     * 扣减库存(实验室)
     * @param s
     */
    @Override
    public void onMessage(Long s) {
        boolean result = skuService.deductStock(s, 1);
        log.info("扣减库存");
    }
}

