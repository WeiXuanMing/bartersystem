package com.daming.bartersystem.service;

import com.daming.bartersystem.entitys.BarterOrderItem;

import java.util.List;

public interface OrderItemService {
    BarterOrderItem queryByOrderItemId(Integer orderItemId);

    boolean insert(BarterOrderItem barterOrderItem);

    boolean updateByBarterOrderItem(BarterOrderItem barterOrderItem);


}
