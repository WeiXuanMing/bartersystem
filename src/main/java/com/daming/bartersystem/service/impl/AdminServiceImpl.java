package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.dao.ItemMapper;
import com.daming.bartersystem.dao.UserMapper;
import com.daming.bartersystem.entitys.Item;
import com.daming.bartersystem.entitys.ItemExample;
import com.daming.bartersystem.entitys.User;
import com.daming.bartersystem.entitys.UserExample;
import com.daming.bartersystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ItemMapper itemMapper;

    public boolean banByUid(Integer uid) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUidEqualTo(uid);
        User user = userMapper.selectByExample(userExample).get(0);
        user.setIsdel(1);
        Integer succeed = userMapper.updateByExample(user,userExample);
        if (succeed == 1){
            return true;
        }
        return false;
    }

    public boolean unBanByUid(Integer uid) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUidEqualTo(uid);
        User user = userMapper.selectByExample(userExample).get(0);
        user.setIsdel(0);
        Integer succeed = userMapper.updateByExample(user,userExample);
        if (succeed == 1){
            return true;
        }
        return false;
    }

    public List<Item> queryAll() {
        ItemExample itemExample = new ItemExample();
        ItemExample.Criteria criteria = itemExample.createCriteria();
        criteria.andIsonEqualTo(0);
        List<Item> items = itemMapper.selectByExample(itemExample);
        return items;
    }
}
