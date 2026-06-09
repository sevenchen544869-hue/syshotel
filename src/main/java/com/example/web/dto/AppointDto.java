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
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
/**
 * 预约记录类
 */
@Data
public class AppointDto extends BaseDto
{

    
     
    /**
     * 起始预约时间
     */ 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("BeginAppointTime")
    private LocalDateTime BeginAppointTime;             
    
     
    /**
     * 编号
     */ 
    @JsonProperty("No")
    private String No;
    
     
    /**
     * 评价
     */ 
    @JsonProperty("Comment")
    private String Comment;
    
     
    /**
     * 退款金额
     */ 
    @JsonProperty("ReturnMoney")
    private Double ReturnMoney;      
    
     
    /**
     * 总人数
     */ 
    @JsonProperty("TotalPeopleNum")
    private Integer TotalPeopleNum;          
    
     
    /**
     * 酒店
     */ 
    @JsonProperty("HotelId")
    private Integer HotelId;          
    
     
    /**
     * 预约状态
     */ 
    @JsonProperty("AppointStatus")
    private Integer AppointStatus;    
    
    public String getAppointStatusFormat() {
        return AppointStatusEnum.GetEnum(AppointStatus).toString();
    }
    
    private String AppointStatusFormat;
    
    
     
    /**
     * 房间
     */ 
    @JsonProperty("RoomId")
    private Integer RoomId;          
    
     
    /**
     * 数量
     */ 
    @JsonProperty("Qty")
    private Integer Qty;          
    
     
    /**
     * 支付时间
     */ 
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("PayTime")
    private LocalDateTime PayTime;     
    
    /**
     * 实际入住时间
     */
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("CheckInTime")
    private LocalDateTime CheckInTime;
    /**
     * 实际退房时间
     */
    @JsonProperty("CheckOutTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime CheckOutTime;
    
     
    /**
     * 房间明细
     */ 
    @JsonProperty("RoomDetId")
    private Integer RoomDetId;          
    
     
    /**
     * 截至预约时间
     */ 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("EndAppointTime")
    private LocalDateTime EndAppointTime;             
    
     
    /**
     * 支付方式
     */ 
    @JsonProperty("PayType")
    private String PayType;
    
     
    /**
     * 评分
     */ 
    @JsonProperty("CommentScore")
    private Double CommentScore;      
    
     
    /**
     * 下单账号
     */ 
    @JsonProperty("ToUserId")
    private Integer ToUserId;          
    
     
    /**
     * 总金额
     */ 
    @JsonProperty("TotalMoney")
    private Double TotalMoney;      

     @JsonProperty("ToUserDto") 
    private AppUserDto ToUserDto;                        
   
     @JsonProperty("RoomDetDto") 
    private RoomDetDto RoomDetDto;                        
   
     @JsonProperty("RoomDto") 
    private RoomDto RoomDto;                        
   
     @JsonProperty("HotelDto") 
    private HotelDto HotelDto;      
    
    @JsonProperty("AppointDetDtos") 
    private List<AppointDetDto> AppointDetDtos;     
    
    @JsonProperty("GuestIds") 
    private List<Integer> GuestIds;  
    
    //房间匹配
    @JsonProperty("RoomMatchDtos") 
    private List<RoomMatchDto> RoomMatchDtos;

    //是否积分抵扣
    @JsonProperty("UseIntegral")
    private Boolean UseIntegral;


 	 /**
     * 把预约记录传输模型转换成预约记录实体
     */
    public Appoint MapToEntity() throws InvocationTargetException, IllegalAccessException {
        Appoint Appoint= new Appoint();
     
         BeanUtils.copyProperties(Appoint,this);
        
        return Appoint;
    }

}
