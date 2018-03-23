package com.daming.bartersystem.service;

import com.daming.bartersystem.DTO.Register;
import com.daming.bartersystem.entitys.User;
import org.springframework.beans.factory.annotation.Autowired;

public class registerServiceImpl implements registerService{

    @Autowired
    private UserService userService;

    public boolean register(Register register) {
        if(userService.queryByLoginAccount(register.getLoginAccount()) != null && userService.queryByUsername(register.getUsername())!=null){
            User user = new User(register.getLoginAccount(),register.getUsername(),register.getPassword(),register.getPhone(),register.getEmail());
            return userService.addUser(user);
         }
        return false;
    }
}
