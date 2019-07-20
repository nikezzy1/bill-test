package com.lingsi.unp.service.notarial.util;

import com.lingsi.unp.mapper.cases.NotarialPaperCaseNumberGeneratorMapper;
import com.lingsi.unp.mapper.cases.NotarialPaperCaseNumberMapper;
import com.lingsi.unp.model.cases.NotarialPaperCaseNumber;
import com.lingsi.unp.utils.base.DateHelper;
import com.lingsi.unp.utils.base.SpringUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * 公证书案号生成
 */
@Transactional
public class NotarialPaperCaseNumberGenerator {
    /**
     * 获取案号
     * @param caseApplyId
     * @return
     */
    public static NotarialPaperCaseNumber getNotarialPaperCaseNumber(Long caseApplyId) throws Exception {
        NotarialPaperCaseNumberMapper notarialPaperCaseNumberMapper = SpringUtils.getBean(NotarialPaperCaseNumberMapper.class);

        NotarialPaperCaseNumberGeneratorMapper NotarialPaperCaseNumberGeneratorMapper = SpringUtils.getBean(NotarialPaperCaseNumberGeneratorMapper.class);

        NotarialPaperCaseNumber notarialPaperCaseNumber = notarialPaperCaseNumberMapper.selectByCaseId(caseApplyId);

        if(notarialPaperCaseNumber==null) {
            String year = DateHelper.getYear();
            int count=0;
            while (true) {
                count++;
                Long id = NotarialPaperCaseNumberGeneratorMapper.select().getId();

                Long originalId=id;

                if(NotarialPaperCaseNumberGeneratorMapper.updateByPrimaryKey( id)==1 ){
                    notarialPaperCaseNumber = new NotarialPaperCaseNumber();
                    notarialPaperCaseNumber.setId(id+1);
                    notarialPaperCaseNumber.setYear(year);
                    notarialPaperCaseNumber.setCaseId(caseApplyId);
                    notarialPaperCaseNumberMapper.insert(notarialPaperCaseNumber);
                    break;
                }
                if(count>1000){
                    throw new Exception("当前获取caseNumber尝试失败，请稍后再试!");
                }
            }
        }
        return notarialPaperCaseNumber;
    }
}
