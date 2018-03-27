package com.daming.bartersystem.service;


import com.daming.bartersystem.entitys.BarterOrder_OrderItem;

import java.util.List;

public interface BarterOrder_OrderItemService {

    List<BarterOrder_OrderItem> queryByOrderId(Integer OrderId);

    boolean addBarterOrder_OrderItem(BarterOrder_OrderItem barterOrder_orderItem);

}
