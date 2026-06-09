package com.example.web.dto;
import com.example.web.enums.*;
import com.example.web.tools.dto.BaseDto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.sql.Date;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.example.web.entity.*;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
/**
 * 设备信息类
 */
@Data
public class EquipmentInfoDto extends BaseDto
{

    
     
    /**
     * 名称
     */ 
    @JsonProperty("Name")
    private String Name;
    
     
    /**
     * 显示顺序
     */ 
    @JsonProperty("Sort")
    private Integer Sort;          
    
     
    /**
     * 分类
     */ 
    @JsonProperty("GroupName")
    private String GroupName;
    
     
    /**
     * 是否免费
     */ 
    @JsonProperty("IsFree")
    private Boolean IsFree;          

 	 /**
     * 把设备信息传输模型转换成设备信息实体
     */
    public EquipmentInfo MapToEntity() throws InvocationTargetException, IllegalAccessException {
        EquipmentInfo EquipmentInfo= new EquipmentInfo();
     
         BeanUtils.copyProperties(EquipmentInfo,this);
        
        return EquipmentInfo;
    }

}
