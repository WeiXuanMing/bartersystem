package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.service.OrderService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class OrderServiceImplTest extends TestCase {

    @Test
    public void testCreateOrder() throws Exception {
        System.out.println(orderService.CreateOrder(1,2));
    }
    @Test
    public void testQueryByUidAndItemId() throws Exception {
        System.out.println(orderService.queryByUidAndItemId(1,2));
    }
    @Test
    public void testQueryByUid() throws Exception {
        System.out.println(orderService.queryByUid(1));
    }

    @Autowired
    private OrderService orderService;



}