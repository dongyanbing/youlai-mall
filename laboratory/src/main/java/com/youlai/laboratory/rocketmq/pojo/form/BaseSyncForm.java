package com.youlai.laboratory.rocketmq.pojo.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zc
 * @date 2022-11-29 04:55
 */
@ApiModel("普通同步消息对象")
@Data
public class BaseSyncForm {

    @ApiModelProperty("消息1")
    private String message1;

    @ApiModelProperty("消息2")
    private String message2;

    @ApiModelProperty("消息3")
    private String message3;
}
