package com.ev.momcalcboot.enums;

public enum CookiesParametr {
    USERNAME("userName"),
    USERID("userId"),
    USERROLE("userRole"),
    FREADID("freadId"),
    ID("id");

    private final String param;
    CookiesParametr(String param) {
        this.param = param;
    }
    public String getParam(){
        return param;
    }
}
