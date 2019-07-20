package com.lingsi.unp.model.notarial;

public class NotarialPaperType {
    private Integer id;

    private String personInvolved;

    private String businessType;

    private String paperNo;

    private Integer pageCount;

    private String paperType;

    private String paperName;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonInvolved() {
        return personInvolved;
    }

    public void setPersonInvolved(String personInvolved) {
        this.personInvolved = personInvolved == null ? null : personInvolved.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getPaperNo() {
        return paperNo;
    }

    public void setPaperNo(String paperNo) {
        this.paperNo = paperNo == null ? null : paperNo.trim();
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType == null ? null : paperType.trim();
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName == null ? null : paperName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}