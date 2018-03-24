package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.dao.UserMapper;
import com.daming.bartersystem.entitys.User;
import com.daming.bartersystem.entitys.UserExample;
import com.daming.bartersystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired(required = false)
    private UserMapper userMapper;

    private final String salt = "daming";
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

    public boolean CheckUUID(Integer uid, String uuid) {
        String rea_uuid = getUUIDByUid(uid);
        if (rea_uuid !=null){
            if (rea_uuid.equals(uuid)){
                return true;
            }
        }
        return false;
    }
    private String getUUIDByUid(Integer uid){
        User user = queryByUid(uid);
        if(user != null) {
            String password = user.getPassword();
            String base = uid + "/" + salt + "/" + password;
            String uuid = DigestUtils.md5DigestAsHex(base.getBytes());
            return uuid;
        }
        return null;
    }
}
