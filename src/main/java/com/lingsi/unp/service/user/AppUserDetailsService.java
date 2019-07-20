package com.lingsi.unp.service.user;

import com.lingsi.unp.mapper.user.AppUserDetailsMapper;
import com.lingsi.unp.model.auth.AppUser;
import com.lingsi.unp.model.user.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-03-27 17:06
 **/
@Service
@Transactional(transactionManager = "hostTransactionManager")
public class AppUserDetailsService {

    @Autowired
    AppUserDetailsMapper appUserDetailsMapper;
    public int deleteByPrimaryKey(String userId) {
        return appUserDetailsMapper.deleteByPrimaryKey(userId);
    }

    public int insert(AppUserDetails record){
        return appUserDetailsMapper.insert(record);
    }

    public int insertSelective(AppUserDetails record) {
        return appUserDetailsMapper.insertSelective(record);
    }

    public AppUserDetails selectByPrimaryKey(String userId) {
        return appUserDetailsMapper.selectByPrimaryKey(userId);
    }

    public int updateByPrimaryKeySelective(AppUserDetails record) {
        return appUserDetailsMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(AppUserDetails record) {
        return appUserDetailsMapper.updateByPrimaryKey(record);
    }

    public List<AppUserDetails> selectBySelective(Map map){
        return appUserDetailsMapper.selectBySelective(map);
    }
}
