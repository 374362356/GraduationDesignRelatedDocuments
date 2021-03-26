package com.pzh.zp.enumState;


public enum UserEnum {
    sysmanager(-1),
    admin(0),
    user(1);

    private Integer value;

    private UserEnum(Integer value){
        this.value=value;
    }

    public Integer toInt() {
        return this.value;
    }
}
