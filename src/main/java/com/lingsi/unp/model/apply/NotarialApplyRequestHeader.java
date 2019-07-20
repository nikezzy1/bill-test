package com.lingsi.unp.model.apply;

import io.swagger.annotations.ApiModelProperty;

public class NotarialApplyRequestHeader {
    @ApiModelProperty(value="授权获得的访问令牌",name="Authorization",required=false)
    String authorization;

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        authorization = authorization;
    }

}
