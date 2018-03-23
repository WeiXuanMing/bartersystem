package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.dao.UserMapper;
import com.daming.bartersystem.entitys.User;
import com.daming.bartersystem.service.UserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class UserServiceImplTest extends TestCase {
    @Autowired
    private UserService userService;
    @Test
    public void testQueryByUid() throws Exception {
        System.out.println(userService.queryByUid(1));
    }
    @Test
    public void testAddUser() throws Exception {
        User user = new User("abcde","tom","123","12312312312","123123@qq.com",new BigDecimal(12.21),1,0);
        System.out.println(userService.addUser(user));
        System.out.println(user);
    }
    @Test
    public void testUpdateUser() throws Exception {
        User user = userService.queryByUid(2);
        user.setPassword("123123123");
        System.out.println(userService.updateUser(user));
    }

}