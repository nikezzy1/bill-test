package com.lingsi.unp.mapper.cases;

import com.lingsi.unp.model.cases.NotarialPaperCaseNumberGenerator;

public interface NotarialPaperCaseNumberGeneratorMapper {
   /* int deleteByPrimaryKey(Long id);

    int insert(NotarialPaperCaseNumberGenerator record);

    int insertSelective(NotarialPaperCaseNumberGenerator record);*/

    NotarialPaperCaseNumberGenerator select();

    int updateByPrimaryKey(Long id);
}