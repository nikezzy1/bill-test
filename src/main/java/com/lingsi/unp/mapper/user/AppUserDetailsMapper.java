package com.lingsi.unp.mapper.user;

import com.lingsi.unp.model.user.AppUserDetails;

import java.util.List;
import java.util.Map;

public interface AppUserDetailsMapper {
    int deleteByPrimaryKey(String userId);

    int insert(AppUserDetails record);

    int insertSelective(AppUserDetails record);

    AppUserDetails selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(AppUserDetails record);

    int updateByPrimaryKey(AppUserDetails record);

    List<AppUserDetails> selectBySelective(Map map);

    AppUserDetails selectByPhone(String telephone);

}