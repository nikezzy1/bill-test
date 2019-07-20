package com.lingsi.unp.controller.auth;


import com.lingsi.unp.exception.DMException;
import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.service.auth.AppRoleUserService;
import com.lingsi.unp.service.auth.AppUserService;
import com.lingsi.unp.utils.auth.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth/appuser" )
@Api(value = "用户管理 | 对用户信息的增删改查",tags = {"用户基础服务-用户增删改查"},position= 4)
public class AppUser_Controller {
    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppRoleUserService appRoleUserService;

    @RequestMapping(value = "/add",method= RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="新增用户",notes = "userid为用户的登陆账号")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="userId",value ="登陆id",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="userName",value ="用户姓名",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="passWord",value ="用户密码",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="idCardNo",value ="身份证号码",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="telephone",value ="手机号",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="companyName",value ="公司名称",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="companyLicenseNo",value ="公司营业执照/统一社会信用代码",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="grantedCode",value ="授权码",required = true,dataType = "String")
    })
    public CommonRes addUser(String userId, String userName, String passWord, String idCardNo, String telephone, String companyName, String companyLicenseNo, String grantedCode){
        try{
            if(appUserService.addUser(userId,userName,passWord,idCardNo,telephone,companyName,companyLicenseNo,grantedCode)==1) {
                return CommonRes.http_ok("新增用户成功").success();
            } else {
                return CommonRes.http_ok("新增用户失败").fail();
            }
        }catch (DMException e){
            return CommonRes.http_ok("新增用户失败|"+e.getMessage()).fail();
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error("新增用户异常");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/query",method= RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="查询用户",notes = "userid为用户的登陆账号")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="userId",value ="登陆id",required = true,dataType = "String")
    })
    public CommonRes getUser(String userId){
        try {
            return CommonRes.http_ok("查询用户成功",appUserService.selectByPrimaryKey(userId)).success();
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }



    @ResponseBody
    @RequestMapping(value = "/queryall",method= RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="获取所有用户",notes = "获取所有用户")
    public CommonRes getAllUsers(){
        try {
            return CommonRes.http_ok("查询用户成功",appUserService.getAllUsers()).success();
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }


    @ResponseBody
    @RequestMapping(value = "/update",method= RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="修改用户资料",notes = "userid为用户的登陆账号")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="userId",value ="登陆id",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="userName",value ="用户姓名",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="idCardNo",value ="身份证号码",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="telephone",value ="手机号",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="companyName",value ="公司名称",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="companyLicenseNo",value ="公司营业执照/统一社会信用代码",required = false,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="grantedCode",value ="授权码",required = true,dataType = "String")
    })
    public CommonRes updateUser(String userId,String userName,String idCardNo,String telephone,String companyName,String companyLicenseNo,String grantedCode){
        try {
            return CommonRes.http_ok("修改用户资料成功",appUserService.updateUserDetail(userId,userName,idCardNo,telephone,companyName,companyLicenseNo,grantedCode)).success();
        }catch (DMException e){
            return CommonRes.http_ok("删除失败|"+e.getMessage()).fail();
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method= RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="删除用户",notes = "userid为用户的登陆账号")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="userId",value ="登陆id",required = true,dataType = "String")
    })
    public CommonRes deleteUser(String userId){
        try {
            if(appUserService.deleteUser(userId)==1) {
                return CommonRes.http_ok("删除成功").success();
            } else {
                return CommonRes.http_ok("删除失败").fail();
            }
        }catch (DMException e){
            return CommonRes.http_ok("删除失败|"+e.getMessage()).fail();
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/password/modify",method= RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="用户修改自己的密码",notes = "用户修改自己的密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="oldPassWord",value ="原密码",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="newPassWord",value ="新密码",required = true,dataType = "String")

    })
    public CommonRes updateUserPassWord(String oldPassWord,String newPassWord){
        try {
            if(appUserService.updateUserPassWord(UserUtils.getCurrentUser().getUserid(), oldPassWord, newPassWord)==1) {
                return CommonRes.http_ok("用户修改密码成功").success();
            } else {
                return CommonRes.http_ok("用户修改密码失败").fail();
            }
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/password/reset",method= RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="管理员重置用户密码",notes = "管理员重置用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="userId",value ="登陆id",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="newPassWord",value ="新密码",required = true,dataType = "String")
    })
    public CommonRes resetUserPassWord(String userId,String newPassWord){
        try {
            if(appUserService.resetUserPassWord(userId,  newPassWord)==1) {
                return CommonRes.http_ok("修改用户密码成功").success();
            } else {
                return CommonRes.http_ok("修改用户密码失败").fail();
            }
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/role/grant",method= RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="给用户分配角色",notes = "给用户分配角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="userId",value ="登陆id",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="roleId",value ="角色编号",required = true,dataType = "String")

    })
    public CommonRes addRoleUser(String  userId,String roleId){
        try {
            appRoleUserService.addRoleUser(userId,roleId);
            return CommonRes.http_ok("给用户分配角色").success();
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/role/revoke",method= RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="收回用户角色",notes = "收回用户角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="userId",value ="登陆id",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="roleId",value ="角色编号",required = true,dataType = "String")

    })
    public CommonRes deleteRoleUser(String  userId,String roleId){
        try {
            appRoleUserService.deleteRoleUser(userId,roleId);
            return CommonRes.http_ok("收回用户角色").success();
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }

}
