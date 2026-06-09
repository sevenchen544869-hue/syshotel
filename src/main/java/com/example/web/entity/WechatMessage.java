package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.WechatMessageDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

/**
 * 会话消息表
 */
@Data
@TableName("`WechatMessage`")
public class WechatMessage extends BaseEntity {

    /**
     * 自身用户
     */
    @JsonProperty("SelfUserId")
    @TableField(value = "SelfUserId", updateStrategy = FieldStrategy.IGNORED)
    private Integer SelfUserId;

    /**
     * 对方用户
     */
    @JsonProperty("OtherUserId")
    @TableField(value = "OtherUserId", updateStrategy = FieldStrategy.IGNORED)
    private Integer OtherUserId;

    /**
     * 消息内容
     */
    @JsonProperty("Content")
    @TableField(value = "Content", updateStrategy = FieldStrategy.IGNORED)
    private String Content;

    /**
     * 发送时间
     */
    @JsonProperty("SendTime")
    @TableField(value = "SendTime", updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime SendTime;

    /**
     * 消息类型
     */
    @JsonProperty("WechatMessageType")
    @TableField(value = "WechatMessageType", updateStrategy = FieldStrategy.IGNORED)
    private Integer WechatMessageType;

    /**
     * 文件路径
     */
    @JsonProperty("FilePath")
    @TableField(value = "FilePath", updateStrategy = FieldStrategy.IGNORED)
    private String FilePath;

    /**
     * 把会话消息实体转换成会话消息传输模型
     */
    public WechatMessageDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        WechatMessageDto WechatMessageDto = new WechatMessageDto();
        BeanUtils.copyProperties(WechatMessageDto, this);
        return WechatMessageDto;
    }

}
