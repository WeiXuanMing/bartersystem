package com.daming.bartersystem.DTO;

public class LoginResult {
    private String loginType;

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public LoginResult(String loginType) {
        this.loginType = loginType;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "loginType='" + loginType + '\'' +
                '}';
    }
}
