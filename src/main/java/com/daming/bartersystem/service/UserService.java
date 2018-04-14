package com.daming.bartersystem.service;

import com.daming.bartersystem.entitys.User;

public interface UserService {

     User queryByUid(Integer uid);

     boolean addUser(User user);

     Integer updateUser(User user);

     User queryByLoginAccount(String LoginAccount);

     User queryByUsername(String username);

     boolean CheckUUID(Integer uid,String uuid);

}
