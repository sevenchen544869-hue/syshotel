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
 * 预约明细表
 */
@Data
@TableName("`AppointDet`")
public class AppointDet extends BaseEntity {

      
  	  /**
     * 性别
     */  
    @JsonProperty("Sex")
    @TableField(value="Sex",updateStrategy = FieldStrategy.ALWAYS)
    private String Sex;
      
    /**
     * 预约订单
     */  
    @JsonProperty("AppointId")
    @TableField(value="AppointId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer AppointId;          
      
  	  /**
     * 姓名
     */  
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.ALWAYS)
    private String Name;
      
  	  /**
     * 年龄
     */  
    @JsonProperty("Age")
    @TableField(value="Age",updateStrategy = FieldStrategy.ALWAYS)
    private String Age;
      
  	  /**
     * 电话
     */  
    @JsonProperty("Phone")
    @TableField(value="Phone",updateStrategy = FieldStrategy.ALWAYS)
    private String Phone;
      
  	  /**
     * 身份证
     */  
    @JsonProperty("IdCard")
    @TableField(value="IdCard",updateStrategy = FieldStrategy.ALWAYS)
    private String IdCard;
  
    /**
     * 把预约明细实体转换成预约明细传输模型
     */
    public AppointDetDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        AppointDetDto AppointDetDto = new AppointDetDto();
       
        BeanUtils.copyProperties(AppointDetDto,this);
       
        return AppointDetDto;
    }

}
