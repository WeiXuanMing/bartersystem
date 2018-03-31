package com.daming.bartersystem.service;

import com.daming.bartersystem.entitys.AdminInformation;
import com.daming.bartersystem.entitys.Item;
import com.daming.bartersystem.entitys.User;

import java.util.List;

public interface AdminService {
    boolean banByUid(Integer uid);

    boolean unBanByUid(Integer uid);
    //获取所有未通过审核的物品
    List<Item> queryAll();

    boolean auditItem(Integer itemId);

    List<User> getAllUserInfo();

    AdminInformation login(String account, String password);
}
