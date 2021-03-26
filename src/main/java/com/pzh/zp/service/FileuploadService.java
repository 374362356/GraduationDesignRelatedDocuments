package com.pzh.zp.service;

import com.pzh.zp.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

/**
 * 文件上传下载表(Fileupload)表服务接口
 *
 * @author makejava
 * @since 2021-03-24 23:47:38
 */
public interface FileuploadService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Fileupload queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Fileupload> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param fileName
     * @param files
     * @return
     * @throws ParseException
     */
    Fileupload insert(String fileName, MultipartFile files) throws ParseException;

    /**
     * 修改数据
     *
     * @param fileupload 实例对象
     * @return 实例对象
     */
    Fileupload update(Fileupload fileupload);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    Fileupload fuzzyFile(String name);
}
