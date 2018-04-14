package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.Result;
import com.daming.bartersystem.DTO.UploadIconAccepter;
import com.daming.bartersystem.service.user_iconService;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Controller
public class IconController {
    @Autowired
    private user_iconService userIconService;

    @PostMapping("/updateIcon")
    @ResponseBody
    public Result uploadIcon(HttpSession session, @RequestBody String param) throws IOException {
        Integer uid = (Integer) session.getAttribute("uid");
        Result result = new Result(0,"failure",null);
        if(uid != null){
            //login
            ObjectMapper objectMapper = new ObjectMapper();
            UploadIconAccepter uploadIconAccepter = objectMapper.readValue(param, UploadIconAccepter.class);
            System.out.println("接受到的iconType是："+uploadIconAccepter.getType());
            System.out.println("接受到的IconBASE64编码是："+uploadIconAccepter.getIcon());
            Base64 base64 = new Base64();
            byte[] icondecode = base64.decodeBase64(uploadIconAccepter.getIcon().substring(22));

            //根据相对路径获取绝对路径，图片上传后位于元数据中
            String realUploadPath= session.getServletContext().getRealPath("/");
            File mainFileDir = new File(realUploadPath+"/userphoto/");
            System.out.println("头像文件真正的主文件目录路径是"+mainFileDir.getAbsolutePath());
            if (!mainFileDir.isDirectory()) {
                mainFileDir.mkdir();
            }
            File userFileDir = new File(mainFileDir,uid.toString());
            System.out.println("头像文件真正的用户主文件目录路径是"+mainFileDir.getAbsolutePath());
            if (!userFileDir.isDirectory()) {
                userFileDir.mkdir();
            }
            File saveFile = new File(userFileDir,"/userphoto.png");
            System.out.println("头像文件真正的文件路径是"+saveFile.getAbsolutePath());
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            fileOutputStream.write(icondecode);
            fileOutputStream.flush();
            fileOutputStream.close();
            result = new Result(0,"succeed",null);
            return result;
        }else {
            //isNotLogin
            result = new Result(0,"isNotLogin",null);
            return result;
        }
    }
}
