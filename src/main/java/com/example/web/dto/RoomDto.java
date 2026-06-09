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
 * 房间类
 */
@Data
public class RoomDto extends BaseDto
{

    
     
    /**
     * 最小价格
     */ 
    @JsonProperty("MinPrice")
    private Double MinPrice;      
    
     
    /**
     * 可抵积分
     */ 
    @JsonProperty("Integral")
    private Double Integral;      
    
     
    /**
     * 房间大小
     */ 
    @JsonProperty("AreaSize")
    private Double AreaSize;      
    
     
    /**
     * 特色房型
     */ 
    @JsonProperty("FeaturedRoomShape")
    private Integer FeaturedRoomShape;    
    
    public String getFeaturedRoomShapeFormat() {
        return FeaturedRoomShapeEnum.GetEnum(FeaturedRoomShape).toString();
    }
    
    private String FeaturedRoomShapeFormat;
    
    
     
    /**
     * 酒店
     */ 
    @JsonProperty("HotelId")
    private Integer HotelId;          
    
     
    /**
     * 设施信息
     */ 
    @JsonProperty("EquipmentInfoIds")
    private String EquipmentInfoIds;
    
    /**
     * 设施信息列表
     */
    @JsonProperty("EquipmentInfoDtos")
    private List<EquipmentInfoDto> EquipmentInfoDtos;


    /**
     * 是否免费取消
     */ 
    @JsonProperty("IsFreeCancel")
    private Boolean IsFreeCancel;          
    
     
    /**
     * 是否立即确定
     */ 
    @JsonProperty("IsImmediatelyConfirm")
    private Boolean IsImmediatelyConfirm;          
    
     
    /**
     * 积分可抵金额
     */ 
    @JsonProperty("IntegralExchangeMoney")
    private Double IntegralExchangeMoney;      
    
     
    /**
     * 早餐类型
     */ 
    @JsonProperty("BreakfastType")
    private Integer BreakfastType;    
    
    public String getBreakfastTypeFormat() {
        return BreakfastTypeEnum.GetEnum(BreakfastType).toString();
    }
    
    private String BreakfastTypeFormat;
    
    
     
    /**
     * 最大价格
     */ 
    @JsonProperty("MaxPrice")
    private Double MaxPrice;      
    
     
    /**
     * 标题
     */ 
    @JsonProperty("Title")
    private String Title;
    
     
    /**
     * 房间主图
     */ 
    @JsonProperty("ImageUrls")
    private String ImageUrls;
    
     
    /**
     * 封面
     */ 
    @JsonProperty("Cover")
    private String Cover;
    
     
    /**
     * 房间介绍
     */ 
    @JsonProperty("Content")
    private String Content;
    
     
    /**
     * 房型
     */ 
    @JsonProperty("RoomShape")
    private Integer RoomShape;    
    
    public String getRoomShapeFormat() {
        return RoomShapeEnum.GetEnum(RoomShape).toString();
    }
    
    private String RoomShapeFormat;
    

     @JsonProperty("HotelDto") 
    private HotelDto HotelDto;        
    
    /**
     * 房间的户数
     * @return
     */
    @JsonProperty("RoomNumber")
    private Long RoomNumber;

          
      @JsonProperty("FavourableSettingDtos")
      private List<FavourableSettingDto> FavourableSettingDtos;
     
        @JsonProperty("RoomPriceDtos")
        private List<RoomPriceDto> RoomPriceDtos;

    /**
     * 实际价格
     */
    @JsonProperty("ActualPrice")
    private Double ActualPrice;

    /**
     * 原始价格
     */
    @JsonProperty("OriginalPrice")
    private Double OriginalPrice;

    /**
     * 剩余房间数
     */
    @JsonProperty("RemainingRoomNumber")
    private Long RemainingRoomNumber;

 	 /**
     * 把房间传输模型转换成房间实体
     */
    public Room MapToEntity() throws InvocationTargetException, IllegalAccessException {
        Room Room= new Room();
     
         BeanUtils.copyProperties(Room,this);
        
        return Room;
    }

}
