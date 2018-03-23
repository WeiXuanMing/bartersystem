package com.daming.bartersystem.service;

import com.daming.bartersystem.entitys.Item;

import java.util.List;

public interface ItemService {
    Item query(Integer itemId);

    List<Item> queryList(String condition);

    List<Item> queryByUid(Integer uid);

    int update(Item item);

}
