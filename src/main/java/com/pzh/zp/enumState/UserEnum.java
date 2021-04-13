package com.pzh.zp.enumState;


public enum UserEnum {
    root(2,"root"),
    admin(3,"admin"),
    user(4,"user"),

    FROZEN(-1,"冻结"),     //炸了，用0就是没法。不能写入数据库。
    UNFROZEN(1,"活跃"),

    CHECKIN(11,"已签到"),
    UNCHECKIN(12,"未签到");


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
