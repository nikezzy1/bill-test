package com.lingsi.unp.service.cases.bean;

import lombok.Data;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-04-24 15:40
 **/
@Data
public class CaseApplyUpdateSelectiveDto {
    private Long id;
    private String oldCaseStatus;
    private String caseStatus;
    //收案人
    private String receiverId;
    private String receiverName;
    //公证员
    private String notaryId;
    private String notaryName;
    //
    private String assistNotaryName;
    private String forensicStartTime;
    private String forensicEndTime;

    private String assistInvolvedName;
    private String assistInvolvedIdCardNo;
    /**
     *    文书线下接收状态
     */
    private String cspStatus;
}
