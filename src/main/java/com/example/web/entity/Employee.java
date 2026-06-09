package com.example.web.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.sql.Date;
import java.sql.Timestamp;
import lombok.Data;
import java.time.LocalDateTime;
import com.example.web.dto.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
/**
 * 员工表
 */
@Data
@TableName("`Employee`")
public class Employee extends BaseEntity {

      
  	  /**
     * 地址
     */  
    @JsonProperty("Address")
    @TableField(value="Address",updateStrategy = FieldStrategy.ALWAYS)
    private String Address;
      
  	  /**
     * 工号
     */  
    @JsonProperty("No")
    @TableField(value="No",updateStrategy = FieldStrategy.ALWAYS)
    private String No;
      
  	  /**
     * 性别
     */  
    @JsonProperty("Sex")
    @TableField(value="Sex",updateStrategy = FieldStrategy.ALWAYS)
    private String Sex;
      
  	  /**
     * 薪资
     */  
    @JsonProperty("Salary")
    @TableField(value="Salary",updateStrategy = FieldStrategy.ALWAYS)
    private String Salary;
      
    /**
     * 酒店
     */  
    @JsonProperty("HotelId")
    @TableField(value="HotelId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer HotelId;          
      
  	  /**
     * 照片
     */  
    @JsonProperty("Photo")
    @TableField(value="Photo",updateStrategy = FieldStrategy.ALWAYS)
    private String Photo;
      
    /**
     * 出生年月
     */  
    @JsonProperty("BirthDay")
    @TableField(value="BirthDay",updateStrategy = FieldStrategy.ALWAYS)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime BirthDay;             
      
    /**
     * 关联账号
     */  
    @JsonProperty("UserId")
    @TableField(value="UserId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer UserId;          
      
  	  /**
     * 身份证
     */  
    @JsonProperty("IdCard")
    @TableField(value="IdCard",updateStrategy = FieldStrategy.ALWAYS)
    private String IdCard;
      
  	  /**
     * 电话
     */  
    @JsonProperty("Phone")
    @TableField(value="Phone",updateStrategy = FieldStrategy.ALWAYS)
    private String Phone;
      
  	  /**
     * 姓名
     */  
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.ALWAYS)
    private String Name;
  
    /**
     * 把员工实体转换成员工传输模型
     */
    public EmployeeDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        EmployeeDto EmployeeDto = new EmployeeDto();
       
        BeanUtils.copyProperties(EmployeeDto,this);
       
        return EmployeeDto;
    }

}
