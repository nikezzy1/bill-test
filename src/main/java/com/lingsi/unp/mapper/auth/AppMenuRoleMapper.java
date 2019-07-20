package com.lingsi.unp.mapper.auth;

import com.lingsi.unp.model.auth.AppMenuRole;

public interface AppMenuRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppMenuRole record);

    int insertSelective(AppMenuRole record);

    AppMenuRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppMenuRole record);

    int updateByPrimaryKey(AppMenuRole record);
}