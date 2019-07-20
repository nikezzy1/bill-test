package com.lingsi.unp.model.auth;

public class AppRoleUser {
    private String id;

    private String roleId;

    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRoleid() {
        return roleId;
    }

    public void setRoleid(String roleid) {
        this.roleId = roleid == null ? null : roleid.trim();
    }

    public String getUserid() {
        return userId;
    }

    public void setUserid(String userid) {
        this.userId = userid == null ? null : userid.trim();
    }
}