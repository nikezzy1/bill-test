package com.lingsi.unp.controller.apply;


import com.lingsi.unp.dto.NotarialPaperTypeDto;
import com.lingsi.unp.mapper.cases.NotarialPaperFilePathMapper;
import com.lingsi.unp.model.cases.CaseApply;
import com.lingsi.unp.model.cases.NotarialPaperFilePath;
import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.service.cases.CaseApplyService;
import com.lingsi.unp.service.cases.bean.CaseApplyUpdateSelectiveDto;
import com.lingsi.unp.service.cases.enums.CaseStatusEnum;
import com.lingsi.unp.service.notarial.NotarialPaperTypeService;
import com.lingsi.unp.service.notarial.appupload.AppPhotoUpLoadService;
import com.lingsi.unp.service.notarial.myenum.MyEnum;
import com.lingsi.unp.utils.base.SpringUtils;
import com.lingsi.unp.utils.io.file.FilePath;
import com.lingsi.unp.utils.io.file.MultiFileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author suntao
 */
@Api(value="拍照上传",tags = {"拍照上传"})
@Slf4j
@RestController
@RequestMapping(value = "/notarial/photo")
public class AppPhotoUpLoad_Controller {
 

    @Autowired
    AppPhotoUpLoadService appPhotoUpLoadService;

    @Autowired
    NotarialPaperTypeService notarialPaperTypeService;

    @Autowired
    CaseApplyService caseApplyService;

   /* @RequestMapping(value = "/upload",method= RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="公证拍照服务-提交照片成功",notes = "公证拍照服务-提交照片成功")
    public CommonRes photoUpLoad(NotarialApplyRequestBody requestBody){
        try {
            if( appPhotoUpLoadService.upLoadPhotos(new Long(1),"123"))
                return CommonRes.http_ok("提交照片成功").success();
            else{
                return CommonRes.http_ok("提交照片失败").fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }*/

    /**
     * 待取证案件
     * @return
     * @Version 20190529 wuzu response新增案件申请人， 案件取证地址
     */
    @RequestMapping(value = "/tasklist",method= RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="公证拍照服务-查询拍照列表",notes = "公证拍照服务-查询拍照列表")
    public CommonRes getTasklist(){
        return CommonRes.http_ok("查询成功",appPhotoUpLoadService.getPhotoTaskList()).success();
    }

    @PostMapping("/attachments/{caseId}/{paperNo}/{itemNo}")
    @ApiOperation(value = "上传拍照new", notes = "上传拍照new")
    public CommonRes  uploadAttachment(
            @ApiParam(value = "caseId", example = "10") @PathVariable long caseId,
//            @ApiParam(value = "附件类别（APONE：信用卡申请表；APTWO：信用卡领用合约补充条款）",
//                    allowableValues = "APONE, APTWO", example = "ID") @PathVariable String fileItem,
            @ApiParam(value = "paperNo", example = "AP01") @PathVariable String paperNo,
            @ApiParam(value = "itemNo", example = "1") @PathVariable int itemNo,
            @ApiParam(value = "要上传的文件") @RequestParam("file") MultipartFile file) {

        log.info("attachments = " + caseId + "#" + paperNo + "#" + itemNo);
        if (file.isEmpty()) {
            return CommonRes.http_ok("上传文件为空").fail();
        }
        String fileName="";

        CaseApply caseApply = caseApplyService.selectByPrimaryKey(caseId);

        NotarialPaperTypeDto notarialPaperTypeDto = notarialPaperTypeService.selectByNo(caseApply.getPersonIdInvolved(), caseApply.getBusinessType(), paperNo);
        fileName = notarialPaperTypeDto.getPaperName();
        String fileType = notarialPaperTypeDto.getPaperType();

        //String fileId = file.getOriginalFilename();  // 文件名字

        String fileId= FilePath.getFileName();
        String savePath = fileId
                +FilePath.getpostfix(file.getOriginalFilename());
        String savePathNew = fileId + "_compress"
                +FilePath.getpostfix(file.getOriginalFilename());
        String fullPath = FilePath.getTemplateFilePath(MyEnum.AppendixFileSavePath)+fileId
                +FilePath.getpostfix(file.getOriginalFilename());
        log.info("fullpath=" + fullPath);
        String fullPathNew = FilePath.getTemplateFilePath(MyEnum.AppendixFileSavePath)+fileId + "_compress"
                +FilePath.getpostfix(file.getOriginalFilename());
        log.info("fullpathnew=" + fullPathNew);
        try {
            MultiFileUtils.multipartFileToFile(file, fullPath);
            try {

                Thumbnails.of(fullPath)
                        .size(2600, 2600)
                        .toFile(fullPathNew);
            }catch (Exception eee)
            {
                eee.printStackTrace();
            }

            NotarialPaperFilePath record = new NotarialPaperFilePath();
            record.setCaseId(caseId);
            record.setFileName(fileName);
            record.setFilePath( savePathNew );
            record.setFileType(fileType);
            record.setItemNo(itemNo);
            return appPhotoUpLoadService.recordAppendixFile(record);
        } catch (Exception e) {
            return CommonRes.http_error(e.getMessage());
        }
    }

    @PostMapping(value = "/pictured/{caseId}")
    @ApiOperation(value = "取证结束")
    public CommonRes pictured(@ApiParam(value = "caseId", example = "10") @PathVariable Long caseId)
    {
        try {
            CaseApply caseApply = caseApplyService.selectByPrimaryKey(caseId);
            String caseStatus = caseApply.getCaseStatus();

            CaseApplyUpdateSelectiveDto dto = new CaseApplyUpdateSelectiveDto();
            dto.setId(caseId);
            if (CaseStatusEnum.PRE_PICTURE.getCode().equals(caseStatus)) {

                //校验图片数量
                List<NotarialPaperTypeDto> typeList = notarialPaperTypeService.selectAp(caseApply.getPersonIdInvolved(), caseApply.getBusinessType());
                int pageNeed = 0;
                for (NotarialPaperTypeDto typeDto : typeList)
                {
                    pageNeed += typeDto.getPageCount();
                }

                NotarialPaperFilePathMapper notarialPaperFilePathMapper = SpringUtils.getBean(NotarialPaperFilePathMapper.class);
                List<NotarialPaperFilePath> records = notarialPaperFilePathMapper.selectByCaseId(caseId);
                log.info("pageNeed = " + pageNeed + ",pageExist = " + records.size());
                if (records.size() != pageNeed)
                {
                    return CommonRes.http_ok("取证未完成，请继续取证").fail();
                }

                //待受理
                dto.setOldCaseStatus(CaseStatusEnum.PRE_PICTURE.getCode());
                //下一阶段 待拍照
                dto.setCaseStatus(CaseStatusEnum.PICTURED.getCode());

            } else {
                return CommonRes.http_ok("非法请求").fail();
            }

            //更新数据
            caseApplyService.updateByStatusSelective(dto);

            return CommonRes.http_ok("处理成功").success();
        }catch (Exception e)
        {
            return CommonRes.http_error("异常，请联系管理员");
        }
    }
}
