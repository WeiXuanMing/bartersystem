package com.daming.bartersystem.service;

import com.daming.bartersystem.entitys.ShoppingTrolleyItem;

import java.util.List;

public interface ShoppingTrolleyItemService {
     List<ShoppingTrolleyItem> queryByTrolleyId(Integer TrolleyId);

     Integer createShoppingTrolleyItem(Integer trolleyId,Integer itemId);
}
