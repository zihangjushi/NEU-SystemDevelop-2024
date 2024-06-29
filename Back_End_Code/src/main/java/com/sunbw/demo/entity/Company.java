package com.sunbw.demo.entity;

public class Company {

    private Integer companyid;
    private String companyname;
    private String contactname;
    private String contactphone;

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }

    public Company() {

    }

    public Company(Integer companyid, String companyname, String contactname, String contactphone) {
        this.companyid = companyid;
        this.companyname = companyname;
        this.contactname = contactname;
        this.contactphone = contactphone;
    }
}
