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
 * 留言反馈表
 */
@Data
@TableName("`LeaveFeedback`")
public class LeaveFeedback extends BaseEntity {

      
    /**
     * 反馈内容
     */  
    @JsonProperty("Content")
     @TableField(value="Content",updateStrategy = FieldStrategy.ALWAYS)
    private String Content;
      
  	  /**
     * 反馈标题
     */  
    @JsonProperty("Title")
    @TableField(value="Title",updateStrategy = FieldStrategy.ALWAYS)
    private String Title;
      
    /**
     * 处理人
     */  
    @JsonProperty("DoWithUserId")
    @TableField(value="DoWithUserId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer DoWithUserId;          
      
    /**
     * 回复内容
     */  
    @JsonProperty("ReplayContent")
     @TableField(value="ReplayContent",updateStrategy = FieldStrategy.ALWAYS)
    private String ReplayContent;
      
    /**
     * 酒店
     */  
    @JsonProperty("HotelId")
    @TableField(value="HotelId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer HotelId;          
      
    /**
     * 是否处理
     */  
    @JsonProperty("IsDoWith")
    @TableField(value="IsDoWith",updateStrategy = FieldStrategy.ALWAYS)
    private Boolean IsDoWith;          
      
    /**
     * 反馈人
     */  
    @JsonProperty("FeedbackUserId")
    @TableField(value="FeedbackUserId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer FeedbackUserId;          
  
    /**
     * 把留言反馈实体转换成留言反馈传输模型
     */
    public LeaveFeedbackDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        LeaveFeedbackDto LeaveFeedbackDto = new LeaveFeedbackDto();
       
        BeanUtils.copyProperties(LeaveFeedbackDto,this);
       
        return LeaveFeedbackDto;
    }

}
