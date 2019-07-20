package com.lingsi.unp.controller.auth;


import com.lingsi.unp.model.auth.AppMenuRole;
import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.service.auth.AppMenuRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value="权限管理",tags = {"用户基础服务-分配菜单访问权限"},position= 2)
@RestController
@RequestMapping(value = "/api/auth/grant")
public class AppMenuRole_Controller {

    @Autowired
    AppMenuRoleService appMenuRoleService;
    @ResponseBody
    @RequestMapping(value = "/grantmenus",method= RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="当前角色增加菜单访问权限",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="mid",value ="菜单id",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="rid",value ="角色编号",required = true,dataType = "String")

    })
    public CommonRes addMenuRole(String mid, String rid){
        try{

            AppMenuRole record = new AppMenuRole();
            record.setMid(Integer.valueOf(mid));
            record.setRid(rid);
            if(appMenuRoleService.addGrantedMenu(record)==1)
                return CommonRes.http_ok("添加成功").success();
            else
                return CommonRes.http_ok("添加失败").fail();
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error("添加失败异常");
        }

  }
}
