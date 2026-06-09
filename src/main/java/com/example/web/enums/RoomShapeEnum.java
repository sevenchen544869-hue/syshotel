package com.example.web.enums;

import java.util.HashMap;

 /**
   *房型枚举
   */
public enum RoomShapeEnum 
  {
    /**
     * 大床房
     */    
    大床房(1), 
     
    /**
     * 双人房
     */    
    双人房(2); 
     
            
    private final int index;
    
    RoomShapeEnum(int index) 
    {
      this.index = index;
    }

    public int index() {
      return index;
    }
    public static RoomShapeEnum GetEnum(Integer v) {
        if (v == null) {
            return values()[0];
        }
        for (RoomShapeEnum myEnum : values()) {
            if (myEnum.index() == v) {
                return myEnum;
            }
        }
        return values()[0];
    }
      
     
 }
