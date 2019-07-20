package com.lingsi.unp.service.cases.enums;

/**
 * @description: 案件状态
 * @author: Wuzu
 * @create: 2019-04-24 10:32
 **/
public enum CaseStatusEnum {

//    公证员：未受理-->待拍照-->已拍照-->待审批
//    主任：待审批-->已审批待制证
//    公证员：已审批待制证-->已制证待送达
//    送达：已制证待送达-->已送达

    /**
     * name 与 code 必需一直， name必需大写
     */
    PRE_RECEIVE("pre_receive", "未受理", ""),

    PRE_PICTURE("pre_picture", "待拍照", ""),

    PICTURED("pictured", "已拍照", ""),

    SUBMITTED("pre_approve", "已处理", ""),
    PRE_APPROVE("pre_approve", "待审批", ""),

    APPROVED("approved", "已审批", "待制证"),
    PRE_CERTIFICATE("approved", "待制证", ""),

    CERTIFICATED("pre_send", "已制证", ""),
    PRE_SEND("pre_send", "待送达", ""),

    SENDED("sended", "已送达", ""),

    CALCEL("cancel", "取消", "取消公证"),
    REJECT("reject", "拒绝受理", "拒绝受理"),
    FAIL("fail", "受理失败", "受理失败"),



    ;

    private String code;
    private String display;
    private String description;

    CaseStatusEnum(String code, String display, String description){
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
        return Enum.valueOf(CaseStatusEnum.class, code.toUpperCase()).getDisplay();
    }

    @Override
    public String toString(){
        return null;
    }
}
