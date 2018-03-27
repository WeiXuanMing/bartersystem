package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.Result;
import com.daming.bartersystem.DTO.UserInformationResult;
import com.daming.bartersystem.DTO.UserSimpleInfoResult;
import com.daming.bartersystem.entitys.User;
import com.daming.bartersystem.entitys.UserBarterUnformation;
import com.daming.bartersystem.service.UserService;
import com.daming.bartersystem.service.impl.UserBarterInformationService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Action;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserBarterInformationService userBarterInformationService;

    @PostMapping("/getUI")
    @ResponseBody
    public Result<UserInformationResult> getUserInformation(HttpSession session){
        System.out.println("Session:"+(Integer) session.getAttribute("uid")+" "+(String)session.getAttribute("uuid"));
        Integer uid = (Integer)session.getAttribute("uid");
        String uuid = (String) session.getAttribute("uuid");
        UserInformationResult userInformationResult = new UserInformationResult();
        Result<UserInformationResult> result = new Result<UserInformationResult>(0,"isNotLogin",userInformationResult);
        boolean isLogin = false;
        if(uid != null && uuid != null){
                isLogin = userService.CheckUUID(uid,uuid);
        }else {
            return result;
        }
        User user;
        if (isLogin){
            user = userService.queryByUid(uid);
        }else {
            return result;
        }
        if (user!=null){
            userInformationResult.setUsername(user.getUsername());
            userInformationResult.setPhone(user.getPhone());
            userInformationResult.setEmail(user.getEmail());
            userInformationResult.setBalance(user.getBalance());
            result = new Result<UserInformationResult>(0,"isLogin",userInformationResult);
        }else {
            return result;
        }
        return result;
    }
    @GetMapping("/getUserSimpleInfo/{uid}")
    @ResponseBody
    public Result<UserSimpleInfoResult> getUIByUid(@PathVariable("uid") Integer uid){
        Result<UserSimpleInfoResult> result = new Result<UserSimpleInfoResult>(0,"failure",new UserSimpleInfoResult());
        if (uid != null) {
            User user = userService.queryByUid(uid);
            if (user != null){
                UserBarterUnformation userBarterUnformation = userBarterInformationService.getUBInfoByUid(uid);
                UserSimpleInfoResult userSimpleInfoResult = new UserSimpleInfoResult();
                userSimpleInfoResult.setUsername(user.getUsername());
                userSimpleInfoResult.setPhone(user.getPhone());
                userSimpleInfoResult.setEmail(user.getEmail());
                userSimpleInfoResult.setConsignee(userBarterUnformation.getConsignee());
                userSimpleInfoResult.setConsignee_phone(userBarterUnformation.getConsigneePhone());
                userSimpleInfoResult.setConsignee_address(userBarterUnformation.getConsigneeAddress());
                userSimpleInfoResult.setConsignee_postalcode(userBarterUnformation.getConsigneePostalcode());
                return new Result<UserSimpleInfoResult>(0,"succeed",userSimpleInfoResult);
            }

        }
        return result;

    }


}
