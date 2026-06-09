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
 * 寄存记录查询模型
 */
@NoArgsConstructor
@Data
public class StorageRecordPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 电话模糊查询条件
     */
  	 @JsonProperty("Phone")
    private String Phone;
    /**
     * 房间号模糊查询条件
     */
  	 @JsonProperty("No")
    private String No;
    /**
     * 物品信息模糊查询条件
     */
  	 @JsonProperty("Name")
    private String Name;
    /**
     * 拍照模糊查询条件
     */
  	 @JsonProperty("TakePicture")
    private String TakePicture;
     /**
     * 酒店
     */
  	 @JsonProperty("HotelId")
    private Integer HotelId;
    /**
     * 领取时间时间范围
     */
    @JsonProperty("TakeTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> TakeTimeRange;

    /**
     * 寄存记录用户Id
     */
    @JsonProperty("RecordUserId")
    private Integer RecordUserId;

}
