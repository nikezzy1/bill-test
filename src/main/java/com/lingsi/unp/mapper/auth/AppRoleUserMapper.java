package com.lingsi.unp.mapper.auth;

import com.lingsi.unp.model.auth.AppRoleUser;
import com.lingsi.unp.model.auth.AppUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppRoleUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(@Param("userId") String userId, @Param("roleId") String roleId);
    int delete(@Param("userId") String userId, @Param("roleId") String roleId);

    int insertSelective(AppRoleUser record);

    AppRoleUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AppRoleUser record);

    int updateByPrimaryKey(AppRoleUser record);





    List<AppUser> selectRoleUser(@Param("roleId") String roleId);
}