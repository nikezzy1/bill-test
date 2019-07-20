package com.lingsi.unp.service.notarial;

import com.lingsi.unp.mapper.cases.NotarialPaperFilePathMapper;
import com.lingsi.unp.model.cases.NotarialPaperFilePath;
import com.lingsi.unp.utils.base.SpringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-05-07 10:47
 **/
public class NotarialPapersMakerUtils {


    public static Map selectCaseFiles(Long caseId) {
        Map fileMap = new HashMap();
        NotarialPaperFilePathMapper notarialPaperFilePathMapper = SpringUtils.getBean(NotarialPaperFilePathMapper.class);

        Map map = new HashMap();
        map.put("caseId", caseId);
        List<NotarialPaperFilePath> records = notarialPaperFilePathMapper.selectBySelective(map);

        if(records!=null && records.size()>0) {
            for (NotarialPaperFilePath record : records) {
                fileMap.put(record.getFilePath(), record.getFileName());
            }
        }
        return fileMap;
    }
}
