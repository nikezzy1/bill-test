package com.lingsi.unp.mapper.cases;

import com.lingsi.unp.model.cases.CaseApply;
import com.lingsi.unp.model.cases.CaseApplyWithPaperType;
import com.lingsi.unp.service.cases.bean.CaseApplySelectiveDto;
import com.lingsi.unp.service.cases.bean.CaseApplyUpdateSelectiveDto;
import com.lingsi.unp.service.cases.bean.CaseApprovedSelectiveDto;

import java.util.List;
import java.util.Map;

public interface CaseApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CaseApply record);

    int insertSelective(CaseApply record);

    CaseApply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CaseApply record);

    int updateByPrimaryKey(CaseApply record);













    List<CaseApply> selectBySelective(CaseApplySelectiveDto record);

    List<CaseApply> selectApprovedSelective(CaseApprovedSelectiveDto record);

    int updateByStatusSelective(CaseApplyUpdateSelectiveDto record);

    CaseApply selectByApplySerialNo(String  applySerialNo);

}