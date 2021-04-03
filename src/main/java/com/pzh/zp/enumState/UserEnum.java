package com.pzh.zp.enumState;


public enum UserEnum {
//    root(-1,"系统管理员"),
//    admin(0,"管理员"),
//    user(1,"普通用户"),

    FROZEN(-1,"冻结"),     //炸了，用0就是没法。不能写入数据库。
    UNFROZEN(1,"活跃");

    private Integer key;
    private String value;

    private UserEnum(Integer key,String value){
        this.key=key;
        this.value=value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
