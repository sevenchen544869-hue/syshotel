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
 * 分配房间查询模型
 */
@NoArgsConstructor
@Data
public class RoomMatchPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
     /**
     * 房间状态
     */
  	 @JsonProperty("RoomStatus")
    private Integer RoomStatus;
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

}
