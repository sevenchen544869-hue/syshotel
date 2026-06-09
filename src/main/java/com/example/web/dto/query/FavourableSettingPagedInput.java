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
 * 优惠设置查询模型
 */
@NoArgsConstructor
@Data
public class FavourableSettingPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
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
     * 截至有效时间时间范围
     */
    @JsonProperty("EndActiveTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> EndActiveTimeRange;
    /**
     * 起始有效时间时间范围
     */
    @JsonProperty("BeginActiveTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> BeginActiveTimeRange;

}
