package com.daming.bartersystem.service;

import com.daming.bartersystem.entitys.Item;

import java.util.List;

public interface ItemService {
    public Item query(Integer itemId);

    public List<Item> queryList(String condition);

    public List<Item> queryByUid(Integer uid);

    public int update(Item item);

}
