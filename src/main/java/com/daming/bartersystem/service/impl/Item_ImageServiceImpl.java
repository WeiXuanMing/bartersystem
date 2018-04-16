package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.dao.Item_ImageMapper;
import com.daming.bartersystem.entitys.Item_Image;
import com.daming.bartersystem.entitys.Item_ImageExample;
import com.daming.bartersystem.service.Item_ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Item_ImageServiceImpl implements Item_ImageService{

    @Autowired
    private Item_ImageMapper itemImageMapper;
    @Override
    public Item_Image queryByItemId(Integer itemId) {
        Item_ImageExample itemImageExample = new Item_ImageExample();
        Item_ImageExample.Criteria criteria = itemImageExample.createCriteria();
        criteria.andItemidEqualTo(itemId);
        List<Item_Image> itemImages = itemImageMapper.selectByExample(itemImageExample);
        if(itemImages!=null && itemImages.size()>0){
            return itemImages.get(0);
        }
        return null;
    }

    @Override
    public boolean insert(Item_Image item_image) {
        Integer count = itemImageMapper.insert(item_image);
        if(count == 1){
            return true;
        }
        return false;
    }
}
