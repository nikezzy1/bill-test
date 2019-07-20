package com.lingsi.unp.model.response;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonRes {

    private Object data;
    private String message;
    private String messageCode;
    private String status;
    private long timestamp;

    public CommonRes() {
    }

    public CommonRes success(){
        messageCode= CommonResEnum.SUCCESS.getCode();
        message = CommonResEnum.SUCCESS.getMsg();
        return this;
    }

    public CommonRes processing(){
        messageCode= CommonResEnum.PROCESS.getCode();
        message = CommonResEnum.PROCESS.getMsg();
        return this;
    }

    public CommonRes fail(){
        messageCode= CommonResEnum.FAILURE.getCode();
        if(message==null || "".equals(message))
            message = CommonResEnum.FAILURE.getMsg();
        return this;
    }

    public CommonRes fail(CommonResEnum code){
        messageCode=code.getCode();
        if(message==null || "".equals(message))
            message = code.getMsg();
        return this;
    }
    @Bean
    public CommonRes apiResponseBody(){
        CommonRes cr = new CommonRes();
        cr.setTimestamp(System.currentTimeMillis());
        return new CommonRes();
    }

    public CommonRes(String status,String msg,Object object){
        this.status = status;
        this.data = object;
        this.timestamp = System.currentTimeMillis();
        this.message=msg;
    }
    public void setMessageEnum(CommonResEnum commonResEnum){
        setMessageCode(commonResEnum.getCode());
        setMessage(commonResEnum.getMsg());
    }


    public static CommonRes http_ok(String msg, Object obj) {
        return new CommonRes("200", msg, obj);
    }

    public static CommonRes http_ok(String msg) {
        return new CommonRes("200", msg, null);
    }
    public static CommonRes http_error(String msg) {
        return new CommonRes("500", msg, null);
    }
    public static CommonRes http_autherror(String msg) {
        return new CommonRes("401", msg, null);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
