package com.NEUSystemDevelop2024.entity;

import java.sql.Timestamp;

public class Company {
    int companyId;
    String companyName;
    String contactName;
    String phoneNumber;
    String adminName;
    int state;
    String description;
    Timestamp createTime;

    public Company() {
    }

    public Company(String adminName, int state, String phoneNumber, String contactName, String companyName, String description, Timestamp createTime) {
        this.adminName = adminName;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.contactName = contactName;
        this.companyName = companyName;
        this.description = description;
        this.createTime = createTime;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
