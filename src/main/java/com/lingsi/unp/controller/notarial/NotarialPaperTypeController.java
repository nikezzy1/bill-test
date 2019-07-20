package com.lingsi.unp.controller.notarial;

import com.lingsi.unp.dto.vo.NotarialPaperTypeVo;
import com.lingsi.unp.model.notarial.NotarialPaperType;
import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.service.notarial.NotarialPaperTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-05-28 14:05
 **/
@Api(tags = "公证资料管理")
@RestController
@Slf4j
@RequestMapping(value = "/conf/notarial/paperType", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class NotarialPaperTypeController {

    @Autowired
    NotarialPaperTypeService notarialPaperTypeService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "新增")
    public CommonRes add(@RequestBody NotarialPaperTypeVo notarialPaperTypeVo)
    {
        try {
            if (1 == notarialPaperTypeService.add(notarialPaperTypeVo)) {
                return CommonRes.http_ok("新增成功").success();
            } else {
                return CommonRes.http_ok("新增失败").fail();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            log.info("================================================");
            log.error(e.getMessage());
            return CommonRes.http_error("异常，请联系管理员").fail();
        }
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "修改")
    public CommonRes update(@RequestBody NotarialPaperType notarialPaperType)
    {
        try {
            if (1 == notarialPaperTypeService.updateByPrimaryKeySelective(notarialPaperType)) {
                return CommonRes.http_ok("更新成功").success();
            } else {
                return CommonRes.http_ok("更新失败").fail();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            log.info("================================================");
            log.error(e.getMessage());
            return CommonRes.http_error("异常，请联系管理员").fail();
        }
    }

    @GetMapping(value = "/query")
    @ApiOperation(value = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="applySerialno",value ="申请流水号,系统唯一",required = true,dataType = "String")
    })
    public CommonRes query(@RequestParam(required = false) String personInvolved, @RequestParam(required = false) String businessType){
        try {
            return CommonRes.http_ok("查询成功", notarialPaperTypeService.selectAll(personInvolved, businessType)).success();
        }catch (Exception e)
        {
            e.printStackTrace();
            log.info("==================================================");
            log.error(e.getMessage());
            return CommonRes.http_error("异常，请联系管理员");
        }
    }
}
