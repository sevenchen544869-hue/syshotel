package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WechatCollection查询模型
 */
@NoArgsConstructor
@Data
public class WechatCollectionPagedInput extends PagedInput {
    
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


}
