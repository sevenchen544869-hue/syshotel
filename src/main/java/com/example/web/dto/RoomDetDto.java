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
 * 房间明细类
 */
@Data
public class RoomDetDto extends BaseDto
{

    
     
    /**
     * 酒店
     */ 
    @JsonProperty("HotelId")
    private Integer HotelId;          
    
     
    /**
     * 楼层
     */ 
    @JsonProperty("Floor")
    private String Floor;
    
     
    /**
     * 房号
     */ 
    @JsonProperty("No")
    private String No;
    
     
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
     * 把房间明细传输模型转换成房间明细实体
     */
    public RoomDet MapToEntity() throws InvocationTargetException, IllegalAccessException {
        RoomDet RoomDet= new RoomDet();
     
         BeanUtils.copyProperties(RoomDet,this);
        
        return RoomDet;
    }

}
