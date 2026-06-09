package com.example.web.enums;

import java.util.HashMap;

 /**
   *房间状态枚举
   */
public enum RoomStatusEnum 
  {
    /**
     * 空闲
     */    
    空闲(1), 
     
    /**
     * 预约中
     */    
    预约中(2), 
     
    /**
     * 入住中
     */    
    入住中(3), 
     
    /**
     * 已退房
     */    
    已退房(4); 
     
            
    private final int index;
    
    RoomStatusEnum(int index) 
    {
      this.index = index;
    }

    public int index() {
      return index;
    }
    public static RoomStatusEnum GetEnum(Integer v) {
        if (v == null) {
            return values()[0];
        }
        for (RoomStatusEnum myEnum : values()) {
            if (myEnum.index() == v) {
                return myEnum;
            }
        }
        return values()[0];
    }
      
     
 }
