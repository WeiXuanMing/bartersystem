package com.daming.bartersystem.DTO;

import com.daming.bartersystem.entitys.User;

public class UserSimpleInfoResult {
  private String username;

  private String phone;

  private String email;

  private String consignee;

  private String consignee_phone;

  private String consignee_address;

  private Integer consignee_postalcode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsignee_phone() {
        return consignee_phone;
    }

    public void setConsignee_phone(String consignee_phone) {
        this.consignee_phone = consignee_phone;
    }

    public String getConsignee_address() {
        return consignee_address;
    }

    public void setConsignee_address(String consignee_address) {
        this.consignee_address = consignee_address;
    }

    public Integer getConsignee_postalcode() {
        return consignee_postalcode;
    }

    public void setConsignee_postalcode(Integer consignee_postalcode) {
        this.consignee_postalcode = consignee_postalcode;
    }

    @Override
    public String toString() {
        return "UserSimpleInfoResult{" +
                "username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", consignee='" + consignee + '\'' +
                ", consignee_phone='" + consignee_phone + '\'' +
                ", consignee_address='" + consignee_address + '\'' +
                ", consignee_postalcode='" + consignee_postalcode + '\'' +
                '}';
    }
}
