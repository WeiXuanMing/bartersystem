package com.daming.bartersystem.controller;

import com.daming.bartersystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PIMController {

    @Autowired
    private UserService userService;


}
