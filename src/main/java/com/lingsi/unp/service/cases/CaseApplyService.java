package com.lingsi.unp.service.cases;

import com.lingsi.unp.controller.cases.bean.CaseApplyVo;
import com.lingsi.unp.exception.DMException;
import com.lingsi.unp.mapper.cases.CaseApplyMapper;
import com.lingsi.unp.model.apply.FileItem;
import com.lingsi.unp.model.apply.NotarialApplyResponseBody;
import com.lingsi.unp.model.cases.CaseApply;
import com.lingsi.unp.model.cases.CaseApplyWithPaperType;
import com.lingsi.unp.model.cases.NotarialPaperFilePath;
import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.model.response.CommonResEnum;
import com.lingsi.unp.service.cases.bean.CaseApplySelectiveDto;
import com.lingsi.unp.service.cases.bean.CaseApplyUpdateSelectiveDto;
import com.lingsi.unp.service.cases.bean.CaseApprovedSelectiveDto;
import com.lingsi.unp.service.cases.enums.CaseStatusEnum;
import com.lingsi.unp.service.cases.enums.CaseTypeEnum;
import com.lingsi.unp.service.notarial.NotarialPapersMaker;
import com.lingsi.unp.utils.base.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 案件申请详情
 * @author: Wuzu
 * @create: 2019-04-24 10:06
 **/
@Service
@Transactional
public class CaseApplyService {

    @Autowired
    CaseApplyMapper caseApplyMapper;

    @Autowired
    CaseApproveService caseApproveService;


    public int deleteByPrimaryKey(Long id){
        return caseApplyMapper.deleteByPrimaryKey(id);
    }

    public int insert(CaseApply record){
        record.setCaseStatus(CaseStatusEnum.PRE_RECEIVE.getCode());
        record.setApplyTime(DateHelper.currentTime());
        record.setApplyDate(DateHelper.currentDate());

        if (1 == caseApplyMapper.insert(record))
        {
            return 1;
        }else{
            throw new DMException("新增案件失败");
        }
    }

    public int insertSelective(CaseApply record){
        return caseApplyMapper.insertSelective(record);
    }

    public CaseApply selectByPrimaryKey(Long id){
        return caseApplyMapper.selectByPrimaryKey(id);
    }

    public CaseApply selectByApplySerialNo(String  applySerialNo){
        return caseApplyMapper.selectByApplySerialNo(applySerialNo);
    }

