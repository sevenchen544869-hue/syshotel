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
 * 员工类
 */
@Data
public class EmployeeDto extends BaseDto
{

    
     
    /**
     * 地址
     */ 
    @JsonProperty("Address")
    private String Address;
    
     
    /**
     * 工号
     */ 
    @JsonProperty("No")
    private String No;
    
     
    /**
     * 性别
     */ 
    @JsonProperty("Sex")
    private String Sex;
    
     
    /**
     * 薪资
     */ 
    @JsonProperty("Salary")
    private String Salary;
    
     
    /**
     * 酒店
     */ 
    @JsonProperty("HotelId")
    private Integer HotelId;          
    
     
    /**
     * 照片
     */ 
    @JsonProperty("Photo")
    private String Photo;
    
     
    /**
     * 出生年月
     */ 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("BirthDay")
    private LocalDateTime BirthDay;             
    
     
    /**
     * 关联账号
     */ 
    @JsonProperty("UserId")
    private Integer UserId;          
    
     
    /**
     * 身份证
     */ 
    @JsonProperty("IdCard")
    private String IdCard;
    
     
    /**
     * 电话
     */ 
    @JsonProperty("Phone")
    private String Phone;
    
     
    /**
     * 姓名
     */ 
    @JsonProperty("Name")
    private String Name;

     @JsonProperty("UserDto") 
    private AppUserDto UserDto;                        
   
     @JsonProperty("HotelDto") 
    private HotelDto HotelDto;                        
   
 	 /**
     * 把员工传输模型转换成员工实体
     */
    public Employee MapToEntity() throws InvocationTargetException, IllegalAccessException {
        Employee Employee= new Employee();
     
         BeanUtils.copyProperties(Employee,this);
        
        return Employee;
    }

}
