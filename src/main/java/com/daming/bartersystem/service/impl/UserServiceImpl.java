package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.dao.UserMapper;
import com.daming.bartersystem.entitys.User;
import com.daming.bartersystem.entitys.UserExample;
import com.daming.bartersystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired(required = false)
    private UserMapper userMapper;
    //根据用户id查询用户所有信息
    public User queryByUid(Integer uid) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUidEqualTo(uid);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size()==1){
            return users.get(0);
        }
        return null;
    }
    //添加用户
    public boolean addUser(User user) {
        Integer i = userMapper.insert(user);
        if(i == 0){ return false;}
        if (i == 1){return true;}
        return false;
    }
    //更新用户信息
    public Integer updateUser(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUidEqualTo(user.getUid());
        return userMapper.updateByExample(user,userExample);
    }

    public User queryByLoginAccount(String LoginAccount) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginAccountEqualTo(LoginAccount);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size()==1){
            return users.get(0);
        }
        return null;
    }

    public User queryByUsername(String username) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size()==1){
            return users.get(0);
        }
        return null;
    }
}
