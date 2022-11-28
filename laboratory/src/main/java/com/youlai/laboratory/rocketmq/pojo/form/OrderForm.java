package com.youlai.laboratory.rocketmq.pojo.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zc
 * @date 2022-11-29 06:55
 */
@ApiModel("顺序消息对象")
@Data
public class OrderForm {

    @ApiModelProperty("生成订单消息")
    private String create;

    @ApiModelProperty("付款消息")
    private String pay;

    @ApiModelProperty("发货消息")
    private String deliver;
}
