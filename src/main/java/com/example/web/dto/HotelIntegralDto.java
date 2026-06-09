package com.example.web.dto;
import com.example.web.enums.*;
import com.example.web.tools.dto.BaseDto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.sql.Date;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.example.web.entity.*;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
/**
 * 酒店积分类
 */
@Data
public class HotelIntegralDto extends BaseDto
{

    
     
    /**
     * 标题
     */ 
    @JsonProperty("Title")
    private String Title;
    
     
    /**
     * 关联单号
     */ 
    @JsonProperty("RelativeNo")
    private String RelativeNo;
    
     
    /**
     * 积分值
     */ 
    @JsonProperty("IntegralValue")
    private Double IntegralValue;      
    
     
    /**
     * 用户
     */ 
    @JsonProperty("UserId")
    private Integer UserId;          
    
     
    /**
     * 酒店
     */ 
    @JsonProperty("HotelId")
    private Integer HotelId;          
    
     
    /**
     * 类型
     */ 
    @JsonProperty("Type")
    private String Type;

     @JsonProperty("UserDto") 
    private AppUserDto UserDto;                        
   
     @JsonProperty("HotelDto") 
    private HotelDto HotelDto;                        
   
 	 /**
     * 把酒店积分传输模型转换成酒店积分实体
     */
    public HotelIntegral MapToEntity() throws InvocationTargetException, IllegalAccessException {
        HotelIntegral HotelIntegral= new HotelIntegral();
     
         BeanUtils.copyProperties(HotelIntegral,this);
        
        return HotelIntegral;
    }

}
