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
 * 优惠设置类
 */
@Data
public class FavourableSettingDto extends BaseDto
{

    
     
    /**
     * 折扣价
     */ 
    @JsonProperty("Discount")
    private Double Discount;      
    
     
    /**
     * 截至有效时间
     */ 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("EndActiveTime")
    private LocalDateTime EndActiveTime;             
    
     
    /**
     * 酒店
     */ 
    @JsonProperty("HotelId")
    private Integer HotelId;          
    
     
    /**
     * 起始有效时间
     */ 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("BeginActiveTime")
    private LocalDateTime BeginActiveTime;             
    
     
    /**
     * 房间
     */ 
    @JsonProperty("RoomId")
    private Integer RoomId;          

     @JsonProperty("HotelDto") 
    private HotelDto HotelDto;                        
   
     @JsonProperty("RoomDto") 
    private RoomDto RoomDto;                        
   
 	 /**
     * 把优惠设置传输模型转换成优惠设置实体
     */
    public FavourableSetting MapToEntity() throws InvocationTargetException, IllegalAccessException {
        FavourableSetting FavourableSetting= new FavourableSetting();
     
         BeanUtils.copyProperties(FavourableSetting,this);
        
        return FavourableSetting;
    }

}
