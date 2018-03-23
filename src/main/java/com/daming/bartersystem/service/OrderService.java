package com.daming.bartersystem.service;


import com.daming.bartersystem.DTO.BarterOrderResult;
import com.daming.bartersystem.entitys.BarterOrder;

import java.util.List;

public interface OrderService {
    public Integer CreateOrder(Integer uid1,Integer uid2);

    public BarterOrderResult queryByOrderId(Integer BarterOrderId);

    public List<BarterOrderResult> queryByUid(Integer uid);

}
