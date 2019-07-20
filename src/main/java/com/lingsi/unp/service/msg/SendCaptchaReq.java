package com.lingsi.unp.service.msg;

import lombok.Data;

@Data
public class SendCaptchaReq {
    private String phone;
    private String code;
    private String appId;
    private long ts;
}
