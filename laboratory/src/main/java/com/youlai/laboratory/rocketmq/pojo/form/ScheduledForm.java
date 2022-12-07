package com.youlai.laboratory.rocketmq.pojo.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zc
 * @date 2022-12-08 05:15
 */
@ApiModel("延迟消息对象")
@Data
public class ScheduledForm {

    @ApiModelProperty("延时等级[1-18]")
    private Integer delayLevel;

    @ApiModelProperty("消息内容")
    private String body;
}