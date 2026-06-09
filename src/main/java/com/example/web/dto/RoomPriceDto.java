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
 * 房间价格类
 */
@Data
public class RoomPriceDto extends BaseDto
{

    
     
    /**
     * 起始有效时间
     */ 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("BeginActiveTime")
    private LocalDateTime BeginActiveTime;             
    
     
    /**
     * 价格
     */ 
    @JsonProperty("Price")
    private Double Price;      
    
     
    /**
     * 截至有效时间
     */ 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("EndActiveTime")
    private LocalDateTime EndActiveTime;             
    
     
    /**
     * 房间
     */ 
    @JsonProperty("RoomId")
    private Integer RoomId;          
    
     
    /**
     * 酒店
     */ 
    @JsonProperty("HotelId")
    private Integer HotelId;          

     @JsonProperty("HotelDto") 
    private HotelDto HotelDto;                        
   
     @JsonProperty("RoomDto") 
    private RoomDto RoomDto;                        
   
 	 /**
     * 把房间价格传输模型转换成房间价格实体
     */
    public RoomPrice MapToEntity() throws InvocationTargetException, IllegalAccessException {
        RoomPrice RoomPrice= new RoomPrice();
     
         BeanUtils.copyProperties(RoomPrice,this);
        
        return RoomPrice;
    }

}
