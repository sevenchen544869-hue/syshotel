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
 * 酒店积分表
 */
@Data
@TableName("`HotelIntegral`")
public class HotelIntegral extends BaseEntity {

      
  	  /**
     * 标题
     */  
    @JsonProperty("Title")
    @TableField(value="Title",updateStrategy = FieldStrategy.ALWAYS)
    private String Title;
      
  	  /**
     * 关联单号
     */  
    @JsonProperty("RelativeNo")
    @TableField(value="RelativeNo",updateStrategy = FieldStrategy.ALWAYS)
    private String RelativeNo;
      
    /**
     * 积分值
     */  
    @JsonProperty("IntegralValue")
    @TableField(value="IntegralValue",updateStrategy = FieldStrategy.ALWAYS)
    private Double IntegralValue;      
      
    /**
     * 用户
     */  
    @JsonProperty("UserId")
    @TableField(value="UserId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer UserId;          
      
    /**
     * 酒店
     */  
    @JsonProperty("HotelId")
    @TableField(value="HotelId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer HotelId;          
      
  	  /**
     * 类型
     */  
    @JsonProperty("Type")
    @TableField(value="Type",updateStrategy = FieldStrategy.ALWAYS)
    private String Type;
  
    /**
     * 把酒店积分实体转换成酒店积分传输模型
     */
    public HotelIntegralDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        HotelIntegralDto HotelIntegralDto = new HotelIntegralDto();
       
        BeanUtils.copyProperties(HotelIntegralDto,this);
       
        return HotelIntegralDto;
    }

}
