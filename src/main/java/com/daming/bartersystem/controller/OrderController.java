package com.daming.bartersystem.controller;

import com.daming.bartersystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

}
