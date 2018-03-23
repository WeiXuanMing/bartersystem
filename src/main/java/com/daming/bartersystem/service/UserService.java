package com.daming.bartersystem.service;

import com.daming.bartersystem.entitys.User;

public interface UserService {

    public User queryByUid(Integer uid);

    public Integer addUser(User user);

    public Integer updateUser(User user);
}
