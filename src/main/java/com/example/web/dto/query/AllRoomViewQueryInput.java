package com.example.web.dto.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AllRoomViewQueryInput {
    /**
     * 酒店Id
     */
    @JsonProperty("HotelId")
    private Integer HotelId;

    /**
     * 起始预约时间时间范围
     */
    @JsonProperty("BeginDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime BeginDate;

    /**
     * 截至预约时间时间范围
     */
    @JsonProperty("EndDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime EndDate;

    /**
     * 是否显示预约单号
     */
    @JsonProperty("AppointId")
    private Integer AppointId;
}
