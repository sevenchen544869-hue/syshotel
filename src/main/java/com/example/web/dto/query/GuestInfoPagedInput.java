package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * 旅客信息查询模型
 */
@NoArgsConstructor
@Data
public class GuestInfoPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 手机号模糊查询条件
     */
  	 @JsonProperty("Phone")
    private String Phone;
    /**
     * 身份证模糊查询条件
     */
  	 @JsonProperty("IdCard")
    private String IdCard;
    /**
     * 姓名模糊查询条件
     */
  	 @JsonProperty("Name")
    private String Name;
    /**
     * 性别模糊查询条件
     */
  	 @JsonProperty("Sex")
    private String Sex;
     /**
     * 所属账号
     */
  	 @JsonProperty("BelongUserId")
    private Integer BelongUserId;

}
