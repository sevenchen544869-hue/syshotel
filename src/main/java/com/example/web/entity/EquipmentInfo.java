package com.example.web.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.sql.Date;
import java.sql.Timestamp;
import lombok.Data;
import java.time.LocalDateTime;
import com.example.web.dto.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
/**
 * 设备信息表
 */
@Data
@TableName("`EquipmentInfo`")
public class EquipmentInfo extends BaseEntity {

      
  	  /**
     * 名称
     */  
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.ALWAYS)
    private String Name;
      
    /**
     * 显示顺序
     */  
    @JsonProperty("Sort")
    @TableField(value="Sort",updateStrategy = FieldStrategy.ALWAYS)
    private Integer Sort;          
      
  	  /**
     * 分类
     */  
    @JsonProperty("GroupName")
    @TableField(value="GroupName",updateStrategy = FieldStrategy.ALWAYS)
    private String GroupName;
      
    /**
     * 是否免费
     */  
    @JsonProperty("IsFree")
    @TableField(value="IsFree",updateStrategy = FieldStrategy.ALWAYS)
    private Boolean IsFree;          
  
    /**
     * 把设备信息实体转换成设备信息传输模型
     */
    public EquipmentInfoDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        EquipmentInfoDto EquipmentInfoDto = new EquipmentInfoDto();
       
        BeanUtils.copyProperties(EquipmentInfoDto,this);
       
        return EquipmentInfoDto;
    }

}
