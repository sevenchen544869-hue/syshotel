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
 * 房间价格表
 */
@Data
@TableName("`RoomPrice`")
public class RoomPrice extends BaseEntity {

      
    /**
     * 起始有效时间
     */  
    @JsonProperty("BeginActiveTime")
    @TableField(value="BeginActiveTime",updateStrategy = FieldStrategy.ALWAYS)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime BeginActiveTime;             
      
    /**
     * 价格
     */  
    @JsonProperty("Price")
    @TableField(value="Price",updateStrategy = FieldStrategy.ALWAYS)
    private Double Price;      
      
    /**
     * 截至有效时间
     */  
    @JsonProperty("EndActiveTime")
    @TableField(value="EndActiveTime",updateStrategy = FieldStrategy.ALWAYS)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime EndActiveTime;             
      
    /**
     * 房间
     */  
    @JsonProperty("RoomId")
    @TableField(value="RoomId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer RoomId;          
      
    /**
     * 酒店
     */  
    @JsonProperty("HotelId")
    @TableField(value="HotelId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer HotelId;          
  
    /**
     * 把房间价格实体转换成房间价格传输模型
     */
    public RoomPriceDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        RoomPriceDto RoomPriceDto = new RoomPriceDto();
       
        BeanUtils.copyProperties(RoomPriceDto,this);
       
        return RoomPriceDto;
    }

}
