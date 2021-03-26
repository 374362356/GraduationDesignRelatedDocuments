package com.pzh.zp.controller;

import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.entity.Fileupload;
import com.pzh.zp.service.FileuploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

@RestController
@RequestMapping("/upload")
public class QiniuController {
    /**
     * 删除时远程数据库文件未删除
     */

    @Autowired
    private FileuploadService fileuploadService;

    @PostMapping("/file_upload")
    public ResultVo fileUpload(@RequestParam("fileName") String fileName,
                             @RequestParam(name = "files",required = false) MultipartFile files) throws ParseException {
        Fileupload insert = fileuploadService.insert(fileName, files);
        if(insert!=null){
            return ResultVo.success(insert);
        }
        return ResultVo.fail("上传失败");
    }

    @DeleteMapping("/file_delete")
    public  ResultVo deleteFile(@RequestParam Integer id){
        return ResultVo.success(fileuploadService.deleteById(id));
    }

    @GetMapping("/findFiles")
    public ResultVo findAll(@RequestParam int offset, @RequestParam int limit){
        return ResultVo.success(fileuploadService.queryAllByLimit(offset, limit));
    }

    @GetMapping("/fuzzyFile")
    public ResultVo fuzzyFiles(@RequestParam String name){
        return ResultVo.success(fileuploadService.fuzzyFile(name));
    }

    @PutMapping("/file_update")
    public ResultVo updateFiles(@RequestBody Fileupload fileupload){
        return ResultVo.success(fileuploadService.update(fileupload));
    }
}
