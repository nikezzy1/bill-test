package com.lingsi.unp.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AppUserDetails {
    private String userId;

    private String userName;

    private String idCardNo;

    private String companyName;

    private String companyLicenseNo;

    private String inputUserId;

    private String inputTime;

    private String telephone;

    private String grantedCode;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @JsonIgnore
    public String getGrantedCode() {
        return grantedCode;
    }

    public void setGrantedCode(String grantedCode) {
        this.grantedCode = grantedCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo == null ? null : idCardNo.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyLicenseNo() {
        return companyLicenseNo;
    }

    public void setCompanyLicenseNo(String companyLicenseNo) {
        this.companyLicenseNo = companyLicenseNo == null ? null : companyLicenseNo.trim();
    }

    public String getInputUserId() {
        return inputUserId;
    }

    public void setInputUserId(String inputUserId) {
        this.inputUserId = inputUserId == null ? null : inputUserId.trim();
    }

    public String getInputTime() {
        return inputTime;
    }

    public void setInputTime(String inputTime) {
        this.inputTime = inputTime == null ? null : inputTime.trim();
    }
}