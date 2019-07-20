package com.lingsi.unp.mapper.auth;

import com.lingsi.unp.model.auth.AppRole;
import com.lingsi.unp.model.auth.AppUser;

import java.util.List;

public interface AppUserMapper {
    int deleteByPrimaryKey(String userid);

    int insert(AppUser record);

    int insertSelective(AppUser record);

    AppUser selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(AppUser record);

    int updateByPrimaryKey(AppUser record);

    List<AppRole> selectAppRoleListById(String userid);

    List<AppUser> getAll();
}