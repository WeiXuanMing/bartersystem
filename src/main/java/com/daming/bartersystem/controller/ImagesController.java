package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.ImageUploadResult;
import com.daming.bartersystem.DTO.ItemImageUploadAccepter;
import com.daming.bartersystem.DTO.Result;
import com.daming.bartersystem.DTO.UploadIconAccepter;
import com.daming.bartersystem.entitys.Item_Image;
import com.daming.bartersystem.service.ImageUpload;
import com.daming.bartersystem.service.ItemImageUpload;
import com.daming.bartersystem.service.Item_ImageService;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

@Controller
public class ImagesController {
    @Autowired
    private ImageUpload imageUpload;
    @Autowired
    private ItemImageUpload itemImageUpload;
    @Autowired
    private Item_ImageService item_imageService;
    @PostMapping("/uploadImage")
    @ResponseBody
    public Result<ImageUploadResult> GenerateImage(@RequestParam("image")CommonsMultipartFile file, HttpServletRequest request){
        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            System.out.println(paraName+": "+request.getParameter(paraName));
        }
        //根据相对路径获取绝对路径，图片上传后位于元数据中
        String realUploadPath= request.getSession().getServletContext().getRealPath("/");
        //获取上传后原图的相对地址
        String imageUrl=imageUpload.uploadImage(file, realUploadPath);
        if (imageUrl != null) {
            String path = request.getContextPath();
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/images/";
            ImageUploadResult imageUploadResult = new ImageUploadResult();
            imageUploadResult.setUrl(basePath+imageUrl);
            Result<ImageUploadResult> result = new Result<ImageUploadResult>(0,"succeed", imageUploadResult);
            return result;
        }

        Result<ImageUploadResult> result = new Result<ImageUploadResult>(0,"failure", null);
        return result;
    }

    @PostMapping("/ItemImageUpload")
    @ResponseBody
    public Result<ImageUploadResult> ItemImageUpload(HttpServletRequest request,HttpSession session, @RequestBody String param) throws IOException {
        Integer uid = (Integer) session.getAttribute("uid");
        Result<ImageUploadResult> result = new Result(0,"failure",null);
        if(uid != null){
            //login
            ObjectMapper objectMapper = new ObjectMapper();
            ItemImageUploadAccepter itemImageUploadAccepter = objectMapper.readValue(param, ItemImageUploadAccepter.class);
            System.out.println("接受到的itemImageid是："+itemImageUploadAccepter.getItemId());
            System.out.println("接受到的itemImageBASE64编码是："+itemImageUploadAccepter.getImage());
            Base64 base64 = new Base64();
            byte[] icondecode = base64.decodeBase64(itemImageUploadAccepter.getImage().substring(22));

            //根据相对路径获取绝对路径，图片上传后位于元数据中
            String realUploadPath= session.getServletContext().getRealPath("/");
            File mainFileDir = new File(realUploadPath+"/items/");
            System.out.println(" 物品封面文件真正的主文件目录路径是"+mainFileDir.getAbsolutePath());
            if (!mainFileDir.isDirectory()) {
                mainFileDir.mkdir();
            }
            String base64head = itemImageUploadAccepter.getImage().substring(0,21);
            int i1 = base64head.indexOf("/");
            int i2 = base64head.indexOf(";");
            String filelastname = base64head.substring(i1+1,i2);
            System.out.println("文件结尾是"+filelastname);
            String newFilename = UUID.randomUUID()+
                    "."+filelastname;
            File saveFile = new File(mainFileDir,newFilename);
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            fileOutputStream.write(icondecode);
            fileOutputStream.flush();
            fileOutputStream.close();
            String path = request.getContextPath();
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/items/";
            ImageUploadResult imageUploadResult = new ImageUploadResult();
            imageUploadResult.setUrl(basePath+newFilename);
            Item_Image item_image = new Item_Image();
            item_image.setItemid(itemImageUploadAccepter.getItemId());
            item_image.setImageurl(basePath+newFilename);
            item_imageService.insert(item_image);
            result = new Result<ImageUploadResult>(0,"succeed", imageUploadResult);
            return result;
        }else {
            //isNotLogin
            result = new Result(0,"isNotLogin",null);
            return result;
        }
    }

}
