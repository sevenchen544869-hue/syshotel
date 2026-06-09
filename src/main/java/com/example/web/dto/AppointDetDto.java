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
 * 预约明细类
 */
@Data
public class AppointDetDto extends BaseDto
{

    
     
    /**
     * 性别
     */ 
    @JsonProperty("Sex")
    private String Sex;
    
     
    /**
     * 预约订单
     */ 
    @JsonProperty("AppointId")
    private Integer AppointId;          
    
     
    /**
     * 姓名
     */ 
    @JsonProperty("Name")
    private String Name;
    
     
    /**
     * 年龄
     */ 
    @JsonProperty("Age")
    private String Age;
    
     
    /**
     * 电话
     */ 
    @JsonProperty("Phone")
    private String Phone;
    
     
    /**
     * 身份证
     */ 
    @JsonProperty("IdCard")
    private String IdCard;

     @JsonProperty("AppointDto") 
    private AppointDto AppointDto;                        
   
 	 /**
     * 把预约明细传输模型转换成预约明细实体
     */
    public AppointDet MapToEntity() throws InvocationTargetException, IllegalAccessException {
        AppointDet AppointDet= new AppointDet();
     
         BeanUtils.copyProperties(AppointDet,this);
        
        return AppointDet;
    }

}
