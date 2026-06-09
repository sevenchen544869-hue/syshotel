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
 * 员工查询模型
 */
@NoArgsConstructor
@Data
public class EmployeePagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 地址模糊查询条件
     */
  	 @JsonProperty("Address")
    private String Address;
    /**
     * 工号模糊查询条件
     */
  	 @JsonProperty("No")
    private String No;
    /**
     * 性别模糊查询条件
     */
  	 @JsonProperty("Sex")
    private String Sex;
    /**
     * 薪资模糊查询条件
     */
  	 @JsonProperty("Salary")
    private String Salary;
    /**
     * 身份证模糊查询条件
     */
  	 @JsonProperty("IdCard")
    private String IdCard;
    /**
     * 电话模糊查询条件
     */
  	 @JsonProperty("Phone")
    private String Phone;
    /**
     * 姓名模糊查询条件
     */
  	 @JsonProperty("Name")
    private String Name;
     /**
     * 酒店
     */
  	 @JsonProperty("HotelId")
    private Integer HotelId;
     /**
     * 关联账号
     */
  	 @JsonProperty("UserId")
    private Integer UserId;

}
