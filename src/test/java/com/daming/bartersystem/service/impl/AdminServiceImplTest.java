package com.daming.bartersystem.service.impl;

import com.daming.bartersystem.service.AdminService;
import junit.framework.TestCase;
import org.hibernate.validator.internal.constraintvalidators.bv.past.PastValidatorForReadableInstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class AdminServiceImplTest extends TestCase {
    @Autowired
    private AdminService adminService;
    @Test
    public void testBanByUid() throws Exception {
        adminService.banByUid(1);
    }

}