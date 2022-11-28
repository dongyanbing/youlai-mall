package com.youlai.laboratory.rocketmq.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;


import com.youlai.common.result.Result;
import com.youlai.laboratory.rocketmq.pojo.MessageVO;
import com.youlai.laboratory.rocketmq.pojo.form.BaseAsyncForm;
import com.youlai.laboratory.rocketmq.pojo.form.BaseOnewayForm;
import com.youlai.laboratory.rocketmq.pojo.form.BaseSyncForm;
import com.youlai.laboratory.rocketmq.pojo.form.OrderForm;
import com.youlai.laboratory.rocketmq.producer.*;
import com.youlai.laboratory.rocketmq.producer.tx.TxProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zc
 * @date 2022-10-27 01:01
 */

@Api(tags = "[实验室]RocketMQ接口")
@RestController
@RequestMapping("/api/v1/rocketmq")
public class RocketMQController {

    /**
     * 搭建测试流程生成者
     */
    @Resource
    private TestProducer testProducer;

    /**
     * 基本信息案例
     */
    @Resource
    private BaseProducer baseProducer;

    /**
     * 顺序消息发送样例
     */
    @Resource
    private OrderProducer orderProducer;

    /**
     * 延时消息
     */
    @Resource
    private ScheduledProducer scheduledProducer;

    /**
     * 指标标签
     */
    @Resource
    private TagProducer tagProducer;

    /**
     * 过滤消息
     */
    @Resource
    private SQLProducer SQLProducer;

    /**
     * 消息事务
     */
    @Resource
    private TxProducer txProducer;

    /**
     * 消息额外属性测试
     */
    @Resource
    private ExProducer exProducer;

    /**
     * 回馈消息样例
     */
    @Resource
    private ReplyProducer replyProducer;

    /**
     * 批量消息发送
     */
    @Resource
    private BatchProducer batchProducer;

    @ApiOperation(value = "测试消息生成及发送", notes = "测试消息生成及发送")
    @ApiOperationSupport(order = 5)
    @GetMapping
    public Object test() {
        testProducer.send();
        return "测试消息生成及发送";
    }


    @ApiOperation(value = "发送普通同步消息", notes = "发送普通同步消息")
    @ApiOperationSupport(order = 10)
    @PostMapping("/base/sync")
    public Result baseSync(@RequestBody BaseSyncForm baseSyncForm) {
        boolean bool = baseProducer.sync(baseSyncForm.getMessage1(), baseSyncForm.getMessage2(), baseSyncForm.getMessage3());
        return Result.success(bool?"发送成功":"发送失败");
    }

    @ApiOperation(value = "发送普通异步消息", notes = "发送普通异步消息")
    @ApiOperationSupport(order = 11)
    @PostMapping("/base/async")
    public Result baseAsync(@RequestBody BaseAsyncForm baseAsyncForm) {
        boolean bool =baseProducer.async(baseAsyncForm.getMessage1(),baseAsyncForm.getMessage2(),baseAsyncForm.getMessage3());
        return Result.success(bool?"发送成功":"发送失败");
    }

    @ApiOperation(value = "单向发送普通消息", notes = "单向发送普通消息")
    @ApiOperationSupport(order = 11)
    @PostMapping("/base/oneway")
    public Result baseOneway(@RequestBody BaseOnewayForm baseOnewayForm) {
        boolean bool =baseProducer.oneWay(baseOnewayForm.getMessage1(),baseOnewayForm.getMessage2(),baseOnewayForm.getMessage3());
        return Result.success(bool?"发送成功":"发送失败");
    }

    @ApiOperation(value = "发送顺序消息", notes = "发送顺序消息")
    @ApiOperationSupport(order = 15)
    @PostMapping("/order")
    public Result order(@RequestBody OrderForm orderForm) {
        orderProducer.order(orderForm.getCreate(),orderForm.getPay(),orderForm.getDeliver());
        return Result.success("发送成功");
    }


    @ApiOperation(value = "发送延时消息", notes = "发送延时消息")
    @ApiOperationSupport(order = 20)
    @PostMapping("/scheduled")
    public Result scheduled(@RequestBody MessageVO messageVO) {
        scheduledProducer.scheduled();
        return Result.success("发送成功");
    }

    @ApiOperation(value = "标签过滤消息样例", notes = "标签过滤消息样例")
    @ApiOperationSupport(order = 25)
    @PostMapping("/tag")
    public Result tag(@RequestBody MessageVO messageVO) {
        // TAG过滤
        tagProducer.tag();
        return Result.success("发送成功");
    }

    @ApiOperation(value = "SQL92过滤消息样例", notes = "SQL92过滤消息样例")
    @ApiOperationSupport(order = 30)
    @PostMapping("/selector")
    public Result selector(@RequestBody MessageVO messageVO) {
        // SQL92过滤
        SQLProducer.selector();
        return Result.success("发送成功");
    }

    @ApiOperation(value = "消息事务样例", notes = "消息事务样例")
    @ApiOperationSupport(order = 35)
    @PostMapping("/tx")
    public Result tx(@RequestBody MessageVO messageVO) {
        // 消息事务
        txProducer.tx();
        return Result.success("发送成功");
    }


    @ApiOperation(value = "消息额外属性", notes = "消息额外属性")
    @ApiOperationSupport(order = 40)
    @PostMapping("/ex")
    public Result ex(@RequestBody MessageVO messageVO) {
        // 消息事务
        exProducer.ex();
        return Result.success("发送成功");
    }

    @ApiOperation(value = "回馈消息样例", notes = "回馈消息样例")
    @ApiOperationSupport(order = 40)
    @PostMapping("/reply")
    public Result reply(@RequestBody MessageVO messageVO) {
        // 消息事务
        replyProducer.reply();
        return Result.success("发送成功");
    }

    @ApiOperation(value = "批量消息样例", notes = "批量消息样例")
    @ApiOperationSupport(order = 45)
    @PostMapping("/batch")
    public Result batch(@RequestBody MessageVO messageVO) {
        // 批量消息样例
        batchProducer.batch();
        return Result.success("发送成功");
    }
}

