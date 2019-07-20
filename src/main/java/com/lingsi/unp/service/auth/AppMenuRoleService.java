package com.lingsi.unp.service.auth;


import com.lingsi.unp.mapper.auth.AppMenuRoleMapper;
import com.lingsi.unp.model.auth.AppMenuRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(transactionManager = "hostTransactionManager")
public class AppMenuRoleService {
    @Autowired
    private AppMenuRoleMapper appMenuRoleMapper;//这里会报错，但是并不会影响

    public int addGrantedMenu(AppMenuRole record) {
        return appMenuRoleMapper.insert(record);
    }


}
