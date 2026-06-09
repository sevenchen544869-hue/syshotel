package com.example.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RoomViewDateDto {
    @JsonProperty("Date")
    private LocalDateTime Date;

    @JsonProperty("RoomViews")
    private List<RoomViewDto> RoomViews;
}
