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
 * 旅客信息表
 */
@Data
@TableName("`GuestInfo`")
public class GuestInfo extends BaseEntity {

      
    /**
     * 所属账号
     */  
    @JsonProperty("BelongUserId")
    @TableField(value="BelongUserId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer BelongUserId;          
      
    /**
     * 出生年月
     */  
    @JsonProperty("Birth")
    @TableField(value="Birth",updateStrategy = FieldStrategy.ALWAYS)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime Birth;             
      
  	  /**
     * 手机号
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
     * 姓名
     */  
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.ALWAYS)
    private String Name;
      
  	  /**
     * 性别
     */  
    @JsonProperty("Sex")
    @TableField(value="Sex",updateStrategy = FieldStrategy.ALWAYS)
    private String Sex;
  
    /**
     * 把旅客信息实体转换成旅客信息传输模型
     */
    public GuestInfoDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        GuestInfoDto GuestInfoDto = new GuestInfoDto();
       
        BeanUtils.copyProperties(GuestInfoDto,this);
       
        return GuestInfoDto;
    }

}
