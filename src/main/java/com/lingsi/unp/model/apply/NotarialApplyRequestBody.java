package com.lingsi.unp.model.apply;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NotarialApplyRequestBody {
    @ApiModelProperty(value="申请流水号,系统唯一",required=true)
    String applySerialno;

    @ApiModelProperty(value="当事人-XXX银行,取申请人字段",required=true)
    String personIdInvolved;

    @ApiModelProperty(value="被申请人姓名",required=true)
    String respondent;

    @ApiModelProperty(value="被申请人身份证号码",required=true)
    String respondentId;

    @ApiModelProperty(value="被申请人资料号--这里取银行卡号",required=true)
    String respondentdataId;

    @ApiModelProperty(value = "申请方产品", required = false)
    String businessType;

    //todo required = true
    @ApiModelProperty(value = "案由", required = false, example = "信用卡纠纷")
    String caseDispute;

    //todo required = true
    @ApiModelProperty(value = "取证地址", required = false, example = "xxx省xxx市xxx银行xx楼xx层")
    String involvedAddress;

}
