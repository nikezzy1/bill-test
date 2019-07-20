package com.lingsi.unp.controller.cases.bean;

import lombok.Data;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-04-24 17:33
 **/
@Data
public class CaseApproveDto extends CaseSubmitDto {
    private String approveResult;
    private String approveMsg;
}
