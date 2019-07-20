package com.lingsi.unp.service.cases.enums;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-04-24 18:10
 **/
public enum CommonOppositeEnum {

    DONE("1", "已处理"),
    TODO("0", "待处理"),

    ;

    private String code;
    private String msg;

    CommonOppositeEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }
    public String getMsg() { return msg; }

    @Override
    public String toString(){
        return null;
    }
}
