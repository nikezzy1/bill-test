package com.lingsi.unp.controller.auth;


import com.lingsi.unp.model.auth.AppRole;
import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.service.auth.AppRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value="角色管理",tags = {"用户基础服务-角色增删改查"},position= 3)
@RestController
@RequestMapping(value = "/api/auth/role")
public class AppRole_Controller {
    @Autowired
    AppRoleService appRoleService;

    @RequestMapping(value = "/add",method= RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="新增角色",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="roleId",value ="角色ID［唯一］",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="roleName",value ="角色名称",required = true,dataType = "String")
    })
    public CommonRes addRole(String roleName, String roleId){
        AppRole appRole = new AppRole();
        appRole.setRoleid(roleId);
        appRole.setRolename(roleName);
        appRole.setStatus("1");
        try{
            if(appRoleService.addRole(appRole)==1)
                return CommonRes.http_ok("新增角色成功").success();
            else
                return CommonRes.http_ok("新增角色失败").fail();
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error("新增角色异常");
        }
    }

    @RequestMapping(value = "/delete",method= RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="删除角色",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="roleId",value ="角色ID［唯一］",required = true,dataType = "String")

    })
    public CommonRes deleteRole(String roleId){
        try {
            appRoleService.deleteRole(roleId);
            return CommonRes.http_ok("删除角色成功").success();
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }


    @RequestMapping(value = "/getall",method= RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="获取所有角色",notes = "")
    public CommonRes getAll(){
        try {
            return CommonRes.http_ok("获取所有角色",appRoleService.getAllRoles()).success();
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }




}
