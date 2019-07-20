package com.lingsi.unp.controller.cases.bean;

import lombok.Data;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-04-24 17:44
 **/
@Data
public class CasePicturedSubmitBatchDto extends CaseSubmitBatchDto {
    private String assistNotaryName;
    private String forensicStartTime;
    private String forensicEndTime;
    private String assistInvolvedName;
    private String assistInvolvedIdCardNo;

    //处理结果，意见
    private String approveResult;
    private String approveMsg;
}
