package com.lingsi.unp.mapper.cases;

import com.lingsi.unp.model.cases.CaseApprove;

public interface CaseApproveMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CaseApprove record);

    int insertSelective(CaseApprove record);

    CaseApprove selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CaseApprove record);

    int updateByPrimaryKey(CaseApprove record);
}