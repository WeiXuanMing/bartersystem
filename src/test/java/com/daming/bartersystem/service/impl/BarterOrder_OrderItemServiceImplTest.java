package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.service.BarterOrder_OrderItemService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class BarterOrder_OrderItemServiceImplTest extends TestCase {
    @Autowired
    private BarterOrder_OrderItemService barterOrder_orderItemService;
    @Test
    public void testQueryByOrderId() throws Exception {
        System.out.println(barterOrder_orderItemService.queryByOrderId(6));
    }

    public void testAddBarterOrder_OrderItem() throws Exception {


    }

}