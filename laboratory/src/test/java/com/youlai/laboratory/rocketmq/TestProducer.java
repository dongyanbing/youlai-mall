package com.youlai.laboratory.rocketmq;

import com.youlai.laboratory.rocketmq.producer.BaseProducer;
import com.youlai.laboratory.rocketmq.producer.tx.TxProducer;
import com.youlai.laboratory.seata.pojo.form.SeataForm;
import org.apache.rocketmq.common.message.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
@SpringBootTest
public class TestProducer {

    @Autowired
    private TxProducer txProducer;

    @Autowired
    private BaseProducer baseProducer;

    @Test
    void txTest() throws InterruptedException {
        SeataForm seataForm = new SeataForm();
        seataForm.setAmount(1L);
        seataForm.setOpenEx(false);
        seataForm.setOpenTx(false);
//        txProducer.tx(seataForm);
        baseProducer.async("1","2","3");
//        Thread.sleep(1000*1000);
    }
}
