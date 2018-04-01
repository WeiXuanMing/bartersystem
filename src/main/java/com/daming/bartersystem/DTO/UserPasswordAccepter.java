package com.daming.bartersystem.DTO;

import lombok.Data;

@Data
public class UserPasswordAccepter {
    private String oldPassword;

    private String newPassword;

}
