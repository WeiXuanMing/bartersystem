package com.daming.bartersystem.DTO;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Register {
    @NotNull(message = "帐号不能为空")
    private String loginAccount;
    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "手机号码不能为空")
    private String phone;
    @NotNull(message = "邮箱地址不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;


    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Register{" +
                "loginAccount='" + loginAccount + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
