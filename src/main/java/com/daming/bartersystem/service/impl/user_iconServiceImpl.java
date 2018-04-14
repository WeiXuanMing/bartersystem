package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.dao.user_iconMapper;
import com.daming.bartersystem.entitys.user_icon;
import com.daming.bartersystem.entitys.user_iconExample;
import com.daming.bartersystem.service.user_iconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class user_iconServiceImpl implements user_iconService {

    @Autowired
    private user_iconMapper user_iconMapper;

    public boolean addIcon(user_icon user_icon) {
        int count = user_iconMapper.insert(user_icon);
        if(count == 1){
            return true;
        }
        return false;
    }

    public boolean updateIcon(user_icon user_icon) {
        user_iconExample userIconExample = new user_iconExample();
        user_iconExample.Criteria criteria = userIconExample.createCriteria();
        criteria.andUidEqualTo(user_icon.getUid());
        int count = user_iconMapper.updateByExample(user_icon,userIconExample);
        if(count == 1){
            return true;
        }
        return false;
    }
}
