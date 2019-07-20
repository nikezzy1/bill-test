package com.lingsi.unp.service.cases.enums;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-04-25 16:05
 **/
public enum  ImageTypeEnum {


    JPG("jpg"),
    GIF("gif"),
    PNG("png"),
    ;

    private String code;

    ImageTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString(){
        return null;
    }

}
