package com.lingsi.unp.controller.auth;

import com.lingsi.unp.model.auth.AppMenu;
import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.service.auth.AppMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value="用户管理",tags = {"用户基础服务-菜单增删改查"},position= 1 )
@RestController
@RequestMapping(value = "/api/auth/menu")
public class AppMenu_Controller {
    @Autowired
    AppMenuService appMenuService;

    @RequestMapping(value = "/add",method= RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="新增菜单",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="url",value ="Pattern",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="path",value ="path",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="component",value ="component",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="name",value ="菜单名称",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="parentId",value ="菜单父节点",required =false,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name="requireAuth",value ="是否需要后台权限校验",required = true,dataType = "Boolean"),
            @ApiImplicitParam(paramType = "query",name="enabled",value ="是否启用",required = true,dataType = "Boolean")
    })
    public CommonRes addMenu(String url, String path, String component, String name, String parentId, Boolean requireAuth, Boolean enabled){
        AppMenu menu = new AppMenu();
        menu.setUrl(url);
        menu.setPath(path);
        menu.setComponent(component);
        menu.setName(name);
        if(parentId!=null)
            menu.setParentid(Integer.parseInt(parentId));
        menu.setRequireauth(requireAuth);
        menu.setEnabled(enabled);
        try{
            if(appMenuService.addMenu(menu)==1){
                return CommonRes.http_ok("添加菜单成功").success();
            }else{
                return CommonRes.http_ok("添加菜单失败").fail();
            }
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_ok("添加菜单失败").fail();
        }

    }
    @RequestMapping(value = "/delete",method= RequestMethod.DELETE,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="删除菜单",notes = "id为菜单的唯一性标识符")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="id",value ="菜单ID［唯一］",required = true,dataType = "String")
    })
    public CommonRes deleteMenu(String id){
        try{
            if(appMenuService.deleteMenu(Integer.valueOf(id))==1){
                return CommonRes.http_ok("删除菜单成功").success();
            }else{
                return CommonRes.http_ok("删除菜单失败").fail();
            }
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error("删除菜单失败");
        }
    }

    @RequestMapping(value = "/query",method= RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="查询菜单配置详情",notes = "id为菜单的唯一性标识符")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="id",value ="菜单ID［唯一］",required = true,dataType = "String")
    })
    public CommonRes getMenuInfo(String id){
        try {
            return CommonRes.http_ok("查询菜单配置详情", appMenuService.getMenuInfo(Integer.valueOf(id))).success();
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }

    @RequestMapping(value = "/getall",method= RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="获取所有菜单",notes = "")
    public CommonRes getAllMenu(){
        try {
            return CommonRes.http_ok("获取所有菜单成功", appMenuService.getAllMenus()).success();
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }

    @RequestMapping(value = "/role/allmenus",method= RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value="获取该角色下的所有菜单权限",notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="roleId",value ="角色编号ID［唯一］",required = true,dataType = "String")
    })
    public CommonRes getAaccessibleMenusByRoleId(String roleId){
        try {
            return CommonRes.http_ok("获取该角色下的所有菜单权限",appMenuService.getAaccessibleMenusByRoleId(roleId)).success();
        }catch (Exception e){
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }

}
