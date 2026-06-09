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
 * 旅客信息类
 */
@Data
public class GuestInfoDto extends BaseDto
{

    
     
    /**
     * 所属账号
     */ 
    @JsonProperty("BelongUserId")
    private Integer BelongUserId;          
    
     
    /**
     * 出生年月
     */ 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("Birth")
    private LocalDateTime Birth;             
    
     
    /**
     * 手机号
     */ 
    @JsonProperty("Phone")
    private String Phone;
    
     
    /**
     * 身份证
     */ 
    @JsonProperty("IdCard")
    private String IdCard;
    
     
    /**
     * 姓名
     */ 
    @JsonProperty("Name")
    private String Name;
    
     
    /**
     * 性别
     */ 
    @JsonProperty("Sex")
    private String Sex;

     @JsonProperty("BelongUserDto") 
    private AppUserDto BelongUserDto;                        
   
 	 /**
     * 把旅客信息传输模型转换成旅客信息实体
     */
    public GuestInfo MapToEntity() throws InvocationTargetException, IllegalAccessException {
        GuestInfo GuestInfo= new GuestInfo();
     
         BeanUtils.copyProperties(GuestInfo,this);
        
        return GuestInfo;
    }

}
