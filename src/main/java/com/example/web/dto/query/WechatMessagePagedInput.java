package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WechatMessage查询模型
 */
@NoArgsConstructor
@Data
public class WechatMessagePagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;

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
     * 互相的聊天消息
     */
    @JsonProperty("IsEach")
    private Boolean IsEach;



}
