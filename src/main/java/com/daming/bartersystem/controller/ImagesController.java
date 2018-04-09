package com.daming.bartersystem.controller;

import com.daming.bartersystem.DTO.ImageUploadResult;
import com.daming.bartersystem.DTO.Result;
import com.daming.bartersystem.service.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Controller
public class ImagesController {
    @Autowired
    private ImageUpload imageUpload;

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

}
