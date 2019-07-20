package com.lingsi.unp.mapper.notarial;

import com.lingsi.unp.dto.NotarialPaperTypeDto;
import com.lingsi.unp.model.notarial.NotarialPaperType;

import java.util.List;
import java.util.Map;

public interface NotarialPaperTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NotarialPaperType record);

    int insertSelective(NotarialPaperType record);

    NotarialPaperType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NotarialPaperType record);

    int updateByPrimaryKey(NotarialPaperType record);




















    List<NotarialPaperType> selectBySelective(Map map);
    List<NotarialPaperTypeDto> selectBySelectiveSimple(Map map);
    List<NotarialPaperType> selectAll(Map map);
}