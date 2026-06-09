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
 * 分配房间表
 */
@Data
@TableName("`RoomMatch`")
public class RoomMatch extends BaseEntity {

      
    /**
     * 房间状态
     */  
    @JsonProperty("RoomStatus")
    @TableField(value="RoomStatus",updateStrategy = FieldStrategy.ALWAYS)
    private Integer RoomStatus;          
      
    /**
     * 酒店
     */  
    @JsonProperty("HotelId")
    @TableField(value="HotelId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer HotelId;          
      
    /**
     * 房间
     */  
    @JsonProperty("RoomId")
    @TableField(value="RoomId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer RoomId;          

    /**
     * 开始激活时间
     */
    @JsonProperty("BeginActiveTime")
    @TableField(value="BeginActiveTime",updateStrategy = FieldStrategy.ALWAYS)
    private LocalDateTime BeginActiveTime;          

    /**
     * 结束激活时间
     */
    @JsonProperty("EndActiveTime")
    @TableField(value="EndActiveTime",updateStrategy = FieldStrategy.ALWAYS)
    private LocalDateTime EndActiveTime;          
      
    /**
     * 关联房号
     */  
    @JsonProperty("RoomDetId")
    @TableField(value="RoomDetId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer RoomDetId;          
      
    /**
     * 订单
     */  
    @JsonProperty("AppointId")
    @TableField(value="AppointId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer AppointId;          
  
    /**
     * 把分配房间实体转换成分配房间传输模型
     */
    public RoomMatchDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        RoomMatchDto RoomMatchDto = new RoomMatchDto();
       
        BeanUtils.copyProperties(RoomMatchDto,this);
       
        return RoomMatchDto;
    }

}
