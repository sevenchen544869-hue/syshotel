package com.example.web.enums;

import java.util.HashMap;

 /**
   *早餐类型枚举
   */
public enum BreakfastTypeEnum 
  {
    /**
     * 无早餐
     */    
    无早餐(1), 
     
    /**
     * 一份早餐
     */    
    一份早餐(2), 
     
    /**
     * 二份早餐
     */    
    二份早餐(3), 
     
    /**
     * 多份早餐
     */    
    多份早餐(4); 
     
            
    private final int index;
    
    BreakfastTypeEnum(int index) 
    {
      this.index = index;
    }

    public int index() {
      return index;
    }
    public static BreakfastTypeEnum GetEnum(Integer v) {
        if (v == null) {
            return values()[0];
        }
        for (BreakfastTypeEnum myEnum : values()) {
            if (myEnum.index() == v) {
                return myEnum;
            }
        }
        return values()[0];
    }
      
     
 }
