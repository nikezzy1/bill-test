package com.lingsi.unp.service.auth;

import com.lingsi.unp.mapper.auth.AppRoleMapper;
import com.lingsi.unp.model.auth.AppRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(transactionManager = "hostTransactionManager")
public class AppRoleService {
    @Autowired
    private AppRoleMapper roleMapper;//这里会报错，但是并不会影响

    public int addRole(AppRole role) {
        return roleMapper.insert(role);
    }
    public int deleteRole(String id){
        return roleMapper.deleteByPrimaryKey(id);
    }
    public AppRole getRoleInfo(String id){
        return roleMapper.selectByPrimaryKey(id);
    }
    public List<AppRole> getAllRoles(){
        return roleMapper.getAllRoles();
    }

}
