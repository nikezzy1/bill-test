package com.lingsi.unp.mapper.auth;

import com.lingsi.unp.model.auth.AppMenu;
import com.lingsi.unp.model.auth.AppRole;

import java.util.List;

public interface AppMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppMenu record);

    int insertSelective(AppMenu record);

    AppMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppMenu record);

    int updateByPrimaryKey(AppMenu record);

    List<AppMenu> getAllMenus();

    List<AppMenu> getAaccessibleMenusByRoleId(String roleid);

    List<AppRole> getRolesByMenuId(Integer id);

}