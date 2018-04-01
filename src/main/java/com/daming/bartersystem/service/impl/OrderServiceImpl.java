package com.daming.bartersystem.service.impl;



import com.daming.bartersystem.dao.BarterOrderMapper;

import com.daming.bartersystem.entitys.*;
import com.daming.bartersystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private BarterOrderMapper barterOrderMapper;


    public boolean CreateOrder(Integer uid, Integer itemId) {
        BarterOrder barterOrder = new BarterOrder();
        barterOrder.setUid(uid);
        barterOrder.setItemId(itemId);
        barterOrder.setOrderState(0);
        Integer count = barterOrderMapper.insert(barterOrder);
        if (count == 1){
            return true;
        }
        return false;
    }

    public BarterOrder queryByUidAndItemId(Integer uid, Integer itemId) {
        BarterOrderExample barterOrderExample = new BarterOrderExample();
        BarterOrderExample.Criteria criteria = barterOrderExample.createCriteria();
        criteria.andUidEqualTo(uid);
        criteria.andItemIdEqualTo(itemId);
        List<BarterOrder> barterOrders = barterOrderMapper.selectByExample(barterOrderExample);
        if (barterOrders.size()>0){
            return barterOrders.get(0);
        }
        return null;
    }

    public List<BarterOrder> queryByUid(Integer uid) {
        List<BarterOrder> barterOrders;
        BarterOrderExample barterOrderExample = new BarterOrderExample();
        BarterOrderExample.Criteria criteria = barterOrderExample.createCriteria();
        criteria.andUidEqualTo(uid);
        barterOrders = barterOrderMapper.selectByExample(barterOrderExample);
        return barterOrders;
    }

    public List<Integer> queryUidByOrderId(Integer OrderId) {


        return null;
    }

    public BarterOrder queryByOrderId(Integer orderId) {
        BarterOrderExample barterOrderExample = new BarterOrderExample();
        BarterOrderExample.Criteria criteria = barterOrderExample.createCriteria();
        criteria.andBarterOrderIdEqualTo(orderId);
        List<BarterOrder> barterOrders = barterOrderMapper.selectByExample(barterOrderExample);
        if(barterOrders!=null&&barterOrders.size()>0){
            return barterOrders.get(0);
        }
        return null;
    }

    public List<BarterOrder> queryByItemId(Integer itemId) {
        BarterOrderExample barterOrderExample = new BarterOrderExample();
        BarterOrderExample.Criteria criteria = barterOrderExample.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<BarterOrder> barterOrders = barterOrderMapper.selectByExample(barterOrderExample);
        if(barterOrders!=null){
            return barterOrders;
        }
        return null;
    }
    /*
    //创建一个Order列和两个OrderItem列
    public Integer CreateOrder(Integer uid1, Integer uid2) {
        BarterOrder barterOrder = new BarterOrder();
        barterOrder.setOrderState(0);
        barterOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
        barterOrderMapper.insert(barterOrder);
        System.out.println(barterOrder);
        BarterOrderItem orderItem1 = new BarterOrderItem();
        BarterOrderItem orderItem2 = new BarterOrderItem();
        orderItem1.setOrderId(barterOrder.getBarterOrderId());
        orderItem1.setUid1(uid1);
        orderItem1.setUid2(uid2);
        orderItem1.setOrderItemState(0);
        orderItem2.setOrderitemId(barterOrder.getBarterOrderId());
        orderItem2.setOrderId(barterOrder.getBarterOrderId());
        orderItem2.setUid1(uid2);
        orderItem2.setUid2(uid1);
        orderItem2.setOrderItemState(0);
        barterOrderItemMapper.insert(orderItem1);
        barterOrderItemMapper.insert(orderItem2);
        System.out.println(orderItem1);
        System.out.println(orderItem2);
        return null;
    }
    //根据OrderId获取订单信息。
    public BarterOrderResult queryByOrderId(Integer BarterOrderId) {
        BarterOrderResult barterOrderResult = new BarterOrderResult();
        BarterOrderExample barterOrderExample = new BarterOrderExample();
        BarterOrderExample.Criteria criteria = barterOrderExample.createCriteria();
        criteria.andBarterOrderIdEqualTo(BarterOrderId);
        List<BarterOrder> barterOrders = barterOrderMapper.selectByExample(barterOrderExample);
        BarterOrder barterOrder;
        barterOrder = barterOrders.get(0);
        barterOrderResult.setBarterOrder(barterOrder);
        BarterOrderItemExample barterOrderItemExample = new BarterOrderItemExample();
        BarterOrderItemExample.Criteria criteria1 = barterOrderItemExample.createCriteria();
        criteria.andBarterOrderIdEqualTo(barterOrder.getBarterOrderId());
        List<BarterOrderItem> barterOrderItems = barterOrderItemMapper.selectByExample(barterOrderItemExample);
        barterOrderResult.setBarterOrderItems(barterOrderItems);
        return barterOrderResult;
    }

    public List<BarterOrderResult> queryByUid(Integer uid) {
        List<BarterOrderResult> barterOrderResults = new ArrayList<BarterOrderResult>();
        BarterOrderItemExample barterOrderItemExample = new BarterOrderItemExample();
        BarterOrderItemExample.Criteria criteria1 = barterOrderItemExample.createCriteria();
        criteria1.andUid1EqualTo(uid);
        List<BarterOrderItem> barterOrderItems = barterOrderItemMapper.selectByExample(barterOrderItemExample);//查出所有作为发送人的子订单
        for (BarterOrderItem barterOrderItem : barterOrderItems){
            BarterOrderResult barterOrderResult = new BarterOrderResult();
            BarterOrderExample barterOrderExample = new BarterOrderExample();
            BarterOrderExample.Criteria criteria = barterOrderExample.createCriteria();
            criteria.andBarterOrderIdEqualTo(barterOrderItem.getOrderId());
            List<BarterOrder> barterOrders = barterOrderMapper.selectByExample(barterOrderExample);
            BarterOrder barterOrder = barterOrders.get(0);//按照作为发送人子订单查出的订单号

            BarterOrderItemExample barterOrderItemExample1 = new BarterOrderItemExample();
            BarterOrderItemExample.Criteria criteria2 = barterOrderItemExample1.createCriteria();
            criteria2.andOrderIdEqualTo(barterOrder.getBarterOrderId());
            criteria2.andUid1NotEqualTo(barterOrderItem.getUid1());
            BarterOrderItem barterOrderItem1 = barterOrderItemMapper.selectByExample(barterOrderItemExample).get(0);//查出另外一个收货人子订单

            List<BarterOrderItem> barterOrderItemList = new ArrayList<BarterOrderItem>();
            barterOrderItemList.add(barterOrderItem);
            barterOrderItemList.add(barterOrderItem1);
            barterOrderResult.setBarterOrder(barterOrder);
            barterOrderResult.setBarterOrderItems(barterOrderItemList);

            barterOrderResults.add(barterOrderResult);
        }
        return barterOrderResults;
    }
    */

}
