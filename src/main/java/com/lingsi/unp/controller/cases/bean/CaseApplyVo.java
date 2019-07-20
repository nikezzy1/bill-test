package com.lingsi.unp.controller.cases.bean;

import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Default;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-04-24 11:19
 **/
@Data
public class CaseApplyVo {

    //申请编号
    private Long id;
    //事项名称
    private String caseType = "";
    //当事人
    private String personNameInvolved = "";
    //被申请人
    private String respondent = "";
    //公证员
    private String notaryName = "";
    //收案人
    private String receiverName = "";
    //申请时间
    private String applyTime = "";
    //案件状态
    private String caseStatus = "";
    //送达接收状态
    private String cspStatus = "";
    //取证地址
    private String involvedAddress = "";
    //案由
    private String caseDispute = "";

}
