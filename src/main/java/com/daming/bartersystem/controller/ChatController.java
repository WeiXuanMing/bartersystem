package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class ChatController {

    // 聊天室房间号生成策略,以uid+uuid随机数为房间号
    @GetMapping("/createChatRoom")
    @ResponseBody
    public Result createChatRoom(HttpSession session){
        Integer uid = (Integer) session.getAttribute("uid");
        Result result = new Result(0,"failure",null);
        if(uid != null){
            String charRoomId = uid + "" + UUID.randomUUID();
            result = new Result(0,"succeed",charRoomId);
            return result;
        }else {
            //isNotLogin
            result = new Result(0,"isNotLogin",null);
            return result;
        }
    }
}
