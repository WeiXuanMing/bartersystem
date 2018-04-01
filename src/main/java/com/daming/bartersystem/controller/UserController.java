package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.*;
import com.daming.bartersystem.entitys.User;
import com.daming.bartersystem.entitys.UserBarterUnformation;
import com.daming.bartersystem.service.UserBarterInformationService;
import com.daming.bartersystem.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Action;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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

    @RequestMapping(value = "/updateUserData", method = RequestMethod.POST, consumes = "application/json",produces="application/json")
    @ResponseBody
    public Result updateUserData(@RequestBody String param,HttpSession session) throws IOException {
        Integer uid = (Integer) session.getAttribute("uid");
        Result result = new Result(0,"failure",null);
        if (uid != null ){
            //login
            System.out.println("接受到的json格式是这个样子的："+param);
            param = new String(param.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println("修改编码后的json格式是这个样子的："+param);
            System.out.println("已经登录了耶~~(＾－＾)V");
            ObjectMapper objectMapper = new ObjectMapper();
            UserDataAccepter userDataAccepter = objectMapper.readValue(param, UserDataAccepter.class);
            String newUsername = userDataAccepter.getUsername();
            String newPhone =  userDataAccepter.getPhone();
            String newEmail = userDataAccepter.getEmail();
            System.out.println("获取到的username是：" +newUsername);
            System.out.println("获取到的Phone是：" + newPhone);
            System.out.println("获取到的Email是" + newEmail);
            User user = userService.queryByUid(uid);
            user.setUsername(newUsername);
            user.setPhone(newPhone);
            user.setEmail(newEmail);
            Integer count = 0;
            count = userService.updateUser(user);
            if (count == 1){
                result = new Result(0,"succeed",null);
                return  result;
            }else {
                result = new Result(0,"updatefailure",null);
                return  result;
            }

        }else {
            //not login
            result = new Result(0,"isNotLogin",null);
            return  result;
        }
    }
    @RequestMapping(value = "/updateUserPassword", method = RequestMethod.POST, consumes = "application/json",produces="application/json")
    @ResponseBody
    public Result updateUserPassword(@RequestBody String param,HttpSession session){
        Integer uid = (Integer) session.getAttribute("uid");
        Result result = new Result(0,"failure",null);
        if (uid != null ){
            //login

        }else {
            //not login
            result = new Result(0,"isNotLogin",null);
            return  result;
        }
    }

}
