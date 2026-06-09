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
 * 房间明细表
 */
@Data
@TableName("`RoomDet`")
public class RoomDet extends BaseEntity {

      
    /**
     * 酒店
     */  
    @JsonProperty("HotelId")
    @TableField(value="HotelId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer HotelId;          
      
  	  /**
     * 楼层
     */  
    @JsonProperty("Floor")
    @TableField(value="Floor",updateStrategy = FieldStrategy.ALWAYS)
    private String Floor;
      
  	  /**
     * 房号
     */  
    @JsonProperty("No")
    @TableField(value="No",updateStrategy = FieldStrategy.ALWAYS)
    private String No;
      
    /**
     * 房间
     */  
    @JsonProperty("RoomId")
    @TableField(value="RoomId",updateStrategy = FieldStrategy.ALWAYS)
    private Integer RoomId;          
  
    /**
     * 把房间明细实体转换成房间明细传输模型
     */
    public RoomDetDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        RoomDetDto RoomDetDto = new RoomDetDto();
       
        BeanUtils.copyProperties(RoomDetDto,this);
       
        return RoomDetDto;
    }

}
