package com.lingsi.unp.controller.cases;

import com.lingsi.unp.controller.cases.bean.*;
import com.lingsi.unp.exception.DMException;
import com.lingsi.unp.model.auth.AppUser;
import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.service.cases.CaseApplyService;
import com.lingsi.unp.service.cases.CaseApproveService;
import com.lingsi.unp.service.cases.bean.CaseApplyUpdateSelectiveDto;
import com.lingsi.unp.service.cases.enums.CaseApproveResultEnum;
import com.lingsi.unp.service.cases.enums.CaseStatusEnum;
import com.lingsi.unp.utils.auth.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 案件服务
 * @author: Wuzu
 * @create: 2019-04-24 11:10
 **/
@Api(value="案件处理",tags = {"案件管理 -- 案件处理"},position= 1 )
@RestController
@RequestMapping(value = "/api/case")
public class CaseApprove_Controller {

    @Autowired
    CaseApplyService caseApplyService;

    @Autowired
    CaseApproveService caseApproveService;


    @RequestMapping(value = "/batch/submit/pictured", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="受理 -- 已拍照 -- 处理",notes = "")
    public CommonRes submit(@RequestBody CasePicturedSubmitBatchDto casePicturedSubmitBatchDto){

        //todo 记录成功多少，失败多少，异常多少
        try {
            AppUser appUser = UserUtils.getCurrentUser();

            //一笔笔处理
            for (CaseSubmitDto caseSubmitDto : casePicturedSubmitBatchDto.getCaseSubmitDtoList()) {
                caseApproveService.submitPictured(casePicturedSubmitBatchDto, caseSubmitDto, appUser);
            }

            return CommonRes.http_ok("操作成功").success();
        }catch (DMException dme)
        {
            dme.printStackTrace();
            return CommonRes.http_ok("操作失败" + "," + dme.getMessage()).fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            return CommonRes.http_error("系统异常:" + e.getMessage()).fail();
        }
    }



    @RequestMapping(value = "/batch/submit", method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="受理 -- 处理 || 制证 -- 处理 || 送达 -- 处理",notes = "")
    public CommonRes submit(@RequestBody CaseSubmitBatchDto caseSubmitBatchDto){

        //todo 记录成功多少，失败多少，异常多少
        try {
            AppUser appUser = UserUtils.getCurrentUser();

            //一笔笔处理
            for (CaseSubmitDto caseSubmitDto : caseSubmitBatchDto.getCaseSubmitDtoList()) {
                caseApproveService.deal(caseSubmitDto, appUser);
            }

            return CommonRes.http_ok("操作成功").success();
        }catch (DMException dme)
        {
            dme.printStackTrace();
            return CommonRes.http_ok("操作失败" + "," + dme.getMessage()).fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            return CommonRes.http_error("系统异常:" + e.getMessage()).fail();
        }
    }


    @RequestMapping(value = "/batch/approve",method= RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="审批 -- 审批",notes = "")
    public CommonRes approve(@RequestBody CaseApproveBatchDto caseApproveBatchDto){

        //todo 记录成功多少，失败多少，异常多少
        try {
            AppUser appUser = UserUtils.getCurrentUser();

            int size = caseApproveBatchDto.getCaseApproveDtoList().size();
            for (CaseApproveDto caseApproveDto : caseApproveBatchDto.getCaseApproveDtoList()) {
                if (size > 1) {
                    //批量时，全部记为通过
                    caseApproveDto.setApproveResult(CaseApproveResultEnum.PASS.getCode());
                    if (caseApproveDto.getApproveMsg().length() == 0){
                        caseApproveDto.setApproveMsg("经审查符合出证条件，同意出证。");
                    }
                }
                caseApproveService.approve(caseApproveDto, appUser);
            }

            return CommonRes.http_ok("操作成功").success();
        }catch (DMException dme)
        {
            dme.printStackTrace();
            return CommonRes.http_ok("操作错误" + "," + dme.getMessage()).fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            return CommonRes.http_error("操作异常" + "," + e.getMessage()).fail();
        }
    }

}
