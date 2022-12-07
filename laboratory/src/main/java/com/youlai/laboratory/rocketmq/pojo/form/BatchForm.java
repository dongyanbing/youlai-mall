package com.youlai.laboratory.rocketmq.pojo.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zc
 * @date 2022-12-08 06:05
 */
@ApiModel("批量消息")
@Data
public class BatchForm {

    @ApiModelProperty("消息1")
    private String message1;

    @ApiModelProperty("消息2")
    private String message2;

    @ApiModelProperty("消息3")
    private String message3;

    @ApiModelProperty("消息4")
    private String message4;

    @ApiModelProperty("消息5")
    private String message5;
}
