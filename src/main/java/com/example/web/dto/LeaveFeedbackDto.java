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
 * 留言反馈类
 */
@Data
public class LeaveFeedbackDto extends BaseDto
{

    
     
    /**
     * 反馈内容
     */ 
    @JsonProperty("Content")
    private String Content;
    
     
    /**
     * 反馈标题
     */ 
    @JsonProperty("Title")
    private String Title;
    
     
    /**
     * 处理人
     */ 
    @JsonProperty("DoWithUserId")
    private Integer DoWithUserId;          
    
     
    /**
     * 回复内容
     */ 
    @JsonProperty("ReplayContent")
    private String ReplayContent;
    
     
    /**
     * 酒店
     */ 
    @JsonProperty("HotelId")
    private Integer HotelId;          
    
     
    /**
     * 是否处理
     */ 
    @JsonProperty("IsDoWith")
    private Boolean IsDoWith;          
    
     
    /**
     * 反馈人
     */ 
    @JsonProperty("FeedbackUserId")
    private Integer FeedbackUserId;          

     @JsonProperty("FeedbackUserDto") 
    private AppUserDto FeedbackUserDto;                        
   
     @JsonProperty("DoWithUserDto") 
    private AppUserDto DoWithUserDto;                        
   
     @JsonProperty("HotelDto") 
    private HotelDto HotelDto;                        
   
 	 /**
     * 把留言反馈传输模型转换成留言反馈实体
     */
    public LeaveFeedback MapToEntity() throws InvocationTargetException, IllegalAccessException {
        LeaveFeedback LeaveFeedback= new LeaveFeedback();
     
         BeanUtils.copyProperties(LeaveFeedback,this);
        
        return LeaveFeedback;
    }

}
