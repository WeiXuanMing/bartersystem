package com.daming.bartersystem.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

@Service
public class ItemImageUpload {

    /*
    * 上传图片并返回图片的保存名称
    * */
    public String uploadImage(CommonsMultipartFile file, String realUploadPath){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String filePath = realUploadPath + "/items/";
        File fileDir = new File(filePath);

        if (!fileDir.isDirectory()) {
            fileDir.mkdir();
        }
        if (file != null && file.getOriginalFilename() != null && file.getOriginalFilename().length() > 0) {
            //获取文件原名
            String fileOriginalFilename = file.getOriginalFilename();

            //生成随机数
            String newFilename = year + "" + month + UUID.randomUUID() +
                    fileOriginalFilename.substring(fileOriginalFilename.lastIndexOf("."));
            //生成文件
            File newFile = new File(filePath + newFilename);
            System.out.println("文件保存在"+newFile.getAbsolutePath());
            try {
                file.transferTo(newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newFilename;
        }
        return null;
    }
}
