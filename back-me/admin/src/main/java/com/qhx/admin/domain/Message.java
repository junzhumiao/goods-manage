package com.qhx.admin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author qhx2004
 * @since 2024-03-11
 */
@Getter
@Setter
@TableName("ap_message")
@ApiModel(value = "Message对象", description = "")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("消息id")
    @TableId(value = "message_id", type = IdType.AUTO)
    private Integer messageId;

    @ApiModelProperty("消息发送者id")
    private Long senderId;

    @ApiModelProperty("消息接受者(一般农户id)")
    private Long receiverId;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("发送这条消息的时间")
    private LocalDateTime senderTime;

    @ApiModelProperty("消息接受时间")
    private LocalDateTime receiverTime;

    @ApiModelProperty("状态（0正常 1停用）")
    private String status;

    @ApiModelProperty("删除标志（0代表存在 1代表删除）")
    private String delFlag;
}
