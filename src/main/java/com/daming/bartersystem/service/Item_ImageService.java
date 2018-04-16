package com.daming.bartersystem.service;


import com.daming.bartersystem.entitys.Item_Image;

public interface Item_ImageService {
    public Item_Image queryByItemId(Integer itemId);

    public boolean insert(Item_Image item_image);

}
