package com.daming.bartersystem.DTO;


import javax.validation.constraints.NotNull;

public class Loginer {
    @NotNull(message = "帐号不能为空")
    private String loginAccount;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "请选择登录身份")
    private String logintype;

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogintype() {
        return logintype;
    }

    public void setLogintype(String logintype) {
        this.logintype = logintype;
    }
}
