package com.lingsi.unp.mapper.cases;

import com.lingsi.unp.model.cases.NotarialPaperFilePath;

import java.util.List;
import java.util.Map;

public interface NotarialPaperFilePathMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NotarialPaperFilePath record);

    int insertSelective(NotarialPaperFilePath record);

    NotarialPaperFilePath selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NotarialPaperFilePath record);

    int updateByPrimaryKey(NotarialPaperFilePath record);

    List<NotarialPaperFilePath> selectByCaseId(Long caseId);

    List<NotarialPaperFilePath> selectBySelective(Map map);

    List<NotarialPaperFilePath> selectApOrderByItemNo(Map map);
}