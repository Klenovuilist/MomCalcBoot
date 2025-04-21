package com.ev.momcalcboot.enums;

public enum AttributeName {
    USERNAME("userName"),
    USERID("userId"),
    USERROLE("userRole"),
    FREADID("freadId"),
    ID("id");

    private final String param;
    AttributeName(String param) {
        this.param = param;
    }
    public String getParam(){
        return param;
    }
}
