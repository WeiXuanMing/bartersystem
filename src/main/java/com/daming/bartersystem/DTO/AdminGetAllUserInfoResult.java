package com.daming.bartersystem.DTO;

import com.daming.bartersystem.entitys.User;
import lombok.Data;

import java.util.List;

@Data
public class AdminGetAllUserInfoResult {
    private List<User> users;
}
