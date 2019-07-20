package com.lingsi.unp.controller.auth;


import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.service.auth.SystemLoginAuthService;
import com.lingsi.unp.service.msg.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Api(value="基础服务",tags = {"用户基础服务-登陆退出"})
@RestController
public class SystemLoginAuth {

    @Autowired
    private SystemLoginAuthService systemLoginAuthService;

    public static final String verification = "verification";

    @RequestMapping(value = "/selflogin",method= RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="用户登陆",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="verification",value ="验证码",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="userId",value ="登陆ID",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="passWord",value ="用户密码",required = true,dataType = "String")
    })
    public void selfLogin(){
        System.out.println("here  selfLogin");
    }

    @RequestMapping(value = "/selflogout",method= RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="用户注销登陆",notes = "")
    public void selfLogout(){
        System.out.println("here  selfLogout");
    }

    @RequestMapping(value = "/selfactivate",method= RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="账户激活",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="userName",value ="用户姓名",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="idCardNo",value ="身份证号码",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="telephone",value ="手机号",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="companyName",value ="公司名称",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="grantedCode",value ="授权码",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="massageCode",value ="短信验证码",required = true,dataType = "String")
    })
    public CommonRes enableAccount(String userName, String idCardNo, String telephone, String companyName, String grantedCode, String massageCode){
        try{
            if(!MessageService.checkMessageCode(telephone,massageCode)){
                return CommonRes.http_ok("短信验证码错误").fail();
            }
            String userId = systemLoginAuthService.verifyUserDetails(userName,idCardNo,telephone,companyName,grantedCode,massageCode);
            if(userId!=null) {
                if(systemLoginAuthService.enbleUser(userId)==1)
                    return CommonRes.http_ok("账户激活成功").success();
                else
                    return CommonRes.http_ok("账户激活失败").fail();
            }
            else
                return CommonRes.http_ok("账户激活失败").fail();

        }catch(Exception e){
            e.printStackTrace();
            return CommonRes.http_error("账户激活失败");
        }
    }


    @RequestMapping(value = "/selfactivate/sendmessagecode",method= RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="发送短信验证码",notes = "发送短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="telephone",value ="手机号",required = true,dataType = "String")
    })
    public CommonRes sendMessageCode(String telephone){
        String verifyCode = String
                .valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
        try {
            if (MessageService.sendMessageCode(telephone, verifyCode))
                return CommonRes.http_ok("发送成功").success();
            else
                return CommonRes.http_ok("发送失败").fail();
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }
}
