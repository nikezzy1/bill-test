package com.lingsi.unp.service.msg;

import lombok.Data;

@Data
public class CheckCaptchaReq {
    private String appId;
    private String phone;
    private String code;
    private long ts;
}
