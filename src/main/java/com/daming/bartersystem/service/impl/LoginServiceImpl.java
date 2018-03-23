package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.DTO.Loginer;
import com.daming.bartersystem.dao.AdminInformationMapper;
import com.daming.bartersystem.dao.UserMapper;
import com.daming.bartersystem.entitys.AdminInformation;
import com.daming.bartersystem.entitys.AdminInformationExample;
import com.daming.bartersystem.entitys.User;
import com.daming.bartersystem.entitys.UserExample;
import com.daming.bartersystem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoginServiceImpl implements LoginService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminInformationMapper adminInformationMapper;
    public boolean checkLogin(Loginer Loginer) {
        if (Loginer.getLogintype().equals("用户")){
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andLoginAccountEqualTo(Loginer.getLoginAccount());
            criteria.andPasswordEqualTo(Loginer.getPassword());
            User user = userMapper.selectByExample(userExample).get(0);
            if(user != null) {return true;}
        }
        if(Loginer.getLogintype().equals("管理员")){
            AdminInformationExample adminInformationExample = new AdminInformationExample();
            AdminInformationExample.Criteria criteria = adminInformationExample.createCriteria();
            criteria.andLoginAccountEqualTo(Loginer.getLoginAccount());
            criteria.andPasswordEqualTo(Loginer.getPassword());
            AdminInformation adminInformation = adminInformationMapper.selectByExample(adminInformationExample).get(0);
            if(adminInformation != null) {return true;}
        }
        return false;
    }
}
