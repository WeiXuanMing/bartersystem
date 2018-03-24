package com.daming.bartersystem.DTO;




public class Loginer {

    private String loginAccount;
    private String password;
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

    @Override
    public String toString() {
        return "Loginer{" +
                "loginAccount='" + loginAccount + '\'' +
                ", password='" + password + '\'' +
                ", logintype='" + logintype + '\'' +
                '}';
    }
}
