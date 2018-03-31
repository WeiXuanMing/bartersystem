package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.dao.ShoppingTrolleyMapper;
import com.daming.bartersystem.entitys.ShoppingTrolley;
import com.daming.bartersystem.entitys.ShoppingTrolleyExample;
import com.daming.bartersystem.service.ShoppingTrolleyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingTrolleyServiceImpl implements ShoppingTrolleyService{

    @Autowired
    private ShoppingTrolleyMapper shoppingTrolleyMapper;

    public ShoppingTrolley queryByUid(Integer uid) {
        ShoppingTrolleyExample shoppingTrolleyExample = new ShoppingTrolleyExample();
        ShoppingTrolleyExample.Criteria criteria = shoppingTrolleyExample.createCriteria();
        criteria.andUidEqualTo(uid);
        List<ShoppingTrolley> shoppingTrolleys = shoppingTrolleyMapper.selectByExample(shoppingTrolleyExample);
        if(shoppingTrolleys.size() == 1){
            return shoppingTrolleys.get(0);
        }
        return null;
    }

    public boolean createShoppingTrolley(Integer uid) {
        ShoppingTrolley shoppingTrolley = new ShoppingTrolley();
        shoppingTrolley.setUid(uid);
        Integer i = shoppingTrolleyMapper.insert(shoppingTrolley);
        if (i == 1){
            return true;
        }
        return false;
    }

}
