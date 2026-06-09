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
 * 预约记录表
 */
@Data
@TableName("`Appoint`")
public class Appoint extends BaseEntity {

      
    /**
     * 起始预约时间
     */  
    @JsonProperty("BeginAppointTime")
    @TableField(value="BeginAppointTime",updateStrategy = FieldStrategy.ALWAYS)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime BeginAppointTime;             
      
  	  /**
     * 编号
     */  
    @JsonProperty("No")
    @TableField(value="No",updateStrategy = FieldStrategy.ALWAYS)
    private String No;
      
  	  /**
     * 评价
     */  
    @JsonProperty("Comment")
    @TableField(value="Comment",updateStrategy = FieldStrategy.ALWAYS)
    private String Comment;
      
    /**
     * 退款金额
     */  
    @JsonProperty("ReturnMoney")
    @TableField(value="ReturnMoney",updateStrategy = FieldStrategy.ALWAYS)
    private Double ReturnMoney;      
      
    /**
     * 总人数
     */  
    @JsonProperty("TotalPeopleNum")
    @TableField(value="TotalPeopleNum",updateStrategy = FieldStrategy.ALWAYS)
    private Integer TotalPeopleNum;          
      
    /**
     * 酒店
     */  
    @JsonProperty("HotelId")
    @TableField(value="HotelId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer HotelId;          
      
    /**
     * 预约状态
     */  
    @JsonProperty("AppointStatus")
    @TableField(value="AppointStatus",updateStrategy = FieldStrategy.ALWAYS)
    private Integer AppointStatus;          
      
    /**
     * 房间
     */  
    @JsonProperty("RoomId")
    @TableField(value="RoomId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer RoomId;        
    
    /**
     * 实际入住时间
     */
    @JsonProperty("CheckInTime")
    @TableField(value="CheckInTime",updateStrategy = FieldStrategy.ALWAYS)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime CheckInTime;
    /**
     * 实际退房时间
     */
    @JsonProperty("CheckOutTime")
    @TableField(value="CheckOutTime",updateStrategy = FieldStrategy.ALWAYS)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime CheckOutTime;
      
    /**
     * 数量
     */  
    @JsonProperty("Qty")
    @TableField(value="Qty",updateStrategy = FieldStrategy.ALWAYS)
    private Integer Qty;          
      
    /**
     * 支付时间
     */  
    @JsonProperty("PayTime")
    @TableField(value="PayTime",updateStrategy = FieldStrategy.ALWAYS)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime PayTime;             
      
    /**
     * 房间明细
     */  
    @JsonProperty("RoomDetId")
    @TableField(value="RoomDetId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer RoomDetId;          
      
    /**
     * 截至预约时间
     */  
    @JsonProperty("EndAppointTime")
    @TableField(value="EndAppointTime",updateStrategy = FieldStrategy.ALWAYS)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime EndAppointTime;             
      
  	  /**
     * 支付方式
     */  
    @JsonProperty("PayType")
    @TableField(value="PayType",updateStrategy = FieldStrategy.ALWAYS)
    private String PayType;
      
    /**
     * 评分
     */  
    @JsonProperty("CommentScore")
    @TableField(value="CommentScore",updateStrategy = FieldStrategy.ALWAYS)
    private Double CommentScore;      
      
    /**
     * 下单账号
     */  
    @JsonProperty("ToUserId")
    @TableField(value="ToUserId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer ToUserId;          
      
    /**
     * 总金额
     */  
    @JsonProperty("TotalMoney")
    @TableField(value="TotalMoney",updateStrategy = FieldStrategy.ALWAYS)
    private Double TotalMoney;      
  
    /**
     * 把预约记录实体转换成预约记录传输模型
     */
    public AppointDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        AppointDto AppointDto = new AppointDto();
       
        BeanUtils.copyProperties(AppointDto,this);
       
        return AppointDto;
    }

}
