package com.lingsi.unp.service.notarial;

import com.lingsi.unp.dto.NotarialPaperTypeDto;
import com.lingsi.unp.dto.vo.NotarialPaperTypeVo;
import com.lingsi.unp.mapper.notarial.NotarialPaperTypeMapper;
import com.lingsi.unp.model.notarial.NotarialPaperType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-05-08 18:40
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class NotarialPaperTypeService {

    @Autowired
    NotarialPaperTypeMapper notarialPaperTypeMapper;



    public int deleteByPrimaryKey(int id){
        return notarialPaperTypeMapper.deleteByPrimaryKey(id);
    }

    public int insert(NotarialPaperType record){
        return notarialPaperTypeMapper.insert(record);
    }

    public int insertSelective(NotarialPaperType record){
        return notarialPaperTypeMapper.insertSelective(record);
    }

    public int add(NotarialPaperTypeVo vo)
    {
        Map param = new HashMap();
        param.put("personInvolved", vo.getPersonInvolved());
        if (vo.getBusinessType() == null || vo.getBusinessType().length()==0){

        }else {
            param.put("businessType", vo.getBusinessType());
        }
        param.put("paperType", vo.getPaperType());
        List<NotarialPaperTypeDto> list = notarialPaperTypeMapper.selectBySelectiveSimple(param);
        int existCnt = list.size();

        NotarialPaperType notarialPaperType = new NotarialPaperType();
        notarialPaperType.setPersonInvolved(vo.getPersonInvolved());
        notarialPaperType.setBusinessType(vo.getBusinessType());
        if (existCnt >= 9){
            notarialPaperType.setPaperNo(vo.getPaperType() + (existCnt + 1));
        }else {
            notarialPaperType.setPaperNo(vo.getPaperType() + "0" + (existCnt + 1));
        }
        notarialPaperType.setPaperType(vo.getPaperType());
        notarialPaperType.setPaperName(vo.getPaperName());
        notarialPaperType.setPageCount(vo.getPageCount());
        return notarialPaperTypeMapper.insertSelective(notarialPaperType);
    }

    public NotarialPaperType selectByPrimaryKey(int id ){
        return notarialPaperTypeMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(NotarialPaperType record){
        return notarialPaperTypeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(NotarialPaperType record){
        return notarialPaperTypeMapper.updateByPrimaryKey(record);
    }

    public List<NotarialPaperType> selectAll(String personInvolved, String businessType){
        Map param = new HashMap();
        param.put("personInvolved", personInvolved);
        if (businessType != null && businessType.length() >0) {
            param.put("businessType", businessType);
        }
        return notarialPaperTypeMapper.selectAll(param);
    }

    public List<NotarialPaperTypeDto> selectAp(String personInvolved, String businessType){
        Map param = new HashMap();
        param.put("personInvolved", personInvolved);
        if (businessType != null && businessType.length() >0) {
            param.put("businessType", businessType);
        }
        param.put("paperType", "AP");
        return notarialPaperTypeMapper.selectBySelectiveSimple(param);
    }
    public NotarialPaperTypeDto selectNp(String personInvolved, String businessType){
        Map param = new HashMap();
        param.put("personInvolved", personInvolved);
        if (businessType != null && businessType.length() >0) {
            param.put("businessType", businessType);
        }
        param.put("paperType", "NP");
        return notarialPaperTypeMapper.selectBySelectiveSimple(param).get(0);
    }

    public NotarialPaperTypeDto selectByNo(String personInvolved, String businessType, String paperNo){
        Map param = new HashMap();
        param.put("personInvolved", personInvolved);
        if (businessType != null && businessType.length() >0) {
            param.put("businessType", businessType);
        }
        param.put("paperNo", paperNo);
        return notarialPaperTypeMapper.selectBySelectiveSimple(param).get(0);
    }


}