    public int updateByPrimaryKeySelective(CaseApply record){
        return caseApplyMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(CaseApply record){
        return caseApplyMapper.updateByPrimaryKey(record);
    }

    public List<CaseApplyVo> selectBySelective(CaseApplySelectiveDto record){
        List<CaseApply> caseApplyList ;
        try {
            caseApplyList = caseApplyMapper.selectBySelective(record);
        }catch (Exception e)
        {
            e.printStackTrace();
            throw new DMException("查询数据库异常");
        }

        List<CaseApplyVo> caseApplyVoList = new ArrayList<>();
        for (CaseApply caseApply : caseApplyList) {
            caseApplyVoList.add(caseApply2Vo(caseApply));
        }
        return caseApplyVoList;
    }

    public int updateByStatusSelective(CaseApplyUpdateSelectiveDto dto){
        if (1 == caseApplyMapper.updateByStatusSelective(dto)) {
            return 1;
        } else {
            throw new DMException("案件不存在");
        }
    }

    public int updateByStatus(CaseApplyUpdateSelectiveDto dto){
        return caseApplyMapper.updateByStatusSelective(dto) ;
    }


    public List<CaseApplyVo> selectApprovedSelective(CaseApprovedSelectiveDto record){
        List<CaseApply> caseApplyList ;
        try {
            caseApplyList = caseApplyMapper.selectApprovedSelective(record);
        }catch (Exception e)
        {
            e.printStackTrace();
            throw new DMException("查询数据库异常");
        }

        List<CaseApplyVo> caseApplyVoList = new ArrayList<>();
        for (CaseApply caseApply : caseApplyList) {
            caseApplyVoList.add(caseApply2Vo(caseApply));
        }
        return caseApplyVoList;
    }

    public static CaseApplyVo caseApply2Vo(CaseApply caseApply ){
        CaseApplyVo vo = new CaseApplyVo();
        //申请编号
        vo.setId(caseApply.getId());
        //事项名称
        vo.setCaseType(CaseTypeEnum.getDisplay(caseApply.getCaseType()));
        //当事人
        vo.setPersonNameInvolved(caseApply.getPersonIdInvolved());
        //被申请人
        vo.setRespondent(caseApply.getRespondent());
        //公证员
        vo.setNotaryName(caseApply.getNotaryName());
        //收案人
        vo.setReceiverName(caseApply.getReceiverName());
        //申请时间
        vo.setApplyTime(caseApply.getApplyTime());
        //案件状态
//        vo.setCaseStatus(CaseStatusEnum.getDisplay(caseApply.getCaseStatus()));
        vo.setCaseStatus(caseApply.getCaseStatus());
        ////送达接收状态
        vo.setCspStatus(caseApply.getCspStatus());
        //取证地址
        vo.setInvolvedAddress(caseApply.getInvolvedAddress());
        //案由
        vo.setCaseDispute(caseApply.getCaseDispute());

        return vo;
    }

    public  CommonRes getNotarialResult(String applySerialno) throws IOException {

        NotarialApplyResponseBody body =new NotarialApplyResponseBody();
        CaseApply caseApply = caseApplyMapper.selectByApplySerialNo(applySerialno);
        if(caseApply==null){
            return CommonRes.http_ok("不存在该笔申请").fail(CommonResEnum.NOTFOUND);
        }
        if(CaseStatusEnum.APPROVED.getCode().equals(caseApply.getCaseStatus())
                || CaseStatusEnum.PRE_CERTIFICATE.getCode().equals(caseApply.getCaseStatus())
                || CaseStatusEnum.CERTIFICATED.getCode().equals(caseApply.getCaseStatus())
                || CaseStatusEnum.PRE_SEND.getCode().equals(caseApply.getCaseStatus())
                || CaseStatusEnum.SENDED.getCode().equals(caseApply.getCaseStatus())){
            body = new NotarialApplyResponseBody();
            body.setApplySerialno(applySerialno);
            HashMap<String, List<FileItem>> fileListMap = new HashMap<String, List<FileItem>>();
            body.setFileList(fileListMap);

            List<FileItem> fileItemList = new ArrayList<FileItem>();
            fileListMap.put("公证书",fileItemList);
            fileItemList.add(new FileItem(1,"公证书",
                    NotarialPapersMaker.getNotarialPaperPdfByteString(caseApply.getId())));

            List<NotarialPaperFilePath> records = NotarialPapersMaker.getAppendixFileList(caseApply.getId());
            if(records!=null && records.size()>0){
                //todo:对于页数大于1的情况需要考虑
                for(NotarialPaperFilePath record : records){
                    String fileName =record.getFileName();
                    String filePath = record.getFilePath();
                    int itemNo =  record.getItemNo();

                    fileItemList = new ArrayList<FileItem>();
                    fileListMap.put(fileName,fileItemList);
                    fileItemList.add(new FileItem(itemNo,fileName,NotarialPapersMaker.getAppendixByteString(filePath)));
                }
            }

            return CommonRes.http_ok("公证完成",body).success();
        }else {
            return CommonRes.http_ok("公证申请受理中").processing();
        }

    }


    public  void getNotarialPdf(String applySerialno, OutputStream out) throws Exception {

        CaseApply caseApply = caseApplyMapper.selectByApplySerialNo(applySerialno);
        if(caseApply==null){
            //业务不存在
            throw new Exception(CommonResEnum.NOTFOUND.getCode() + "#" +CommonResEnum.NOTFOUND.getMsg());
        }
        if(CaseStatusEnum.APPROVED.getCode().equals(caseApply.getCaseStatus())
                || CaseStatusEnum.PRE_CERTIFICATE.getCode().equals(caseApply.getCaseStatus())
                || CaseStatusEnum.CERTIFICATED.getCode().equals(caseApply.getCaseStatus())
                || CaseStatusEnum.PRE_SEND.getCode().equals(caseApply.getCaseStatus())
                || CaseStatusEnum.SENDED.getCode().equals(caseApply.getCaseStatus())){

            //获取公证书路径
            String path = NotarialPapersMaker.getNotarialPaperfilePath( caseApply.getId() );

            FileInputStream inputStream = null;
            try {

                inputStream = new FileInputStream(new File(path));

                byte[] bytes = new byte[1024*1024];
                int len;

                while ((len = inputStream.read(bytes)) != -1)
                {
                    out.write(bytes, 0, len);
                }
                out.flush();
            }finally {
                if (inputStream != null)
                {
                    try {
                        inputStream.close();
                    }catch (Exception e)
                    {
                        e.getMessage();
                    }
                }
            }

        }else {
            //公证申请受理中
            throw new Exception(CommonResEnum.PROCESS.getCode() + "#" + CommonResEnum.PROCESS.getMsg());
        }

    }

}
