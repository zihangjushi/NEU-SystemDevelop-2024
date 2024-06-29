package com.sunbw.demo.entity;

public class User {

    private Integer userid;
    private Integer companyid;
    private String username;
    private String usernum;
    private String password;
    private String role;
    private String createdate;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsernum() {
        return usernum;
    }

    public void setUsernum(String usernum) {
        this.usernum = usernum;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(){

    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }


    public User(Integer userid, Integer companyid, String username, String usernum, String password, String createdate, String role) {
        this.userid = userid;
        this.companyid = companyid;
        this.username = username;
        this.usernum = usernum;
        this.password = password;
        this.createdate = createdate;
        this.role = role;
    }
}
