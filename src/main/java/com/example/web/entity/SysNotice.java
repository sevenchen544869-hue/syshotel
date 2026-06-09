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
 * 系统通知表
 */
@Data
@TableName("`SysNotice`")
public class SysNotice extends BaseEntity {

      
  	  /**
     * 标题
     */  
    @JsonProperty("Title")
    @TableField(value="Title",updateStrategy = FieldStrategy.ALWAYS)
    private String Title;
      
    /**
     * 通知内容
     */  
    @JsonProperty("Content")
     @TableField(value="Content",updateStrategy = FieldStrategy.ALWAYS)
    private String Content;
      
    /**
     * 是否显示
     */  
    @JsonProperty("IsPutaway")
    @TableField(value="IsPutaway",updateStrategy = FieldStrategy.ALWAYS)
    private Boolean IsPutaway;          
  
    /**
     * 把系统通知实体转换成系统通知传输模型
     */
    public SysNoticeDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        SysNoticeDto SysNoticeDto = new SysNoticeDto();
       
        BeanUtils.copyProperties(SysNoticeDto,this);
       
        return SysNoticeDto;
    }

}
