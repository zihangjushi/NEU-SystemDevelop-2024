package com.sunbw.demo.entity;

public class Company2 {

    private Integer companyId;
    private String contactName;
    private String companyName;
    private String phoneNumber;
    private String adminName;
    private Integer state;
    private String createTime;
    private String description;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company2() {

    }

    public Company2(String contactName, String companyName, Integer companyId, String adminName, String phoneNumber, String createTime, Integer state, String description) {
        this.contactName = contactName;
        this.companyName = companyName;
        this.companyId = companyId;
        this.adminName = adminName;
        this.phoneNumber = phoneNumber;
        this.createTime = createTime;
        this.state = state;
        this.description = description;
    }
}
