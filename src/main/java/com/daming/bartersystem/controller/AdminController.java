package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.AdminGetAllUserInfoResult;
import com.daming.bartersystem.DTO.Result;
import com.daming.bartersystem.DTO.WaitingForAuditResult;
import com.daming.bartersystem.entitys.AdminInformation;
import com.daming.bartersystem.entitys.Item;
import com.daming.bartersystem.entitys.User;
import com.daming.bartersystem.service.AdminService;
import com.daming.bartersystem.service.ItemService;
import com.daming.bartersystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes(value = {"adminId"})
public class AdminController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;
    /*
    * 获取所有等待通过审核的物品
    * */
    @GetMapping("/getWaitingForAuditItems")
    @ResponseBody
    public Result<WaitingForAuditResult> getWaitingForAuditItems(HttpSession session){
        Integer adminId = (Integer) session.getAttribute("adminId");
        WaitingForAuditResult waitingForAuditResult = new WaitingForAuditResult();
        Result<WaitingForAuditResult> result = new Result<WaitingForAuditResult>(0,"failure",waitingForAuditResult);
        if (adminId != null){
            //登录
            List<Item> waitingForAuditItems = adminService.queryAll();
            waitingForAuditResult.setItems(waitingForAuditItems);
            return result = new Result<WaitingForAuditResult>(0,"succeed",waitingForAuditResult);
        }else {
            //未登录
            return result = new Result<WaitingForAuditResult>(0,"isNotLogin",waitingForAuditResult);
        }
    }
    /*
        物品审核通过
    */
    @GetMapping("/AuditItem/{itemId}")
    @ResponseBody
    public Result AuditItem(@PathVariable("itemId") Integer itemId,HttpSession session){
        Integer adminId = (Integer) session.getAttribute("adminId");
        Result result = new Result(0,"failure",null);
        if (adminId != null){
            //登录
            boolean isSucceed = itemService.onSale(itemId);
            if (isSucceed){
                return  result = new Result(0,"succeed",null);
            }else {
                return  result;
            }
        }else {
            //未登录
            return result = new Result<WaitingForAuditResult>(0,"isNotLogin",null);
        }
    }
    /*
    * ban用户帐号
    * */
    @GetMapping("/banAccount/{uid}")
    @ResponseBody
    public Result banAccount(@PathVariable("uid") Integer uid,HttpSession session){
        Integer adminId = (Integer) session.getAttribute("adminId");
        Result result = new Result(0,"failure",null);
        if (adminId != null){
            //登录
            boolean isSucceed = adminService.banByUid(uid);
            if (isSucceed){
                return  result = new Result(0,"succeed",null);
            }else {
                return  result;
            }
        }else {
            //未登录
            return result = new Result<WaitingForAuditResult>(0,"isNotLogin",null);
        }
    }
     /*
    * 解封用户帐号
    * */
     @GetMapping("/unBanAccount/{uid}")
     @ResponseBody
     public Result unBanAccount(@PathVariable("uid") Integer uid,HttpSession session){
         Integer adminId = (Integer) session.getAttribute("adminId");
         Result result = new Result(0,"failure",null);
         if (adminId != null){
             //登录
             boolean isSucceed = adminService.unBanByUid(uid);
             if (isSucceed){
                 return  result = new Result(0,"succeed",null);
             }else {
                 return  result;
             }
         }else {
             //未登录
             return result = new Result<WaitingForAuditResult>(0,"isNotLogin",null);
         }
     }

     /*
     * 获取所有用户
     * */
    @GetMapping("/getAllUserInfo")
    @ResponseBody
    public Result<AdminGetAllUserInfoResult> getAllUserInfo(HttpSession session){
        Integer adminId = (Integer) session.getAttribute("adminId");
        AdminGetAllUserInfoResult adminGetAllUserInfoResult = new AdminGetAllUserInfoResult();
        Result result = new Result(0,"failure",adminGetAllUserInfoResult);
        if (adminId != null){
            //登录
            List<User> users = adminService.getAllUserInfo();
            adminGetAllUserInfoResult.setUsers(users);
            return result = new Result(0,"succeed",adminGetAllUserInfoResult);
        }else {
            //未登录
            return result = new Result(0,"isNotLogin",adminGetAllUserInfoResult);
        }
    }
    /*
    * 管理员登录
    * */
    @RequestMapping(value = "/AdminLogin", method = RequestMethod.POST, consumes = "application/json",produces="application/json")
    @ResponseBody
    public Result adminLogin(@RequestBody Map<String, String> map, ModelMap model,HttpSession session){
        String adminAccout = map.get("loginAccount");
        String password = map.get("password");
        Result result = new Result(0,"failure",null);
        AdminInformation admin = adminService.login(adminAccout,password);
        if (admin != null){
            Integer adminId = admin.getAdminId();
            model.addAttribute("adminId", adminId);
            result = new Result(0,"succeed",null);
            return result;
        }else {
            return result;
        }
    }

    @GetMapping("/AdminLogin")
    public String showAdminLogin(){
        return "admin_login";
    }
}
