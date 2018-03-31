package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.dao.UserMapper;
import com.daming.bartersystem.entitys.User;
import com.daming.bartersystem.entitys.UserExample;
import com.daming.bartersystem.service.PIMService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PIMServiceImpl implements PIMService{
    @Autowired
    private UserMapper userMapper;

    public User getUserInfo(Integer uid) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUidEqualTo(uid);
        List<User> users = userMapper.selectByExample(userExample);
        if (users!=null){
            return users.get(0);
        }
        return null;
    }
    public boolean updateUser(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUidEqualTo(user.getUid());
        int i = userMapper.updateByExample(user, userExample);
        if (i == 1){
            return true;
        }
        return false;
    }
}
