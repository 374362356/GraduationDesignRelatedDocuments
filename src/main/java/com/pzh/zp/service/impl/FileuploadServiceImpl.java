package com.pzh.zp.service.impl;

import com.pzh.zp.dao.FileuploadDao;
import com.pzh.zp.entity.Fileupload;
import com.pzh.zp.service.FileuploadService;
import com.pzh.zp.utils.QiniuUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 文件上传下载表(Fileupload)表服务实现类
 *
 * @author makejava
 * @since 2021-03-24 23:47:38
 */
@Service("fileuploadService")
public class FileuploadServiceImpl implements FileuploadService {
    @Resource
    private FileuploadDao fileuploadDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Fileupload queryById(Integer id) {
        return this.fileuploadDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Fileupload> queryAllByLimit(int offset, int limit) {
        return this.fileuploadDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param fileName
     * @param files
     * @return
     * @throws ParseException
     */
    @Override
    @Transactional
    public Fileupload insert(String fileName,MultipartFile files) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(new Date());
        Date date = dateFormat.parse(format);

        String uploadUrl = QiniuUtil.getUploadUrl(files);
        Fileupload file = new Fileupload(0, fileName, uploadUrl, date);
        this.fileuploadDao.insert(file);
        return file;
    }

    /**
     * 修改数据
     *
     * @param fileupload 实例对象
     * @return 实例对象
     */
    @Override
    public Fileupload update(Fileupload fileupload) {
        this.fileuploadDao.update(fileupload);
        return this.queryById(fileupload.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean deleteById(Integer id) {         //也删除了远程
        Fileupload fileupload = fileuploadDao.queryById(id);
        //这的key是文件名？
        String key=null;
        if(fileupload.getFileUrl().length()>0){
            key = fileupload.getFileUrl().substring(QiniuUtil.TheHeaders.length());
            QiniuUtil.delete(key);
        }
        System.out.println("=====删除了远程文件========>"+key);
        if(key!=null){
            return this.fileuploadDao.deleteById(id) > 0;
        }
        return false;
    }

    @Override
    public Fileupload fuzzyFile(String name) {
        return fileuploadDao.fuzzyFile(name);
    }
}
