package com.lingsi.unp.service.cases;

import com.lingsi.unp.controller.cases.bean.CaseApproveDto;
import com.lingsi.unp.controller.cases.bean.CaseApproveLogDTO;
import com.lingsi.unp.controller.cases.bean.CasePicturedSubmitBatchDto;
import com.lingsi.unp.controller.cases.bean.CaseSubmitDto;
import com.lingsi.unp.exception.DMException;
import com.lingsi.unp.mapper.cases.CaseApplyMapper;
import com.lingsi.unp.mapper.cases.CaseApproveMapper;
import com.lingsi.unp.mapper.cases.NotarialPaperFilePathMapper;
import com.lingsi.unp.model.auth.AppUser;
import com.lingsi.unp.model.cases.CaseApply;
import com.lingsi.unp.model.cases.CaseApprove;
import com.lingsi.unp.service.cases.bean.CaseApplyUpdateSelectiveDto;
import com.lingsi.unp.service.cases.enums.CaseApproveResultEnum;
import com.lingsi.unp.service.cases.enums.CaseStatusEnum;
import com.lingsi.unp.service.cases.enums.CommonOppositeEnum;
import com.lingsi.unp.service.cases.enums.CspStatusEnum;
import com.lingsi.unp.service.notarial.NotarialPaperTypeService;
import com.lingsi.unp.service.notarial.NotarialPapersMaker;
import com.lingsi.unp.utils.base.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-04-24 17:12
 **/
@Service
@Transactional
public class CaseApproveService {

    @Autowired
    CaseApproveMapper caseApproveMapper;

    @Autowired
    CaseApplyMapper caseApplyMapper;

    @Autowired
    NotarialPaperFilePathMapper notarialPaperFilePathMapper;

    @Autowired
    NotarialPaperTypeService notarialPaperTypeService;

    public int deleteByPrimaryKey(Long id){
        return caseApproveMapper.deleteByPrimaryKey(id);
    }

    public int insert(CaseApprove record){
        return caseApproveMapper.insert(record);
    }

    public int insertSelective(CaseApprove record){
        return caseApproveMapper.insertSelective(record);
    }

