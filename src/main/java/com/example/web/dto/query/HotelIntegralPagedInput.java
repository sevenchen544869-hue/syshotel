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
 * 酒店积分查询模型
 */
@NoArgsConstructor
@Data
public class HotelIntegralPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 标题模糊查询条件
     */
  	 @JsonProperty("Title")
    private String Title;
    /**
     * 关联单号模糊查询条件
     */
  	 @JsonProperty("RelativeNo")
    private String RelativeNo;
    /**
     * 类型模糊查询条件
     */
  	 @JsonProperty("Type")
    private String Type;
     /**
     * 用户
     */
  	 @JsonProperty("UserId")
    private Integer UserId;
     /**
     * 酒店
     */
  	 @JsonProperty("HotelId")
    private Integer HotelId;

}
