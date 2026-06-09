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
 * 预约记录查询模型
 */
@NoArgsConstructor
@Data
public class AppointPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 编号模糊查询条件
     */
  	 @JsonProperty("No")
    private String No;
    /**
     * 评价模糊查询条件
     */
  	 @JsonProperty("Comment")
    private String Comment;
    /**
     * 支付方式模糊查询条件
     */
  	 @JsonProperty("PayType")
    private String PayType;
     /**
     * 酒店
     */
  	 @JsonProperty("HotelId")
    private Integer HotelId;
     /**
     * 预约状态
     */
  	 @JsonProperty("AppointStatus")
    private Integer AppointStatus;
     /**
     * 房间
     */
  	 @JsonProperty("RoomId")
    private Integer RoomId;
     /**
     * 房间明细
     */
  	 @JsonProperty("RoomDetId")
    private Integer RoomDetId;
     /**
     * 下单账号
     */
  	 @JsonProperty("ToUserId")
    private Integer ToUserId;
    /**
     * 起始预约时间时间范围
     */
    @JsonProperty("BeginAppointTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> BeginAppointTimeRange;
    /**
     * 支付时间时间范围
     */
    @JsonProperty("PayTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> PayTimeRange;
    /**
     * 截至预约时间时间范围
     */
    @JsonProperty("EndAppointTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> EndAppointTimeRange;

}
