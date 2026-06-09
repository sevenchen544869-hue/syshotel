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
 * 分配房间类
 */
@Data
public class RoomMatchDto extends BaseDto
{

    
     
    /**
     * 房间状态
     */ 
    @JsonProperty("RoomStatus")
    private Integer RoomStatus;    
    
    public String getRoomStatusFormat() {
        return RoomStatusEnum.GetEnum(RoomStatus).toString();
    }
    
    private String RoomStatusFormat;
    
    
     
    /**
     * 酒店
     */ 
    @JsonProperty("HotelId")
    private Integer HotelId;          
    
     
    /**
     * 房间
     */ 
    @JsonProperty("RoomId")
    private Integer RoomId;          
    
     
    /**
     * 关联房号
     */ 
    @JsonProperty("RoomDetId")
    private Integer RoomDetId;          
    
     
    /**
     * 订单
     */ 
    @JsonProperty("AppointId")
    private Integer AppointId;          

     @JsonProperty("AppointDto") 
    private AppointDto AppointDto;                        
   
     @JsonProperty("RoomDetDto") 
    private RoomDetDto RoomDetDto;                        
   
     @JsonProperty("RoomDto") 
    private RoomDto RoomDto;                        
   
     @JsonProperty("HotelDto") 
    private HotelDto HotelDto;         
    
    /**
     * 开始激活时间
     */
    @JsonProperty("BeginActiveTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime BeginActiveTime;          

    /**
     * 结束激活时间
     */
    @JsonProperty("EndActiveTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime EndActiveTime;  
   
 	 /**
     * 把分配房间传输模型转换成分配房间实体
     */
    public RoomMatch MapToEntity() throws InvocationTargetException, IllegalAccessException {
        RoomMatch RoomMatch= new RoomMatch();
     
         BeanUtils.copyProperties(RoomMatch,this);
        
        return RoomMatch;
    }

}
