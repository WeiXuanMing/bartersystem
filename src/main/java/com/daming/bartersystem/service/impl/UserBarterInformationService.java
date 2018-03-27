package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.dao.UserBarterUnformationMapper;
import com.daming.bartersystem.entitys.UserBarterUnformation;
import com.daming.bartersystem.entitys.UserBarterUnformationExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBarterInformationService implements com.daming.bartersystem.service.UserBarterInformationService{
    @Autowired
    private UserBarterUnformationMapper userBarterUnformationMapper;

    public UserBarterUnformation getUBInfoByUid(Integer uid) {
        UserBarterUnformationExample userBarterUnformationExample = new UserBarterUnformationExample();
        UserBarterUnformationExample.Criteria criteria = userBarterUnformationExample.createCriteria();
        criteria.andUidEqualTo(uid);
        List<UserBarterUnformation> unformations = userBarterUnformationMapper.selectByExample(userBarterUnformationExample);

        if (unformations.size() > 0){
            System.out.println(unformations.get(0));
            return unformations.get(0);
        }
        return null;
    }
}
