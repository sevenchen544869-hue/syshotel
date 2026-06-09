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
 * 酒店表
 */
@Data
@TableName("`Hotel`")
public class Hotel extends BaseEntity {

      
    /**
     * 酒店负责人
     */  
    @JsonProperty("HotelUserId")
    @TableField(value="HotelUserId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer HotelUserId;          
      
  	  /**
     * 名称
     */  
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.ALWAYS)
    private String Name;
      
  	  /**
     * 入住提示
     */  
    @JsonProperty("CheckInTip")
    @TableField(value="CheckInTip",updateStrategy = FieldStrategy.ALWAYS)
    private String CheckInTip;
      
  	  /**
     * 酒店详细地址
     */  
    @JsonProperty("Address")
    @TableField(value="Address",updateStrategy = FieldStrategy.ALWAYS)
    private String Address;
      
    /**
     * 纬度
     */  
    @JsonProperty("Latitude")
    @TableField(value="Latitude",updateStrategy = FieldStrategy.ALWAYS)
    private Double Latitude;      
      
  	  /**
     * 必读
     */  
    @JsonProperty("MustRead")
    @TableField(value="MustRead",updateStrategy = FieldStrategy.ALWAYS)
    private String MustRead;
      
    /**
     * 酒店介绍
     */  
    @JsonProperty("Content")
     @TableField(value="Content",updateStrategy = FieldStrategy.ALWAYS)
    private String Content;
      
  	  /**
     * 酒店封面
     */  
    @JsonProperty("Cover")
    @TableField(value="Cover",updateStrategy = FieldStrategy.ALWAYS)
    private String Cover;
      
    /**
     * 是否可带宠物
     */  
    @JsonProperty("IsPet")
    @TableField(value="IsPet",updateStrategy = FieldStrategy.ALWAYS)
    private Boolean IsPet;          
      
  	  /**
     * 酒店图片
     */  
    @JsonProperty("HotelImages")
    @TableField(value="HotelImages",updateStrategy = FieldStrategy.ALWAYS)
    private String HotelImages;
      
  	  /**
     * 服务设施
     */  
    @JsonProperty("Services")
    @TableField(value="Services",updateStrategy = FieldStrategy.ALWAYS)
    private String Services;
      
  	  /**
     * 省市区
     */  
    @JsonProperty("ProviceCityArea")
    @TableField(value="ProviceCityArea",updateStrategy = FieldStrategy.ALWAYS)
    private String ProviceCityArea;
      
  	  /**
     * 酒店通知
     */  
    @JsonProperty("Notice")
    @TableField(value="Notice",updateStrategy = FieldStrategy.ALWAYS)
    private String Notice;
      
    /**
     * 经度
     */  
    @JsonProperty("Longitude")
    @TableField(value="Longitude",updateStrategy = FieldStrategy.ALWAYS)
    private Double Longitude;   

    /**
     * 是否审核通过
     * @return
     */
    @JsonProperty("IsAudit")
    @TableField(value="IsAudit",updateStrategy = FieldStrategy.IGNORED)
    private Boolean IsAudit;          
      
    /**
     * 审核结果
     */
    @JsonProperty("AuditResult")
    @TableField(value="AuditResult",updateStrategy = FieldStrategy.IGNORED)
    private String AuditResult;      
  
    /**
     * 把酒店实体转换成酒店传输模型
     */
    public HotelDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        HotelDto HotelDto = new HotelDto();
       
        BeanUtils.copyProperties(HotelDto,this);
       
        return HotelDto;
    }

}
