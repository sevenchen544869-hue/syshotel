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
 * 系统通知类
 */
@Data
public class SysNoticeDto extends BaseDto
{

    
     
    /**
     * 标题
     */ 
    @JsonProperty("Title")
    private String Title;
    
     
    /**
     * 通知内容
     */ 
    @JsonProperty("Content")
    private String Content;
    
     
    /**
     * 是否显示
     */ 
    @JsonProperty("IsPutaway")
    private Boolean IsPutaway;          

 	 /**
     * 把系统通知传输模型转换成系统通知实体
     */
    public SysNotice MapToEntity() throws InvocationTargetException, IllegalAccessException {
        SysNotice SysNotice= new SysNotice();
     
         BeanUtils.copyProperties(SysNotice,this);
        
        return SysNotice;
    }

}
