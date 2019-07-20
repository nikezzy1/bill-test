package com.lingsi.unp.controller.apply;


import com.alibaba.fastjson.JSON;
import com.lingsi.unp.dto.NotarialPaperTypeDto;
import com.lingsi.unp.exception.DMException;
import com.lingsi.unp.model.apply.NotarialApplyRequestBody;
import com.lingsi.unp.model.auth.AppUser;
import com.lingsi.unp.model.cases.CaseApply;
import com.lingsi.unp.model.notarial.NotarialPaperType;
import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.model.response.CommonResEnum;
import com.lingsi.unp.service.auth.AppRoleUserService;
import com.lingsi.unp.service.auth.AppUserService;
import com.lingsi.unp.service.cases.CaseApplyService;
import com.lingsi.unp.service.cases.enums.CaseTypeEnum;
import com.lingsi.unp.service.notarial.NotarialPaperTypeService;
import com.lingsi.unp.service.notarial.NotarialPapersMaker;
import com.lingsi.unp.service.notarial.NotarialPapersMakerUtils;
import com.lingsi.unp.service.notarial.myenum.MyEnum;
import com.lingsi.unp.utils.io.ZipMultiFile;
import com.lingsi.unp.utils.io.file.FilePath;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Api(value="公证申请服务",tags = {"公证申请服务"})
@RestController
public class NotarialApply_Controller {

    private Logger logger = LogManager.getLogger();

    @Autowired
    CaseApplyService caseApplyService;

    @Autowired
    NotarialPaperTypeService notarialPaperTypeService;

    @Autowired
    AppRoleUserService appRoleUserService;

    public static final String verification = "verification";

    /**
     * csp系统发起公证申请
     * @param requestBody
     * @return
     * @Version 20190519 增加案由caseDispute, 取证地址 involvedAddress 字段 必输
     */
    @RequestMapping(value = "/notarial/apply",method= RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="公证申请服务-提交公证申请",notes = "公证申请服务-提交公证申请")
    public CommonRes notarialApply(@RequestBody  NotarialApplyRequestBody requestBody ){

        logger.info(JSON.toJSONString(requestBody));
        try {

            //todo 临时改为统一成功，无任何操作
            //申请流水号是否已存在
            CaseApply existsCase = caseApplyService.selectByApplySerialNo(requestBody.getApplySerialno());
            if (existsCase != null && existsCase.getId() != null)
            {
                if (existsCase.getPersonIdInvolved().equals(requestBody.getPersonIdInvolved())
                        || existsCase.getRespondent().equals(requestBody.getRespondent())
                        || existsCase.getRespondentDataId().equals(requestBody.getRespondentdataId())) {
                    throw new Exception("请勿重复提交");
                } else {
                    throw new Exception("该流水号已经占用，请保持流水号在系统内唯一");
                }
            }

            CaseApply caseApply = new CaseApply();
            caseApply.setApplySerialNo(requestBody.getApplySerialno());
            caseApply.setPersonIdInvolved(requestBody.getPersonIdInvolved());
            caseApply.setBusinessType(requestBody.getBusinessType());
            caseApply.setRespondent(requestBody.getRespondent());
            caseApply.setRespondentId(requestBody.getRespondentId());
            caseApply.setRespondentDataId(requestBody.getRespondentdataId());
            caseApply.setCaseDispute(requestBody.getCaseDispute());
            caseApply.setInvolvedAddress(requestBody.getInvolvedAddress());
            caseApply.setReceiverName("练力");
            caseApply.setCaseType(CaseTypeEnum.PRESERVATION.getCode());
            caseApplyService.insert(caseApply);


            return CommonRes.http_ok("提交公证申请成功").success();
        }catch (DMException dme)
        {
            dme.printStackTrace();
            return CommonRes.http_ok(dme.getMessage()).fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            return CommonRes.http_error("提交工作申请异常 || " + e.getMessage()).fail();
        }

    }

    @RequestMapping(value = "/notarial/query",method= {RequestMethod.GET,RequestMethod.POST},produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="公证申请服务-查询公证结果",notes = "公证申请服务-查询公证结果")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="applySerialno",value ="申请流水号,系统唯一",required = true,dataType = "String")
    })
    public CommonRes notarialQuery(String applySerialno){
        System.out.println("applySerialno="+applySerialno);
        try {
            if(caseApplyService.selectByApplySerialNo(applySerialno)==null){
                return CommonRes.http_ok("不存在该流水").fail(CommonResEnum.NOTFOUND);
            }
            return caseApplyService.getNotarialResult(applySerialno);
        } catch (IOException e) {
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage()).fail();
        }

    }

