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
 * 房间表
 */
@Data
@TableName("`Room`")
public class Room extends BaseEntity {

      
    /**
     * 最小价格
     */  
    @JsonProperty("MinPrice")
    @TableField(value="MinPrice",updateStrategy = FieldStrategy.ALWAYS)
    private Double MinPrice;      
      
    /**
     * 可抵积分
     */  
    @JsonProperty("Integral")
    @TableField(value="Integral",updateStrategy = FieldStrategy.ALWAYS)
    private Double Integral;      
      
    /**
     * 房间大小
     */  
    @JsonProperty("AreaSize")
    @TableField(value="AreaSize",updateStrategy = FieldStrategy.ALWAYS)
    private Double AreaSize;      
      
    /**
     * 特色房型
     */  
    @JsonProperty("FeaturedRoomShape")
    @TableField(value="FeaturedRoomShape",updateStrategy = FieldStrategy.ALWAYS)
    private Integer FeaturedRoomShape;          
      
    /**
     * 酒店
     */  
    @JsonProperty("HotelId")
    @TableField(value="HotelId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer HotelId;          
      
  	  /**
     * 设施信息
     */  
    @JsonProperty("EquipmentInfoIds")
    @TableField(value="EquipmentInfoIds",updateStrategy = FieldStrategy.ALWAYS)
    private String EquipmentInfoIds;
      
    /**
     * 是否免费取消
     */  
    @JsonProperty("IsFreeCancel")
    @TableField(value="IsFreeCancel",updateStrategy = FieldStrategy.ALWAYS)
    private Boolean IsFreeCancel;          
      
    /**
     * 是否立即确定
     */  
    @JsonProperty("IsImmediatelyConfirm")
    @TableField(value="IsImmediatelyConfirm",updateStrategy = FieldStrategy.ALWAYS)
    private Boolean IsImmediatelyConfirm;          
      
    /**
     * 积分可抵金额
     */  
    @JsonProperty("IntegralExchangeMoney")
    @TableField(value="IntegralExchangeMoney",updateStrategy = FieldStrategy.ALWAYS)
    private Double IntegralExchangeMoney;      
      
    /**
     * 早餐类型
     */  
    @JsonProperty("BreakfastType")
    @TableField(value="BreakfastType",updateStrategy = FieldStrategy.ALWAYS)
    private Integer BreakfastType;          
      
    /**
     * 最大价格
     */  
    @JsonProperty("MaxPrice")
    @TableField(value="MaxPrice",updateStrategy = FieldStrategy.ALWAYS)
    private Double MaxPrice;      
      
  	  /**
     * 标题
     */  
    @JsonProperty("Title")
    @TableField(value="Title",updateStrategy = FieldStrategy.ALWAYS)
    private String Title;
      
  	  /**
     * 房间主图
     */  
    @JsonProperty("ImageUrls")
    @TableField(value="ImageUrls",updateStrategy = FieldStrategy.ALWAYS)
    private String ImageUrls;
      
  	  /**
     * 封面
     */  
    @JsonProperty("Cover")
    @TableField(value="Cover",updateStrategy = FieldStrategy.ALWAYS)
    private String Cover;
      
    /**
     * 房间介绍
     */  
    @JsonProperty("Content")
     @TableField(value="Content",updateStrategy = FieldStrategy.ALWAYS)
    private String Content;
      
    /**
     * 房型
     */  
    @JsonProperty("RoomShape")
    @TableField(value="RoomShape",updateStrategy = FieldStrategy.ALWAYS)
    private Integer RoomShape;          
  
           


    
    /**
     * 把房间实体转换成房间传输模型
     */
    public RoomDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        RoomDto RoomDto = new RoomDto();
       
        BeanUtils.copyProperties(RoomDto,this);
       
        return RoomDto;
    }

}
