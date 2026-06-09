package com.example.web.enums;

import java.util.HashMap;

 /**
   *预约状态枚举
   */
public enum AppointStatusEnum 
  {
    /**
     * 待入住
     */    
    待入住(1), 
     
    /**
     * 已入住
     */    
    已入住(2), 
     
    /**
     * 用户取消
     */    
    用户取消(3), 
     
    /**
     * 酒店取消
     */    
    酒店取消(4), 
     
    /**
     * 完成
     */    
    完成(5), 
     
    /**
     * 待确定
     */    
    待确定(6), 
     
    /**
     * 申请取消
     */    
    申请取消(7), 
     
    /**
     * 完成退房
     */    
    完成退房(8),

    /**
     * 待支付
     */
    待支付(9),
    
    /**
     * 系统取消
    */
    系统取消(10);
    private final int index;
    
    AppointStatusEnum(int index) 
    {
      this.index = index;
    }

    public int index() {
      return index;
    }
    public static AppointStatusEnum GetEnum(Integer v) {
        if (v == null) {
            return values()[0];
        }
        for (AppointStatusEnum myEnum : values()) {
            if (myEnum.index() == v) {
                return myEnum;
            }
        }
        return values()[0];
    }
      
     
 }
