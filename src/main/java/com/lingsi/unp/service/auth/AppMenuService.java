package com.lingsi.unp.service.auth;

import com.lingsi.unp.mapper.auth.AppMenuMapper;
import com.lingsi.unp.model.auth.AppMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(transactionManager = "hostTransactionManager")
public class AppMenuService {
    @Autowired
    private AppMenuMapper menuMapper;//这里会报错，但是并不会影响

    public int addMenu(AppMenu menu) {
        return menuMapper.insert(menu);
    }
    public int deleteMenu(int id){
        return menuMapper.deleteByPrimaryKey(id);
    }
    public AppMenu getMenuInfo(int id){
        return menuMapper.selectByPrimaryKey(id);
    }
    public List<AppMenu> getAllMenus(){
        return menuMapper.getAllMenus();
    }

    public List<AppMenu> getAaccessibleMenusByRoleId(String roleid){
        return menuMapper.getAaccessibleMenusByRoleId(roleid);
    }

}
