package com.lingsi.unp.service.auth;

import com.lingsi.unp.mapper.auth.AppRoleUserMapper;
import com.lingsi.unp.model.auth.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(transactionManager = "hostTransactionManager")
public class AppRoleUserService {
    @Autowired
    private AppRoleUserMapper roleUserMapper;//这里会报错，但是并不会影响

    public int addRoleUser(String  userId,String roleId) {
        return roleUserMapper.insert(userId,roleId);
    }

    public int deleteRoleUser(String  userId,String roleId) {
        return roleUserMapper.delete(userId,roleId);
    }

    /**
     * 查询助理公证员
     * @return
     */
    public List<AppUser> selectAssistNotary(){
        return roleUserMapper.selectRoleUser("assistNotary");
    }

    /**
     * 查询角色下用户
     */
    public List<AppUser> selectRoleUser(String roleId)
    {
        return roleUserMapper.selectRoleUser(roleId);
    }
}
