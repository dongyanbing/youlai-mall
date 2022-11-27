package com.youlai.laboratory.rocketmq.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zc
 * @date 2022-11-28 02:12
 */
@Data
public class MessageVO {
    @ApiModelProperty("消息类型")
    private Long type;
    @ApiModelProperty("消息内容")
    private String body;
}
