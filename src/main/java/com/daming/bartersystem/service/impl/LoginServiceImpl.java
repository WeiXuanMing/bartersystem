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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminInformationMapper adminInformationMapper;
    public boolean checkLogin(Loginer Loginer) {
        if (Loginer.getLogintype().equals("user")){
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andLoginAccountEqualTo(Loginer.getLoginAccount());
            criteria.andPasswordEqualTo(Loginer.getPassword());

            List<User> users = userMapper.selectByExample(userExample);
            if (users.size()>0){
                User user = users.get(0);
                if (user != null){return true;}
            }
        }
        if(Loginer.getLogintype().equals("admin")){
            AdminInformationExample adminInformationExample = new AdminInformationExample();
            AdminInformationExample.Criteria criteria = adminInformationExample.createCriteria();
            criteria.andLoginAccountEqualTo(Loginer.getLoginAccount());
            criteria.andPasswordEqualTo(Loginer.getPassword());
            List<AdminInformation> adminInformations = adminInformationMapper.selectByExample(adminInformationExample);
            if(adminInformations.size()>0) {
                return true;
            }
        }
        return false;
    }
}
