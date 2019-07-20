package com.lingsi.unp.service.cases.enums;

/**
 * @description: 案件类型
 * @author: Wuzu
 * @create: 2019-04-24 10:24
 **/
public enum  CaseTypeEnum {

    /**
     * name 与 code 必需一直， name必需大写
     */
    PRESERVATION("preservation", "保全证据", "preservation of evidence"),

    ;

    private String code;
    private String display;
    private String description;

    CaseTypeEnum(String code, String display, String description){
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
        return Enum.valueOf(CaseTypeEnum.class, code.toUpperCase()).getDisplay();
    }

    @Override
    public String toString(){
        return null;
    }

}
