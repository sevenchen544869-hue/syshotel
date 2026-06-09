package com.example.web.enums;

import java.util.HashMap;

 /**
   *特色房型枚举
   */
public enum FeaturedRoomShapeEnum 
  {
    /**
     * 酒店套餐
     */    
    酒店套餐(1), 
     
    /**
     * 亲子主题房
     */    
    亲子主题房(2), 
     
    /**
     * 家庭房
     */    
    家庭房(3); 
     
            
    private final int index;
    
    FeaturedRoomShapeEnum(int index) 
    {
      this.index = index;
    }

    public int index() {
      return index;
    }
    public static FeaturedRoomShapeEnum GetEnum(Integer v) {
        if (v == null) {
            return values()[0];
        }
        for (FeaturedRoomShapeEnum myEnum : values()) {
            if (myEnum.index() == v) {
                return myEnum;
            }
        }
        return values()[0];
    }
      
     
 }
