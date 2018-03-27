package com.daming.bartersystem.service.impl;



import com.daming.bartersystem.dao.BarterOrder_OrderItemMapper;
import com.daming.bartersystem.entitys.BarterOrder_OrderItem;
import com.daming.bartersystem.entitys.BarterOrder_OrderItemExample;
import com.daming.bartersystem.service.BarterOrder_OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BarterOrder_OrderItemServiceImpl implements BarterOrder_OrderItemService{

    @Autowired
    private BarterOrder_OrderItemMapper barterOrder_orderItemMapper;

    public List<BarterOrder_OrderItem> queryByOrderId(Integer OrderId) {
        BarterOrder_OrderItemExample barterOrder_orderItemExample = new BarterOrder_OrderItemExample();
        BarterOrder_OrderItemExample.Criteria criteria = barterOrder_orderItemExample.createCriteria();
        criteria.andBarterOrderIdEqualTo(OrderId);
        List<BarterOrder_OrderItem> barterOrder_orderItems = barterOrder_orderItemMapper.selectByExample(barterOrder_orderItemExample);
        return barterOrder_orderItems;
    }

    public boolean addBarterOrder_OrderItem(BarterOrder_OrderItem barterOrder_orderItem) {
        Integer count = barterOrder_orderItemMapper.insert(barterOrder_orderItem);
        if (count == 1){
            return true;
        }
        return false;
    }

}
