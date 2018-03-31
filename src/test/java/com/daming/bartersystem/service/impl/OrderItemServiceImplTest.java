package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.entitys.BarterOrderItem;
import com.daming.bartersystem.service.OrderItemService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class OrderItemServiceImplTest extends TestCase {
    @Autowired
    private OrderItemService orderItemService;
    public void testQueryByOrderItemId() throws Exception {
    }
    @Test
    public void testInsert() throws Exception {
        BarterOrderItem barterOrderItem = new BarterOrderItem();
        barterOrderItem.setOrderId(1);
        barterOrderItem.setItemId(3);
        barterOrderItem.setUid1(3);
        barterOrderItem.setUid2(1);
        barterOrderItem.setOrderItemState(0);
        System.out.println(orderItemService.insert(barterOrderItem));
        System.out.println(barterOrderItem);
    }

    public void testUpdateByBarterOrderItem() throws Exception {

    }

    @Test
    public void testQueryByOrderIdUidUidItemId() throws  Exception{
        BarterOrderItem item = orderItemService.queryByOrderIdUidUidItemId(14,2,1,4);
        System.out.println(item);
    }

}