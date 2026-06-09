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
 * 房间明细查询模型
 */
@NoArgsConstructor
@Data
public class RoomDetPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 楼层模糊查询条件
     */
  	 @JsonProperty("Floor")
    private String Floor;
    /**
     * 房号模糊查询条件
     */
  	 @JsonProperty("No")
    private String No;
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

}