    public CaseApprove selectByPrimaryKey(Long id){
        return caseApproveMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(CaseApprove record){
        return caseApproveMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(CaseApprove record){
        return caseApproveMapper.updateByPrimaryKey(record);
    }

    public void approve(CaseApproveDto caseApproveDto, AppUser appUser) throws Exception
    {

        String caseStatus = caseApproveDto.getCaseStatus();

        CaseApply caseApply = caseApplyMapper.selectByPrimaryKey(caseApproveDto.getId());

        CaseApplyUpdateSelectiveDto dto = new CaseApplyUpdateSelectiveDto();
        dto.setId(caseApproveDto.getId());


        //审批记录
        CaseApprove caseApprove = new CaseApprove();

        if (CaseStatusEnum.PRE_APPROVE.getCode().equals(caseStatus)) {
            if (CaseApproveResultEnum.PASS.getCode().equals(caseApproveDto.getApproveResult())){
                //同意pass
                //待审批
                dto.setOldCaseStatus(CaseStatusEnum.PRE_APPROVE.getCode());
                //下一阶段 已审批
                dto.setCaseStatus(CaseStatusEnum.APPROVED.getCode());
                //生成pdf
                NotarialPapersMaker.notarialPaperHandle(caseApply, notarialPaperTypeService.selectNp(caseApply.getPersonIdInvolved(), ""));

                //审批结果
                caseApprove.setApproveResult(CaseApproveResultEnum.PASS.getCode());


            }else {
                //拒绝reject
                //待审批
                dto.setOldCaseStatus(CaseStatusEnum.PRE_APPROVE.getCode());
                //退回 待拍照
                dto.setCaseStatus(CaseStatusEnum.PRE_PICTURE.getCode());

                //审批结果
                caseApprove.setApproveResult(CaseApproveResultEnum.REJECT.getCode());

            }
            //更新数据
            caseApplyMapper.updateByStatusSelective(dto);

            caseApprove.setCaseId(caseApproveDto.getId());
            caseApprove.setUserId(appUser.getUserid());
            caseApprove.setRoleId(appUser.getRoles().get(0).getRoleid());
            caseApprove.setApproveMsg(caseApproveDto.getApproveMsg());
            caseApprove.setBeginTime(DateHelper.currentTime());
            caseApprove.setEndTime(DateHelper.currentTime());
            caseApprove.setApproveStatus(CommonOppositeEnum.DONE.getCode());
            caseApproveMapper.insertSelective(caseApprove);
        } else {
            throw new Exception("非法请求");
        }
    }


    @Transactional
    public void deal(CaseSubmitDto caseSubmitDto, AppUser appUser) throws Exception
    {
        String caseStatus = caseSubmitDto.getCaseStatus();

        CaseApplyUpdateSelectiveDto dto = new CaseApplyUpdateSelectiveDto();
        dto.setId(caseSubmitDto.getId());

        if (CaseStatusEnum.PRE_RECEIVE.getCode().equals(caseStatus)) {
            //待受理
            dto.setOldCaseStatus(CaseStatusEnum.PRE_RECEIVE.getCode());
            //下一阶段 待拍照
            dto.setCaseStatus(CaseStatusEnum.PRE_PICTURE.getCode());

            //公证员
            dto.setNotaryId(appUser.getUserid());
            dto.setNotaryName(appUser.getUsername());

        } else if (CaseStatusEnum.PICTURED.getCode().equals(caseStatus)) {
            //已拍照
            dto.setOldCaseStatus(CaseStatusEnum.PICTURED.getCode());
            //下一阶段 待审批
            dto.setCaseStatus(CaseStatusEnum.PRE_APPROVE.getCode());
            //公证员
            dto.setNotaryId(appUser.getUserid());
            dto.setNotaryName(appUser.getUsername());

        } else if (CaseStatusEnum.PRE_CERTIFICATE.getCode().equals(caseStatus)) {
            //待制证
            dto.setOldCaseStatus(CaseStatusEnum.PRE_CERTIFICATE.getCode());
            //下一阶段 已制证
            dto.setCaseStatus(CaseStatusEnum.CERTIFICATED.getCode());

        } else if (CaseStatusEnum.PRE_SEND.getCode().equals(caseStatus)) {
            //待送达
            dto.setOldCaseStatus(CaseStatusEnum.PRE_SEND.getCode());
            //下一阶段 已送达
            dto.setCaseStatus(CaseStatusEnum.SENDED.getCode());
            //todo csp接口通知已收到文书
            dto.setCspStatus(CspStatusEnum.PAPER_RECEIVED.getCode());
        } else {
            throw new Exception("非法请求");
        }

        //更新数据
        caseApplyMapper.updateByStatusSelective(dto);

        //审批记录
//        CaseApprove caseApprove = new CaseApprove();
//        //审批结果
//        caseApprove.setApproveResult(CaseApproveResultEnum.PASS.getCode());
//        caseApprove.setCaseId(caseSubmitDto.getId());
//        caseApprove.setUserId(appUser.getUserid());
//        caseApprove.setRoleId(appUser.getRoles().get(0).getRoleid());
//        caseApprove.setApproveMsg("");
//        caseApprove.setBeginTime(DateHelper.currentTime());
//        caseApprove.setEndTime(DateHelper.currentTime());
//        caseApprove.setApproveStatus(CommonOppositeEnum.DONE.getCode());
//        caseApproveMapper.insertSelective(caseApprove);
    }


    /**
     * 审批日志
     * @param approveLogDTO
     * @param appUser
     */
    public void approveLog(CaseApproveLogDTO approveLogDTO, AppUser appUser)
    {
        //审批记录
        CaseApprove caseApprove = new CaseApprove();
        //审批结果
        caseApprove.setApproveResult(approveLogDTO.getApproveResult());
        caseApprove.setApproveMsg(approveLogDTO.getApproveMsg());

        caseApprove.setCaseId(approveLogDTO.getId());
        caseApprove.setUserId(appUser.getUserid());
        caseApprove.setRoleId(appUser.getRoles().get(0).getRoleid());
        caseApprove.setBeginTime(DateHelper.currentTime());
        caseApprove.setEndTime(DateHelper.currentTime());
        caseApprove.setApproveStatus(CommonOppositeEnum.DONE.getCode());
        try {
            caseApproveMapper.insertSelective(caseApprove);
        }catch (Exception e)
        {
            e.printStackTrace();
            throw new DMException("新增审批记录失败", e);
        }
    }

    /**
     *
     * @param casePicturedSubmitBatchDto
     * @param caseSubmitDto
     * @param appUser
     */
    @Transactional(rollbackFor = Exception.class)
    public void submitPictured(CasePicturedSubmitBatchDto casePicturedSubmitBatchDto, CaseSubmitDto caseSubmitDto, AppUser appUser) throws Exception{

        String assistNotaryName = casePicturedSubmitBatchDto.getAssistNotaryName();
        String forensicStartTime = casePicturedSubmitBatchDto.getForensicStartTime();
        String forensicEndTime = casePicturedSubmitBatchDto.getForensicEndTime();
        String assistInvolvedName = casePicturedSubmitBatchDto.getAssistInvolvedName();
        String assistInvolvedIdCardNo = casePicturedSubmitBatchDto.getAssistInvolvedIdCardNo();
        //处理结果，意见
        String approveResult = casePicturedSubmitBatchDto.getApproveResult();
        String approveMsg = casePicturedSubmitBatchDto.getApproveMsg();

        String caseStatus = caseSubmitDto.getCaseStatus();

        CaseApplyUpdateSelectiveDto dto = new CaseApplyUpdateSelectiveDto();
        dto.setId(caseSubmitDto.getId());

        if (CaseStatusEnum.PICTURED.getCode().equals(caseStatus)) {
            //已拍照
            dto.setOldCaseStatus(CaseStatusEnum.PICTURED.getCode());
            //下一阶段 待审批
            dto.setCaseStatus(CaseStatusEnum.PRE_APPROVE.getCode());

            //助理公证员
            dto.setAssistNotaryName(assistNotaryName);
            //取证时间
            dto.setForensicStartTime(forensicStartTime);
            dto.setForensicEndTime(forensicEndTime);
            //牵涉方协助员
            dto.setAssistInvolvedName(assistInvolvedName);
            dto.setAssistInvolvedIdCardNo(assistInvolvedIdCardNo);

        } else {
            throw new Exception("非法请求");
        }

        //更新数据
        caseApplyMapper.updateByStatusSelective(dto);

        //审批记录
//        CaseApprove caseApprove = new CaseApprove();
//        caseApprove.setCaseId(caseSubmitDto.getId());
//        caseApprove.setUserId(appUser.getUserid());
//        caseApprove.setRoleId(appUser.getRoles().get(0).getRoleid());
//        caseApprove.setApproveMsg("");
//        //审批结果
//        caseApprove.setApproveResult(CaseApproveResultEnum.PASS.getCode());
//        caseApprove.setBeginTime(DateHelper.currentTime());
//        caseApprove.setEndTime(DateHelper.currentTime());
//        caseApprove.setApproveStatus(CommonOppositeEnum.DONE.getCode());
//        caseApproveMapper.insertSelective(caseApprove);
    }

}
