package com.sunbw.demo.request;

public class LoginRequest {

    private String usernum;
    private String password;






    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsernum() {
        return usernum;
    }

    public void setUsernum(String username) {
        this.usernum = username;
    }

    public LoginRequest(String username, String password) {
        this.usernum = username;
        this.password = password;

    }

    public LoginRequest() {}
}
