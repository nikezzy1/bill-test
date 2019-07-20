package com.lingsi.unp.controller.cases;

import com.alibaba.fastjson.JSON;
import com.lingsi.unp.controller.cases.bean.CaseApplyVo;
import com.lingsi.unp.exception.DMException;
import com.lingsi.unp.model.auth.AppUser;
import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.service.cases.CaseApplyService;
import com.lingsi.unp.service.cases.bean.CaseApplySelectiveDto;
import com.lingsi.unp.service.cases.bean.CaseApprovedSelectiveDto;
import com.lingsi.unp.service.cases.enums.CaseStatusEnum;
import com.lingsi.unp.utils.auth.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 案件查询
 * @author: Wuzu
 * @create: 2019-04-24 11:10
 **/
@Api(value="案件查询",tags = {"案件管理 -- 案件查询"},position= 1 )
@RestController
@RequestMapping(value = "/api/case/query")
public class CaseQuery_Controller {

    @Autowired
    CaseApplyService caseApplyService;

    @RequestMapping(value = "/preReceive",method= RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="受理 -- 待受理",notes = "", position = 1)
    public CommonRes preReceive(){

        try {
            CaseApplySelectiveDto dto = new CaseApplySelectiveDto();
            dto.setCaseStatus(CaseStatusEnum.PRE_RECEIVE.getCode().split("#"));

            List<CaseApplyVo> caseApplyVoList = caseApplyService.selectBySelective(dto);

            System.out.println(JSON.toJSONString(caseApplyVoList));
            return CommonRes.http_ok("查询成功", caseApplyVoList).success();
        }catch (DMException dme)
        {
            dme.printStackTrace();
            return CommonRes.http_ok("查询错误" + "," + dme.getMessage()).fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            return CommonRes.http_error("查询异常:" + e.getMessage()).fail();
        }
    }


    @RequestMapping(value = "/picturing",method= RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="受理 -- 待拍照",notes = "", position = 2)
    public CommonRes picturing(){

        try {
            CaseApplySelectiveDto dto = new CaseApplySelectiveDto();
            dto.setCaseStatus((CaseStatusEnum.PRE_PICTURE.getCode() + "#" + CaseStatusEnum.PICTURED.getCode()).split("#"));

            List<CaseApplyVo> caseApplyVoList = caseApplyService.selectBySelective(dto);

            System.out.println(JSON.toJSONString(caseApplyVoList));
            return CommonRes.http_ok("查询成功", caseApplyVoList).success();
        }catch (DMException dme)
        {
            dme.printStackTrace();
            return CommonRes.http_ok("查询错误" + "," + dme.getMessage()).fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            return CommonRes.http_error("查询异常:" + e.getMessage()).fail();
        }
    }


    @RequestMapping(value = "/submitted",method= RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="受理 -- 已处理",notes = "", position = 3)
    public CommonRes submitted(){

        try {
            CaseApplySelectiveDto dto = new CaseApplySelectiveDto();
            dto.setCaseStatus((CaseStatusEnum.PRE_APPROVE.getCode() + "#" + CaseStatusEnum.APPROVED.getCode() + "#" + CaseStatusEnum.PRE_SEND.getCode() + "#" + CaseStatusEnum.SENDED.getCode()).split("#"));

            List<CaseApplyVo> caseApplyVoList = caseApplyService.selectBySelective(dto);

            //展示案件当前真实状态
//            for (CaseApplyVo vo : caseApplyVoList)
//            {
//                vo.setCaseStatus(CaseStatusEnum.SUBMITTED.getDisplay());
//            }

            System.out.println(JSON.toJSONString(caseApplyVoList));
            return CommonRes.http_ok("查询成功", caseApplyVoList).success();
        }catch (DMException dme)
        {
            dme.printStackTrace();
            return CommonRes.http_ok("查询错误" + "," + dme.getMessage()).fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            return CommonRes.http_error("查询异常:" + e.getMessage()).fail();
        }
    }


    @RequestMapping(value = "/preApprove",method= RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="审批 -- 待处理",notes = "", position = 4)
    public CommonRes preApprove(){

        try {
            CaseApplySelectiveDto dto = new CaseApplySelectiveDto();
            dto.setCaseStatus(CaseStatusEnum.PRE_APPROVE.getCode().split("#"));

            List<CaseApplyVo> caseApplyVoList = caseApplyService.selectBySelective(dto);

            System.out.println(JSON.toJSONString(caseApplyVoList));
            return CommonRes.http_ok("查询成功", caseApplyVoList).success();
        }catch (DMException dme)
        {
            dme.printStackTrace();
            return CommonRes.http_ok("查询错误" + "," + dme.getMessage()).fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            return CommonRes.http_error("查询异常:" + e.getMessage()).fail();
        }
    }

