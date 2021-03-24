package com.pzh.zp.utils;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class QiuniuUtil {

    public static String accessKey = "paWCYuCarLFbl4uv8DATd8KLcbG3clogDOijz5q1";
    public static String secretKey = "Q-f8sDCKMx4JLO2CBtjnCdG6gyxu_PZBrtu1fOJG";
    public static String bucket = "yhl-cv";
    public static String TheHeaders = "http://yhlssh.top/";

    public static Configuration cfg = new Configuration(Region.region2());

    //文件上传
    public static String getUploadUrl(MultipartFile multipartFile){
        try {
            UploadManager uploadManager = new UploadManager(cfg);
            InputStream is = multipartFile.getInputStream();
            Response response = uploadManager
                    .put(is,multipartFile.getOriginalFilename()
                            ,getToken(multipartFile.getOriginalFilename())
                            ,null,null);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return TheHeaders+putRet.key;
        }catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getHeadUploadUrl(String maker,MultipartFile multipartFile,String id){
        try {
            UploadManager uploadManager = new UploadManager(cfg);
            InputStream is = multipartFile.getInputStream();
            String filepath = maker+"/"
                    +id+"/"
                    +multipartFile.getOriginalFilename();

            Response response = uploadManager
                    .put(is,filepath
                            ,getToken(filepath)
                            ,null,null);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

            return "http://yhlssh.top/"+putRet.key;
        }catch(
                IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *删除
     * @param key
     * @return
     * @throws QiniuException
     */
    public static String delete(String key){
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketMgr = new BucketManager(auth, cfg);
        try {
            bucketMgr.delete(bucket,key);
        }catch (QiniuException Q){
            return Q.toString();
        }
        return null;
    }

    public static String getToken(String key) {
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucket, key);
    }
}