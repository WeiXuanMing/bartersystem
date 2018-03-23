package com.daming.bartersystem.service;

import com.daming.bartersystem.entitys.ShoppingTrolleyItem;

import java.util.List;

public interface ShoppingTrolleyItemService {
    public List<ShoppingTrolleyItem> queryByTrolleyId(Integer TrolleyId);
}
