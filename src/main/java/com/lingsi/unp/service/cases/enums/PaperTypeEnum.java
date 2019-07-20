package com.lingsi.unp.service.cases.enums;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-04-25 16:20
 **/
public enum  PaperTypeEnum {
    NOTARY("notary", "公证书"),
    NOTARY_APPLY("notaryApply", "申请表"),

    CREDITCARD_APPLY("creditCardApply", "信用卡申请表"),
    USE_CONTRACT("userContract", "领用合约"),
    IDENTITY("identity", "被申请人身份证明"),
    NOTARY_CONTRACT("nataryContract", "仲裁协议"),
    CREDITCARD_TRANS("fail", "信用卡交易明细"),

    ;

    private String code;
    private String display;

    PaperTypeEnum(String code, String display){
        this.code = code;
        this.display = display;
    }

    public String getCode() {
        return code;
    }
    public String getDisplay() { return display; }

    public static String getDisplay(String code){
        return Enum.valueOf(CaseStatusEnum.class, code.toUpperCase()).getDisplay();
    }

    @Override
    public String toString(){
        return null;
    }
}
