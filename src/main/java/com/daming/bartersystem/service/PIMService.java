package com.daming.bartersystem.service;

import com.daming.bartersystem.entitys.User;

public interface PIMService {
        //获取个人信息
        User getUserInfo(Integer uid);
        //修改个人信息
        boolean updateUser(User user);
}
