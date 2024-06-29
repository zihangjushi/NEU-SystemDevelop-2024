package com.sunbw.demo.controller;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class ImageController {


    @PostMapping("/upload")
    public Map upload(MultipartFile file){

        Map map = new HashMap();
        if (file.isEmpty()) {
            map.put("isOk",false);
            return map;
        }

        //重命名
        String oriFilename = file.getOriginalFilename();
        String ext = oriFilename.substring(oriFilename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString().replace("-","");

        String fileName = uuid+ext;

        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String pre=applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath()+
                "\\src\\main\\resources\\static\\images\\";
        String path=pre+fileName;

        String urlPath = "http://localhost:9000/images/"+fileName;
        System.out.println(urlPath);


        try {
            file.transferTo(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        map.put("isOk",true);
        map.put("fileName",path);
        return map;
    }


}
