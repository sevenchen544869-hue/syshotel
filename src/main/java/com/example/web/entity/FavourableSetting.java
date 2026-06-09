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
 * 优惠设置表
 */
@Data
@TableName("`FavourableSetting`")
public class FavourableSetting extends BaseEntity {

      
    /**
     * 折扣价
     */  
    @JsonProperty("Discount")
    @TableField(value="Discount",updateStrategy = FieldStrategy.ALWAYS)
    private Double Discount;      
      
    /**
     * 截至有效时间
     */  
    @JsonProperty("EndActiveTime")
    @TableField(value="EndActiveTime",updateStrategy = FieldStrategy.ALWAYS)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime EndActiveTime;             
      
    /**
     * 酒店
     */  
    @JsonProperty("HotelId")
    @TableField(value="HotelId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer HotelId;          
      
    /**
     * 起始有效时间
     */  
    @JsonProperty("BeginActiveTime")
    @TableField(value="BeginActiveTime",updateStrategy = FieldStrategy.ALWAYS)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime BeginActiveTime;             
      
    /**
     * 房间
     */  
    @JsonProperty("RoomId")
    @TableField(value="RoomId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer RoomId;          
  
    /**
     * 把优惠设置实体转换成优惠设置传输模型
     */
    public FavourableSettingDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        FavourableSettingDto FavourableSettingDto = new FavourableSettingDto();
       
        BeanUtils.copyProperties(FavourableSettingDto,this);
       
        return FavourableSettingDto;
    }

}
