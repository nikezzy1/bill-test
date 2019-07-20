package com.lingsi.unp.model.response;

public enum CommonResEnum {
    /**
     * 成功
     */
    SUCCESS("0000","success"),
    PASS("0001","pass"),

    /**
     * 失败
     */
    FAILURE("1000","failed"),
    PARAMERROR("1001","parameter_error "),
    NOTFOUND("1002","not_found"),
    DISNABLE("1003","your_account_is_not_active"),

    /**
     * 处理中
     */
    PROCESS("2000", "process"),

    /**
     * 拒绝
     */
    REJECT("4000", "reject"),
    /**
     * 错误
     */
    ERROR("8888","error"),

    /**
     * 未知错误
     */
    UNKNOW("9999","unknow_error")

    ;

    private String code;
    private String msg;

    CommonResEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString(){
        return null;
    }
}
