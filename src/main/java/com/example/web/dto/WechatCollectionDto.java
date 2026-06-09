package com.example.web.dto;

import com.example.web.entity.WechatCollection;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

/**
 * 会话集合类
 */
@Data
public class WechatCollectionDto extends BaseDto {


    /**
     * 本身用户
     */
    @JsonProperty("SelfUserId")
    private Integer SelfUserId;


    /**
     * 对方用户
     */
    @JsonProperty("OtherUserId")
    private Integer OtherUserId;


    /**
     * 最新消息
     */
    @JsonProperty("LastMessage")
    private String LastMessage;


    /**
     * 最新消息时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("LastTime")
    private LocalDateTime LastTime;

    @JsonProperty("SelfUserDto")
    private AppUserDto SelfUserDto;

    @JsonProperty("OtherUserDto")
    private AppUserDto OtherUserDto;

    /**
     * 把会话集合传输模型转换成会话集合实体
     */
    public WechatCollection MapToEntity() throws InvocationTargetException, IllegalAccessException {
        WechatCollection WechatCollection = new WechatCollection();
        BeanUtils.copyProperties(WechatCollection, this);
        return WechatCollection;
    }

}
