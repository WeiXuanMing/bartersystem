package com.daming.bartersystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ServiceFactory {

    @Autowired
    public static UserService userService;
}
