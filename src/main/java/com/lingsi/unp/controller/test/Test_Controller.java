package com.lingsi.unp.controller.test;

import com.lingsi.unp.controller.cases.bean.CaseImageVo;
import com.lingsi.unp.exception.DMException;
import com.lingsi.unp.model.cases.CaseApply;
import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.service.cases.CaseApplyService;
import com.lingsi.unp.service.cases.bean.CaseApplyUpdateSelectiveDto;
import com.lingsi.unp.service.cases.enums.CaseStatusEnum;
import com.lingsi.unp.service.cases.enums.CaseTypeEnum;
import com.lingsi.unp.service.notarial.NotarialPapersMakerUtils;
import com.lingsi.unp.service.notarial.myenum.MyEnum;
import com.lingsi.unp.utils.base.DateHelper;
import com.lingsi.unp.utils.io.ZipMultiFile;
import com.lingsi.unp.utils.io.file.Base64Convert;
import com.lingsi.unp.utils.io.file.FilePath;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-04-25 08:51
 **/
@RestController
@RequestMapping("/test")
@Api(value = "测试", tags = {"测试"})
public class Test_Controller {

    Logger logger = LogManager.getLogger();

    @Autowired
    CaseApplyService caseApplyService;

    @RequestMapping(value = "/createCase", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "创建测试案件", notes = "")
    public CommonRes createCase(@RequestHeader Integer count){

        String date = DateHelper.currentDate().replaceAll("/", "");
        for (int k = 1; k <= count; k++)
        {
            CaseApply caseApply = new CaseApply();
            caseApply.setApplySerialNo(new StringBuffer(date).append(k).toString());
            caseApply.setCaseType(CaseTypeEnum.PRESERVATION.getCode());
            caseApply.setPersonIdInvolved("中国银行衢州分行");
            caseApply.setRespondent("被告人" + k);
            caseApply.setRespondentId("rid" + k);
            caseApply.setRespondentDataId("6008770010004012" + k);
            caseApply.setRespondentData("6008770010004012" + k);
            caseApply.setCaseStatus(CaseStatusEnum.PRE_RECEIVE.getCode());
            caseApplyService.insertSelective(caseApply);
        }

        return CommonRes.http_ok("模拟数据创建成功").success();
    }

    @RequestMapping(value = "/picture", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "案件拍照", notes = "")
    public CommonRes picture(@RequestBody List<Long> list){

        for (Long id : list)
        {
            CaseApplyUpdateSelectiveDto dto = new CaseApplyUpdateSelectiveDto();
            dto.setId(id);
            dto.setOldCaseStatus(CaseStatusEnum.PRE_PICTURE.getCode());
            dto.setCaseStatus(CaseStatusEnum.PICTURED.getCode());
            caseApplyService.updateByStatusSelective(dto);
        }

        return CommonRes.http_ok("更新为拍照成功").success();
    }


    @RequestMapping(value = "/applyTable",method= RequestMethod.GET,produces = MediaType.APPLICATION_PDF_VALUE)
    @ApiOperation(value="审批 -- 申请表")
    public void applyTable(@RequestParam Long id, HttpServletResponse response) {

        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            System.out.println("param id = " + id);
            String path = "/home/app/123.pdf";

            inputStream = new FileInputStream(new File(path));

            outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024 * 1024];
            int len;

            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
//            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.getMessage();
                }

                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }
    }


    @RequestMapping(value = "/dataList",method= RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="审批 -- 材料清单")
    public CommonRes dataList(@RequestParam(required = true) Long id){

        try {
            List<CaseImageVo> caseImageVos = new ArrayList<>();

            CaseImageVo imageVo = new CaseImageVo();
            imageVo.setFilePath(22 + "");
            imageVo.setShowName("信用卡申请表");
            caseImageVos.add(imageVo);

            CaseImageVo imageVo1 = new CaseImageVo();
            imageVo1.setFilePath(23 + "");
            imageVo1.setShowName("领用合约");
            caseImageVos.add(imageVo1);

            CaseImageVo imageVo2 = new CaseImageVo();
            imageVo2.setFilePath(24 + "");
            imageVo2.setShowName("被申请人身份证明");
            caseImageVos.add(imageVo2);

            CaseImageVo imageVo3 = new CaseImageVo();
            imageVo3.setFilePath(25 + "");
            imageVo3.setShowName("仲裁协议");
            caseImageVos.add(imageVo3);

            CaseImageVo imageVo4 = new CaseImageVo();
            imageVo4.setFilePath(26 + "");
            imageVo4.setShowName("信用卡交易明细");
            caseImageVos.add(imageVo4);

            return CommonRes.http_ok("查询成功", caseImageVos).success();
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



    @RequestMapping(value = "/image",method= RequestMethod.GET,produces = MediaType.IMAGE_PNG_VALUE)
    @ApiOperation(value="图片base64")
    public void imagePng(@RequestParam String filePath, HttpServletResponse response){
        try {

            String path = "";
            if ("22".equals( filePath ))
            {
                path = "/home/app/22.jpg";
            }else if ("23".equals(filePath))
            {
                path = "/home/app/23.png";
            }else if ("24".equals(filePath))
            {
                path = "/home/app/24.jpg";
            }else if ("25".equals(filePath))
            {
                path = "/home/app/25.gif";
            }else if ("26".equals(filePath))
            {
                path = "/home/app/26.gif";
            }

            response.getWriter().write(Base64Convert.GetImageStr(path));

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }




    @RequestMapping(value = "/zip", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="公证服务 -- 文件压缩包")
    public void fileZip( HttpServletResponse response){

        String zipFilePath = "D:/test/21697353413275648.zip";
        logger.info("================ zip file out ================");
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
            logger.info("================ zip file out end ================");

        }

    }

    @RequestMapping(value = "/testDownload", method = RequestMethod.GET)
    public void testDownload(@RequestParam(required = true) String applySerialNo, HttpServletResponse res) throws Exception{

        CaseApply caseApply = caseApplyService.selectByApplySerialNo(applySerialNo);

        Map newFileNameMap = NotarialPapersMakerUtils.selectCaseFiles(caseApply.getId());

        File[] srcFiles = new File[newFileNameMap.size()];

        logger.info("fileNames========");

        int k = 0;
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
            srcFiles[k++] = new File(filePath);
        }
        logger.info("fileNames========");



        logger.info("================ zip start ================");

        String zipFilePath = FilePath.getFileRootPath() + MyEnum.NotarialZipSavePath.getValue() + applySerialNo + ".zip";
        ZipMultiFile.zipFiles(srcFiles, new File(zipFilePath), newFileNameMap);

        logger.info("================ zip end ================");


        logger.info("================ zip file out start ================");

        String fileName = zipFilePath;
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


        logger.info("================ zip file out end ================");

    }
}
