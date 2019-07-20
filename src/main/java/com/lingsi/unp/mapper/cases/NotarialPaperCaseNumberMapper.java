package com.lingsi.unp.mapper.cases;

import com.lingsi.unp.model.cases.NotarialPaperCaseNumber;

public interface NotarialPaperCaseNumberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NotarialPaperCaseNumber record);

    int insertSelective(NotarialPaperCaseNumber record);

    NotarialPaperCaseNumber selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NotarialPaperCaseNumber record);

    int updateByPrimaryKey(NotarialPaperCaseNumber record);

    NotarialPaperCaseNumber selectByCaseId(Long caseId);
}