package com.lingsi.unp.mapper.auth;

import com.lingsi.unp.model.auth.AppRole;

import java.util.List;

public interface AppRoleMapper {
    int deleteByPrimaryKey(String roleid);

    int insert(AppRole record);

    int insertSelective(AppRole record);

    AppRole selectByPrimaryKey(String roleid);

    int updateByPrimaryKeySelective(AppRole record);

    int updateByPrimaryKey(AppRole record);

    List<AppRole> getAllRoles();

}