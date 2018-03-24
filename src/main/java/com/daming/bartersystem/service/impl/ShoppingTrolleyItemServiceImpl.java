package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.dao.ShoppingTrolleyItemMapper;
import com.daming.bartersystem.entitys.ShoppingTrolleyItem;
import com.daming.bartersystem.entitys.ShoppingTrolleyItemExample;
import com.daming.bartersystem.service.ShoppingTrolleyItemService;
import com.daming.bartersystem.service.ShoppingTrolleyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingTrolleyItemServiceImpl implements ShoppingTrolleyItemService{
    @Autowired
    private ShoppingTrolleyItemMapper shoppingTrolleyItemMapper;

    public List<ShoppingTrolleyItem> queryByTrolleyId(Integer TrolleyId) {
        ShoppingTrolleyItemExample shoppingTrolleyExample = new ShoppingTrolleyItemExample();
        ShoppingTrolleyItemExample.Criteria criteria = shoppingTrolleyExample.createCriteria();
        criteria.andTrolleyIdEqualTo(TrolleyId);
        List<ShoppingTrolleyItem> shoppingTrolleyItems = shoppingTrolleyItemMapper.selectByExample(shoppingTrolleyExample);
        if(shoppingTrolleyItems.size() == 1){
            return shoppingTrolleyItems;
        }
        return null;
    }
}
