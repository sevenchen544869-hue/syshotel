package com.example.web.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 房间试图
 */
@Data
public class RoomViewDto {
    
    @JsonProperty("Floor")
    private String Floor;

    
    @JsonProperty("RoomDetViews")
    private List<RoomDetViewDto> RoomDetViews;
}
