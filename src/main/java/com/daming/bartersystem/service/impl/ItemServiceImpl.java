package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.dao.ItemMapper;
import com.daming.bartersystem.entitys.Item;
import com.daming.bartersystem.entitys.ItemExample;
import com.daming.bartersystem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemMapper itemMapper;

    public Item query(Integer itemId) {
        ItemExample itemExample = new ItemExample();
        ItemExample.Criteria criteria = itemExample.createCriteria();
        criteria.andUidEqualTo(itemId);
        List<Item> items = itemMapper.selectByExample(itemExample);
        if (items.size()==1){
            return items.get(0);
        }
        return null;

    }

    public List<Item> queryList(String condition) {
        ItemExample itemExample = new ItemExample();
        List<Item> itemList = itemMapper.selectByExample(null);
        ItemExample.Criteria criteria = itemExample.createCriteria();
        criteria.andTitleLike("%condition%");
        itemMapper.selectByExample(itemExample);
        return itemList;
    }

    public List<Item> queryByUid(Integer uid) {
        ItemExample itemExample = new ItemExample();
        List<Item> itemList = itemMapper.selectByExample(null);
        ItemExample.Criteria criteria = itemExample.createCriteria();
        criteria.andUidEqualTo(uid);
        itemMapper.selectByExample(itemExample);
        return itemList;
    }

    public int update(Item item) {
        ItemExample itemExample = new ItemExample();
        ItemExample.Criteria criteria = itemExample.createCriteria();
        criteria.andUidEqualTo(item.getUid());
        return itemMapper.updateByExample(item,itemExample);
    }
}
