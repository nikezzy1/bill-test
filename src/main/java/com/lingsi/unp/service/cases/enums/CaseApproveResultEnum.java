package com.lingsi.unp.service.cases.enums;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-04-24 18:10
 **/
public enum  CaseApproveResultEnum {

    /**
     * name 与 code 必需一直， name必需大写
     */
    PASS("pass", "通过"),
    REJECT("reject", "拒绝"),

    ;

    private String code;
    private String msg;

    CaseApproveResultEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }
    public String getMsg() { return msg; }

    public static String getMsg(String code){
        return Enum.valueOf(CaseApproveResultEnum.class, code.toUpperCase()).getMsg();
    }

    @Override
    public String toString(){
        return null;
    }
}
