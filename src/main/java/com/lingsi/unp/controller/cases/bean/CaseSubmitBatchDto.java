package com.lingsi.unp.controller.cases.bean;

import lombok.Data;

import java.util.List;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-04-24 17:44
 **/
@Data
public class CaseSubmitBatchDto {
    private List<CaseSubmitDto> caseSubmitDtoList;
}
