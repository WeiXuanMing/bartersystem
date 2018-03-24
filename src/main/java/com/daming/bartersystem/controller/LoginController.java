package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.LoginResult;
import com.daming.bartersystem.DTO.Loginer;
import com.daming.bartersystem.DTO.Result;
import com.daming.bartersystem.entitys.User;
import com.daming.bartersystem.service.LoginService;
import com.daming.bartersystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@SessionAttributes(value = {"uid","uuid"})
public class LoginController {
    private final String salt = "daming";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
   @ModelAttribute
   public Loginer addLoginer(){
       return new Loginer();
   }
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Result<LoginResult> login(@RequestBody Map<String, String> map, ModelMap model){
        Result<LoginResult> loginResult = new Result<LoginResult>(0,"failure",new LoginResult(""));
        Loginer loginer = new Loginer();
        loginer.setLoginAccount(map.get("loginAccount"));
        loginer.setPassword(map.get("password"));
        loginer.setLogintype(map.get("logintype"));
        logger.debug(loginer.toString());
        boolean result= loginService.checkLogin(loginer);
        logger.debug("检查结果："+result);
        if (result){
            loginResult.setMessage("succeed");
            User user = userService.queryByLoginAccount(loginer.getLoginAccount());
            Integer uid = user.getUid();
            String password = user.getPassword();
            model.addAttribute("uid",uid);
            String base = uid +"/"+salt+"/"+password;
            String uuid = DigestUtils.md5DigestAsHex(base.getBytes());
            logger.debug(uuid);
            model.addAttribute("uuid",uuid);
            if (loginer.getLogintype().equals("user")){
                loginResult.setData(new LoginResult("user"));
            }else if (loginer.getLogintype().equals("admin")){
                loginResult.setData(new LoginResult("admin"));
            }
        }
        return loginResult;
    }
    @PostMapping("/checklogin")
    public String checkLogin(HttpSession session){
        String uuid = (String) session.getAttribute("uuid");
        Integer uid = (Integer)session.getAttribute("uid");
        if (uuid == null){
            return "login";
        }else {
            String rea_uuid = getUUIDByUid(uid);
            if(rea_uuid.equals(uuid)){
                return "redirect:/userInformation";
            }else {
                return "login";
            }
        }


    }
    private String getUUIDByUid(Integer uid){
        User user = userService.queryByUid(uid);
        String password = user.getPassword();
        String base = uid +"/"+salt+"/"+password;
        String uuid = DigestUtils.md5DigestAsHex(base.getBytes());
        return uuid;
    }

}
