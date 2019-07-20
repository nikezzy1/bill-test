package com.lingsi.unp.controller.cases;

import com.alibaba.fastjson.JSON;
import com.lingsi.unp.controller.cases.bean.CaseImageVo;
import com.lingsi.unp.exception.DMException;
import com.lingsi.unp.model.cases.CaseApply;
import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.service.cases.CaseApplyService;
import com.lingsi.unp.service.notarial.NotarialPapersMaker;
import com.lingsi.unp.utils.io.file.Base64Convert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @description: 案件资料
 * @author: Wuzu
 * @create: 2019-04-24 11:10
 **/
@Api(value="案件资料",tags = {"案件管理 -- 案件资料"},position= 1 )
@RestController
@RequestMapping(value = "/api/case/paper")
public class CasePaper_Controller {

    private Logger logger = LogManager.getLogger();

    @Autowired
    CaseApplyService caseApplyService;

    @RequestMapping(value = "/notary",method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="审批 -- 公证书")
    public CommonRes notary(@RequestParam Long id){

        try {
            //query case_apply
            CaseApply caseApply = caseApplyService.selectByPrimaryKey(id);
            //generate notary paper html
            String html = NotarialPapersMaker.getNotarialHtml(caseApply);

            return CommonRes.http_ok("查询成功", html).success();
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


    @RequestMapping(value = "/applyTable",method= RequestMethod.GET,produces = MediaType.APPLICATION_PDF_VALUE)
    @ApiOperation(value="审批 -- 申请表")
    public void applyTable(@RequestParam Long id, HttpServletResponse response){

        logger.info(id);
        logger.info("================ pdf start ================");

        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String path = NotarialPapersMaker.getNotarialPaperfilePath(id);

            logger.info("================ path = " + path + " ================");

            inputStream = new FileInputStream(new File(path));

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

            logger.info("================ pdf end ================");
        }
    }


    @RequestMapping(value = "/dataList",method= RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="审批 -- 材料清单")
    public CommonRes dataList(@RequestParam(required = true) Long id){

        try {
            List<CaseImageVo> caseImageVos = NotarialPapersMaker.getAppendixFileListForShow(id);

            logger.info(JSON.toJSONString(caseImageVos));
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

            String path = NotarialPapersMaker.getAppendixFilePath(filePath);
            response.getWriter().write(Base64Convert.GetImageStr(path));

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
