package com.daming.bartersystem.service;

import com.daming.bartersystem.entitys.BarterOrderItem;

import java.util.List;

public interface OrderItemService {
    BarterOrderItem queryByOrderItemId(Integer orderItemId);

    boolean insert(BarterOrderItem barterOrderItem);

    boolean updateByBarterOrderItem(BarterOrderItem barterOrderItem);

    BarterOrderItem queryByUidUidItemId(Integer uid1,Integer uid2,Integer itemId);

    BarterOrderItem queryByOrderIdUidUidItemId(Integer orderId,Integer uid1,Integer uid2,Integer itemId);
}