//    @RequestMapping(value = "/notarial/pdf", method= RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
//    @ApiOperation(value="公证申请服务-查询公证书",notes = "公证申请服务-查询公证书")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query",name="applySerialno",value ="申请流水号,系统唯一",required = true,dataType = "String")
//
//    })
//    public void notarialPdf(String applySerialno, HttpServletResponse response) throws Exception{
//
//        System.out.println("applySerialno="+applySerialno);
//
//        caseApplyService.getNotarialPdf(applySerialno, response.getOutputStream());
//    }

    @RequestMapping(value = "/notarial/np/make",method= RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="公证服务-制作公证书",notes = "公证申请服务-获取文件")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="id",value ="id",required = true,dataType = "String")
    })
    public String getFile(String id) throws IOException {

        CaseApply cas = caseApplyService.selectByPrimaryKey(Long.valueOf(id));
        NotarialPaperTypeDto notarialPaperTypeDto = notarialPaperTypeService.selectNp(cas.getPersonIdInvolved(), "");
        try {
            NotarialPapersMaker.notarialPaperHandle(cas, notarialPaperTypeDto);
            String re = NotarialPapersMaker.getNotarialPaperfilePath(Long.valueOf(id));
            return re;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }




    @RequestMapping(value = "/notarial/files",method= RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="公证服务 -- 文件列表")
    public CommonRes dataList(@RequestParam(required = true) String applySerialNo){

        try {
            CaseApply caseApply = caseApplyService.selectByApplySerialNo(applySerialNo);
            Map fileNameMap = NotarialPapersMakerUtils.selectCaseFiles(caseApply.getId());
            Iterator it = fileNameMap.keySet().iterator();
            List nameList = new ArrayList();
            while(it.hasNext())
            {
                nameList.add(fileNameMap.get(it.next()));
            }
            return CommonRes.http_ok("查询成功", nameList).success();
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



    @RequestMapping(value = "/notarial/file/zip", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="公证服务 -- 文件压缩包")
    public void fileZip(@RequestParam(required = true) String applySerialNo, HttpServletResponse response) throws Exception{

        applySerialNo = "21672955776053248";

        CaseApply caseApply = caseApplyService.selectByApplySerialNo(applySerialNo);

        Map newFileNameMap = NotarialPapersMakerUtils.selectCaseFiles(caseApply.getId());

        File[] srcFiles = new File[newFileNameMap.size()];

        logger.info("fileNames========");

        int i = 0;
        Iterator it = newFileNameMap.keySet().iterator();
        while (it.hasNext())
        {
            String key = it.next().toString();
            String value = newFileNameMap.get(key).toString();
            String filePath = "";
            if (key.endsWith("pdf"))
            {
                filePath = FilePath.getFileRootPath() + MyEnum.NotarialPdfSavePath.getValue() + key;
            }else {
                filePath = FilePath.getFileRootPath() + MyEnum.AppendixFileSavePath.getValue() + key;
            }

            System.out.println(filePath);
            srcFiles[i++] = new File(filePath);
        }
        logger.info("fileNames========");



        logger.info("================ zip start ================");

        String zipFilePath = FilePath.getFileRootPath() + MyEnum.NotarialZipSavePath.getValue() + applySerialNo + ".zip";
        ZipMultiFile.zipFiles(srcFiles, new File(zipFilePath), newFileNameMap);

        logger.info("================ zip end ================");

        logger.info("================ zip file out start ================");
        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {

            inputStream = new FileInputStream(new File(zipFilePath));

            outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len;

            while ((len = inputStream.read(bytes)) != -1)
            {
                outputStream.write(bytes, 0, len);
            }
            outputStream.flush();

            logger.info("================ zip file out end ================");

            try {
                new File(zipFilePath).delete();
            }catch (Exception fe){
                fe.printStackTrace();
            }
            logger.info("================== file deleted ==================");
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if (inputStream != null)
            {
                try {
                    inputStream.close();
                }catch (Exception e)
                {
                    e.getMessage();
                }

                try {
                    outputStream.close();
                }catch (Exception e)
                {
                    e.getMessage();
                }
            }
        }

    }

    @RequestMapping(value = "/notarial/file/pdf",method= RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    @ApiOperation(value="公证服务 -- 文件列表")
    public void pdfDownLoad(@RequestParam(required = true) String filePath, HttpServletResponse response) throws Exception{

        if (filePath.endsWith("pdf"))
        {
            filePath = FilePath.getFileRootPath() + "/" + MyEnum.NotarialPdfSavePath.getValue() + "/" + filePath;
        }else {
            filePath = FilePath.getFileRootPath() + "/" + MyEnum.AppendixFileSavePath.getValue() + "/" + filePath;
        }

        System.out.println(filePath);

        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {

            inputStream = new FileInputStream(new File(filePath));

            outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len;

            while ((len = inputStream.read(bytes)) != -1)
            {
                outputStream.write(bytes, 0, len);
            }
            outputStream.flush();
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if (inputStream != null)
            {
                try {
                    inputStream.close();
                }catch (Exception e)
                {
                    e.getMessage();
                }

                try {
                    outputStream.close();
                }catch (Exception e)
                {
                    e.getMessage();
                }
            }
        }

    }


}
