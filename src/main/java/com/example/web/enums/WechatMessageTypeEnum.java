package com.example.web.enums;

public enum WechatMessageTypeEnum {
    /**
     * 文本
     */
    文本(1),

    /**
     * 用户
     */
    图片(2),

    /**
     * 视频
     */
    视频(4),

    /**
     * 文件
     */
    文件(5);

    private final int index;

    WechatMessageTypeEnum(int index) {
        this.index = index;
    }

    public int index() {
        return index;
    }

    public String toString() {

        return name();
    }

    public static WechatMessageTypeEnum GetEnum(Integer v) {
        for (WechatMessageTypeEnum myEnum : values()) {
            if (myEnum.index() == v) {
                return myEnum;
            }
        }
        return values()[0];
    }
}
