package com.lingsi.unp.model.auth;

import java.util.List;

public class AppRole {
    private String rolename;

    private String status;

    private String roleid;

    private List<AppMenu>  menusList;//得到当前角色可以访问的菜单

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public List<AppMenu> getMenusList() {
        return menusList;
    }

    public void setMenusList(List<AppMenu> menusList) {
        this.menusList = menusList;
    }
}