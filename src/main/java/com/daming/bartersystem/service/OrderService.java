package com.daming.bartersystem.service;


import com.daming.bartersystem.DTO.BarterOrderResult;
import com.daming.bartersystem.entitys.BarterOrder;
import com.daming.bartersystem.entitys.BarterOrder_OrderItem;

import java.util.List;

public interface OrderService {
     /*
          Integer CreateOrder(Integer uid1,Integer uid2);

          BarterOrderResult queryByOrderId(Integer BarterOrderId);

     */
     boolean CreateOrder(Integer uid,Integer itemId);

     BarterOrder queryByUidAndItemId(Integer uid,Integer itemId);

     List<BarterOrder> queryByUid(Integer uid);

     List<Integer> queryUidByOrderId(Integer OrderId);

     BarterOrder queryByOrderId(Integer orderId);

     List<BarterOrder> queryByItemId(Integer itemId);
}
