package com.lingsi.unp.model.cases;

import com.lingsi.unp.dto.NotarialPaperTypeDto;
import lombok.Data;

import java.util.List;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-05-28 09:32
 **/
@Data
public class CaseApplyWithPaperType {
    private Long caseId;
    /**
     * 申请人
     */
    private String involvedName = "";
    /**
     * 申请人地址， 取证地址
     */
    private String involvedAddress = "";
    private String respondent = "";
    private String respondentId = "";
    private List<NotarialPaperTypeDto> notarialPaperTypeDtoList;
}
