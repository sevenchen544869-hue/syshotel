package com.example.web.dto.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HotelDataAnalysisQueryInput {

    /**
     * 酒店ID
     */
    @JsonProperty("HotelId")

    private Integer HotelId;

    /**
     * 查询开始时间
     */
    @JsonProperty("StartTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime StartTime;

    /**
     * 查询结束时间
     */
    @JsonProperty("EndTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime EndTime;



}
