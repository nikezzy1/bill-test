package com.lingsi.unp.service.cases.enums;

/**
 * @description: 送达回执状态
 * @author: Wuzu
 * @create: 2019-04-24 10:32
 **/
public enum CspStatusEnum {

    /**
     * name 与 code 必需一直， name必需大写
     */
    PAPER_RECEIVED("paper_received", "系统已接收", ""),

    PAPER_SENDED("paper_sended", "系统未接收", ""),

    ;

    private String code;
    private String display;
    private String description;

    CspStatusEnum(String code, String display, String description){
        this.code = code;
        this.display = display;
        this.description = description;
    }

    public String getCode() {
        return code;
    }
    public String getDisplay() { return display; }
    public String getDescription() { return description; }

    public static String getDisplay(String code){
        return Enum.valueOf(CspStatusEnum.class, code.toUpperCase()).getDisplay();
    }

    @Override
    public String toString(){
        return null;
    }
}
