package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * 房间查询模型
 */
@NoArgsConstructor
@Data
public class RoomPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 设施信息模糊查询条件
     */
  	 @JsonProperty("EquipmentInfoIds")
    private String EquipmentInfoIds;
    /**
     * 标题模糊查询条件
     */
  	 @JsonProperty("Title")
    private String Title;
    /**
     * 酒店名称模糊查询条件
     */
    @JsonProperty("HotelName")
    private String HotelName;
     /**
     * 特色房型
     */
  	 @JsonProperty("FeaturedRoomShape")
    private Integer FeaturedRoomShape;
     /**
     * 酒店
     */
  	 @JsonProperty("HotelId")
    private Integer HotelId;
     /**
     * 早餐类型
     */
  	 @JsonProperty("BreakfastType")
    private Integer BreakfastType;
     /**
     * 房型
     */
  	 @JsonProperty("RoomShape")
    private Integer RoomShape;
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
     * 选择日期列表
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("SelectDateList")
    private List<LocalDateTime> SelectDateList;

    /**
     * 最高价格
     */
    @JsonProperty("MaxPrice")
    private Double MaxPrice;
    /**
     * 最低价格
     */
    @JsonProperty("MinPrice")
    private Double MinPrice;
    
    /**
     * 全局地址
     */
    @JsonProperty("GlobalAddress")
    private String GlobalAddress;
}
