package com.daming.bartersystem.service;


import com.daming.bartersystem.DTO.BarterOrderResult;
import com.daming.bartersystem.entitys.BarterOrder;

import java.util.List;

public interface OrderService {
     Integer CreateOrder(Integer uid1,Integer uid2);

     BarterOrderResult queryByOrderId(Integer BarterOrderId);

     List<BarterOrderResult> queryByUid(Integer uid);

}
