package com.daming.bartersystem.service;

import com.daming.bartersystem.entitys.Item;

import java.util.List;

public interface AdminService {
    boolean banByUid(Integer uid);

    boolean unBanByUid(Integer uid);
    //获取所有未通过审核的物品
    List<Item> queryAll();
}
