package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.dao.BarterOrderItemMapper;
import com.daming.bartersystem.entitys.BarterOrder;
import com.daming.bartersystem.entitys.BarterOrderItem;
import com.daming.bartersystem.entitys.BarterOrderItemExample;
import com.daming.bartersystem.service.OrderItemService;
import com.daming.bartersystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService{
    @Autowired
    private BarterOrderItemMapper barterOrderItemMapper;
    public BarterOrderItem queryByOrderItemId(Integer orderItemId) {
        BarterOrderItemExample barterOrderItemExample = new BarterOrderItemExample();
        BarterOrderItemExample.Criteria criteria = barterOrderItemExample.createCriteria();
        criteria.andOrderitemIdEqualTo(orderItemId);
        List<BarterOrderItem> barterOrderItems = barterOrderItemMapper.selectByExample(barterOrderItemExample);
        if (barterOrderItems.size()>0){
            return barterOrderItems.get(0);
        }
        return null;
    }

    public boolean insert(BarterOrderItem barterOrderItem) {
        Integer count = barterOrderItemMapper.insert(barterOrderItem);
        if (count == 1){
            return true;
        }
        return false;
    }

    public boolean updateByBarterOrderItem(BarterOrderItem barterOrderItem) {
        BarterOrderItemExample barterOrderItemExample = new BarterOrderItemExample();
        BarterOrderItemExample.Criteria criteria = barterOrderItemExample.createCriteria();
        criteria.andOrderitemIdEqualTo(barterOrderItem.getOrderitemId());
        Integer count = barterOrderItemMapper.updateByExample(barterOrderItem,barterOrderItemExample);
        if (count == 1){
            return true;
        }
        return false;
    }

    public BarterOrderItem queryByUidUidItemId(Integer uid1, Integer uid2, Integer itemId) {
        BarterOrderItemExample barterOrderItemExample = new BarterOrderItemExample();
        BarterOrderItemExample.Criteria criteria = barterOrderItemExample.createCriteria();
        criteria.andUid1EqualTo(uid1);
        criteria.andUid2EqualTo(uid2);
        criteria.andItemIdEqualTo(itemId);
        List<BarterOrderItem> barterOrderItems = barterOrderItemMapper.selectByExample(barterOrderItemExample);
        if (barterOrderItems.size()>0){
            return barterOrderItems.get(0);
        }
        return null;
    }

    public BarterOrderItem queryByOrderIdUidUidItemId(Integer orderId, Integer uid1, Integer uid2, Integer itemId) {
        BarterOrderItemExample barterOrderItemExample = new BarterOrderItemExample();
        BarterOrderItemExample.Criteria criteria = barterOrderItemExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        criteria.andUid1EqualTo(uid1);
        criteria.andUid2EqualTo(uid2);
        criteria.andItemIdEqualTo(itemId);
        List<BarterOrderItem> barterOrderItems = barterOrderItemMapper.selectByExample(barterOrderItemExample);
        if (barterOrderItems.size()>0){
            return barterOrderItems.get(0);
        }
        return null;
    }
}
