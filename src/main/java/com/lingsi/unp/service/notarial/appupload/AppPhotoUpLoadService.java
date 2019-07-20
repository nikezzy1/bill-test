package com.lingsi.unp.service.notarial.appupload;

import com.lingsi.unp.mapper.cases.CaseApplyMapper;
import com.lingsi.unp.mapper.cases.NotarialPaperFilePathMapper;
import com.lingsi.unp.model.cases.CaseApply;
import com.lingsi.unp.model.cases.CaseApplyWithPaperType;
import com.lingsi.unp.model.cases.NotarialPaperFilePath;
import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.service.cases.CaseApplyService;
import com.lingsi.unp.service.cases.bean.CaseApplySelectiveDto;
import com.lingsi.unp.service.cases.enums.CaseStatusEnum;
import com.lingsi.unp.service.notarial.NotarialPaperTypeService;
import com.lingsi.unp.utils.io.file.FilePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppPhotoUpLoadService {

    @Autowired
    NotarialPaperFilePathMapper notarialPaperFilePathMapper;

    @Autowired
    CaseApplyService caseApplyService;

    @Autowired
    CaseApplyMapper caseApplyMapper;

    @Autowired
    NotarialPaperTypeService notarialPaperTypeService;

    public CommonRes recordAppendixFile(NotarialPaperFilePath record){
//        List<String> fileNameList = new ArrayList<>();
//        fileNameList.add(record.getFileName());
//
//        List<NotarialPaperFilePath> fileList = notarialPaperFilePathMapper.selectByCaseId(record.getCaseId());
//        if(fileList!=null){
//            for(NotarialPaperFilePath file : fileList){
//                if(file.getFileType().equals(record.getFileType()) && !fileNameList.contains(file.getFileName())){
//                    fileNameList.add(file.getFileName());
//                }
//                if(file.getFileName().contains(record.getFileName())){
//                    return CommonRes.http_ok("请勿重复上传相同文件").fail();
//                }
//            }
//        }
//        if(fileNameList.size()>=2){
//            CaseApplyUpdateSelectiveDto dto = new CaseApplyUpdateSelectiveDto();
//            dto.setId(record.getCaseId());
//            dto.setOldCaseStatus(CaseStatusEnum.PRE_PICTURE.getCode());
//            dto.setCaseStatus(CaseStatusEnum.PICTURED.getCode());
//            if(caseApplyService.updateByStatus(dto)!=1){
//                return CommonRes.http_ok("上传失败").fail();
//            }
//        }

        boolean done = false;
        List<NotarialPaperFilePath> fileList = notarialPaperFilePathMapper.selectByCaseId(record.getCaseId());
        if(fileList!=null) {
            for (NotarialPaperFilePath file : fileList)
            {
                if (file.getFileType().equals(record.getFileType()) && (int)file.getItemNo()==(int)record.getItemNo() && file.getFileName().equals(record.getFileName()))
                {
                    record.setId(file.getId());
                    notarialPaperFilePathMapper.updateByPrimaryKeySelective(record);
                    done = true;
                    break;
                }
            }
        }
        if (!done) {
            notarialPaperFilePathMapper.insert(record);
        }

        return CommonRes.http_ok("记录成功").success();
    }
    public boolean upLoadPhotos(Long caseid,String fileName) throws Exception {
        List<NotarialPaperFilePath> records =  notarialPaperFilePathMapper.selectByCaseId(caseid);
        if(records==null){
            throw  new  Exception("案件信息不存在！"+caseid);
        }
        List<String> fileNameList=new ArrayList<>();
        if(records!=null){
            for(NotarialPaperFilePath record : records){
                if(record.getFileType().equalsIgnoreCase("AP")) {
                    fileNameList.add(record.getFileName());
                }
            }
        }
        if(fileNameList.contains(fileName)){
            return false;
        }

        NotarialPaperFilePath notarialPaperFilePath = new NotarialPaperFilePath();
        notarialPaperFilePath.setFileName(fileName);
        notarialPaperFilePath.setFileType("AP");
        notarialPaperFilePath.setItemNo(1);
        notarialPaperFilePath.setCaseId(caseid);
        String fileId= FilePath.getFileName();
        notarialPaperFilePath.setFilePath(fileId);
        notarialPaperFilePathMapper.insert(notarialPaperFilePath);
        return true;
    }

//    public List<String> getPhotoTaskList(){
//        List<String> resultList= new ArrayList<>();
//        CaseApplySelectiveDto caseApplySelectiveDto = new CaseApplySelectiveDto();
//        String[] caseStatus = new String[1];
//       caseStatus[0]=CaseStatusEnum.PRE_PICTURE.getCode();
//        caseApplySelectiveDto.setCaseStatus(caseStatus);
//        List<CaseApply> caseApplyList = caseApplyMapper.selectBySelective(caseApplySelectiveDto);
//        if(caseApplyList!=null){
//            for(CaseApply  caseApply:caseApplyList )
//                resultList.add(caseApply.getId()+"#"+caseApply.getRespondent()+"#"+caseApply.getRespondentId());
//        }
//        return  resultList;
//    }

    public List<CaseApplyWithPaperType> getPhotoTaskList(){
        List<CaseApplyWithPaperType> list = new ArrayList<>();

        CaseApplySelectiveDto caseApplySelectiveDto = new CaseApplySelectiveDto();
        String[] caseStatus = new String[1];
       caseStatus[0]=CaseStatusEnum.PRE_PICTURE.getCode();
        caseApplySelectiveDto.setCaseStatus(caseStatus);

        List<CaseApply> caseApplyList = caseApplyMapper.selectBySelective(caseApplySelectiveDto);
        if (caseApplyList != null)
        {
            for (CaseApply caseApply : caseApplyList)
            {
                CaseApplyWithPaperType caseApplyWithPaperType = new CaseApplyWithPaperType();
                caseApplyWithPaperType.setNotarialPaperTypeDtoList(notarialPaperTypeService.selectAp(caseApply.getPersonIdInvolved(), caseApply.getBusinessType()));
                caseApplyWithPaperType.setCaseId(caseApply.getId());
                caseApplyWithPaperType.setRespondent(caseApply.getRespondent());
                caseApplyWithPaperType.setRespondentId(caseApply.getRespondentId());
                // 20190529-wuzu-新增申请人，取证地址
                caseApplyWithPaperType.setInvolvedName(caseApply.getPersonIdInvolved());
                caseApplyWithPaperType.setInvolvedAddress(caseApply.getInvolvedAddress());
                list.add(caseApplyWithPaperType);
            }
        }

        return list;
    }

}
