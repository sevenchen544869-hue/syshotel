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
 * 设备信息查询模型
 */
@NoArgsConstructor
@Data
public class EquipmentInfoPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 名称模糊查询条件
     */
  	 @JsonProperty("Name")
    private String Name;
    /**
     * 分类模糊查询条件
     */
  	 @JsonProperty("GroupName")
    private String GroupName;
     /**
     * 是否免费
     */
  	 @JsonProperty("IsFree")
    private Boolean IsFree;

}