    @RequestMapping(value = "/approved",method= RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="审批 -- 已处理",notes = "", position = 5)
    public CommonRes approveHistory(){

        try {
            AppUser appUser = UserUtils.getCurrentUser();
            CaseApprovedSelectiveDto dto = new CaseApprovedSelectiveDto();
            dto.setUserId(appUser.getUserid());
//            dto.setRoleId(appUser.getRoles().get(0).getRoleid());
            List<CaseApplyVo> caseApplyVoList = caseApplyService.selectApprovedSelective(dto);
            //展示案件真实状态
//            for (CaseApplyVo vo : caseApplyVoList)
//            {
//                vo.setCaseStatus(CaseStatusEnum.APPROVED.getDisplay());
//            }
            System.out.println(JSON.toJSONString(caseApplyVoList));
            return CommonRes.http_ok("查询成功", caseApplyVoList).success();
        }catch (DMException dme)
        {
            dme.printStackTrace();
            return CommonRes.http_ok("查询错误" + "," + dme.getMessage()).fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            return CommonRes.http_error("查询异常:" + e.getMessage()).fail();
        }
    }


    @RequestMapping(value = "/preCertificate",method= RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="制证 -- 待处理",notes = "", position = 6)
    public CommonRes preCertificate(){

        try {
            CaseApplySelectiveDto dto = new CaseApplySelectiveDto();
            dto.setCaseStatus(CaseStatusEnum.APPROVED.getCode().split("#"));

            List<CaseApplyVo> caseApplyVoList = caseApplyService.selectBySelective(dto);
            //展示案件真实状态，不再转换
//            for (CaseApplyVo vo : caseApplyVoList)
//            {
//                vo.setCaseStatus(CaseStatusEnum.PRE_CERTIFICATE.getDisplay());
//            }

            System.out.println(JSON.toJSONString(caseApplyVoList));
            return CommonRes.http_ok("查询成功", caseApplyVoList).success();
        }catch (DMException dme)
        {
            dme.printStackTrace();
            return CommonRes.http_ok("查询错误" + "," + dme.getMessage()).fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            return CommonRes.http_error("查询异常:" + e.getMessage()).fail();
        }
    }


    @RequestMapping(value = "/certificated",method= RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="制证 -- 已制证",notes = "", position = 7)
    public CommonRes certificated(){

        try {
            CaseApplySelectiveDto dto = new CaseApplySelectiveDto();
            dto.setCaseStatus((CaseStatusEnum.PRE_SEND.getCode() + "#" + CaseStatusEnum.SENDED.getCode()).split("#"));

            List<CaseApplyVo> caseApplyVoList = caseApplyService.selectBySelective(dto);
//            for (CaseApplyVo vo : caseApplyVoList)
//            {
//                vo.setCaseStatus(CaseStatusEnum.CERTIFICATED.getDisplay());
//            }

            System.out.println(JSON.toJSONString(caseApplyVoList));
            return CommonRes.http_ok("查询成功", caseApplyVoList).success();
        }catch (DMException dme)
        {
            dme.printStackTrace();
            return CommonRes.http_ok("查询错误" + "," + dme.getMessage()).fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            return CommonRes.http_error("查询异常:" + e.getMessage()).fail();
        }
    }


    @RequestMapping(value = "/preSend",method= RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="送达 -- 待送达",notes = "", position = 8)
    public CommonRes preSend(){

        try {
            CaseApplySelectiveDto dto = new CaseApplySelectiveDto();
            dto.setCaseStatus(CaseStatusEnum.PRE_SEND.getCode().split("#"));

            List<CaseApplyVo> caseApplyVoList = caseApplyService.selectBySelective(dto);

            System.out.println(JSON.toJSONString(caseApplyVoList));
            return CommonRes.http_ok("查询成功", caseApplyVoList).success();
        }catch (DMException dme)
        {
            dme.printStackTrace();
            return CommonRes.http_ok("查询错误" + "," + dme.getMessage()).fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            return CommonRes.http_error("查询异常:" + e.getMessage()).fail();
        }
    }


    @RequestMapping(value = "/sended",method= RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="送达 -- 已送达",notes = "", position = 9)
    public CommonRes sended(){

        try {
            CaseApplySelectiveDto dto = new CaseApplySelectiveDto();
            dto.setCaseStatus(CaseStatusEnum.SENDED.getCode().split("#"));

            List<CaseApplyVo> caseApplyVoList = caseApplyService.selectBySelective(dto);

            System.out.println(JSON.toJSONString(caseApplyVoList));
            return CommonRes.http_ok("查询成功", caseApplyVoList).success();
        }catch (DMException dme)
        {
            dme.printStackTrace();
            return CommonRes.http_ok("查询错误" + "," + dme.getMessage()).fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            return CommonRes.http_error("查询异常:" + e.getMessage()).fail();
        }
    }

}
