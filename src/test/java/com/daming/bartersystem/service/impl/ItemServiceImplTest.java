package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.entitys.Item;
import com.daming.bartersystem.service.ItemService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class ItemServiceImplTest extends TestCase {
    @Autowired
    private ItemService itemService;
    @Test
    public void testQuery() throws Exception {
        System.out.println(itemService.query(1));
    }
    @Test
    public void testQueryList() throws Exception {
        System.out.println(itemService.queryList("b"));
    }
    @Test
    public void testQueryByUid() throws Exception {
        System.out.println(itemService.queryByUid(1));
    }
    /*
        0为更新失败，1为更新成功
     */
    @Test
    public void testUpdate(){
        Item item = itemService.query(1);
        item.setUid(2);
        System.out.println(itemService.update(item));
    }

}