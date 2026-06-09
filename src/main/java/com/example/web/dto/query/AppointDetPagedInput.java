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
 * 预约明细查询模型
 */
@NoArgsConstructor
@Data
public class AppointDetPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 性别模糊查询条件
     */
  	 @JsonProperty("Sex")
    private String Sex;
    /**
     * 姓名模糊查询条件
     */
  	 @JsonProperty("Name")
    private String Name;
    /**
     * 年龄模糊查询条件
     */
  	 @JsonProperty("Age")
    private String Age;
    /**
     * 电话模糊查询条件
     */
  	 @JsonProperty("Phone")
    private String Phone;
    /**
     * 身份证模糊查询条件
     */
  	 @JsonProperty("IdCard")
    private String IdCard;
     /**
     * 预约订单
     */
  	 @JsonProperty("AppointId")
    private Integer AppointId;

}
