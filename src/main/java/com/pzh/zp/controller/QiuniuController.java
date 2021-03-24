package com.pzh.zp.controller;

import com.pzh.zp.utils.QiuniuUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class QiuniuController {

    @PostMapping("/fileUpload")
    public String fileUpload(@RequestParam("fileName") String fileName,
                             @RequestParam(name = "files",required = false) MultipartFile files){

        System.out.println(fileName+"=====>"+QiuniuUtil.getUploadUrl(files));
        return QiuniuUtil.getUploadUrl(files);
    }


}
