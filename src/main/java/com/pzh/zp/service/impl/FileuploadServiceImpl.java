package com.pzh.zp.service.impl;

import com.pzh.zp.dao.FileuploadDao;
import com.pzh.zp.entity.Fileupload;
import com.pzh.zp.service.FileuploadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
     * @param fileupload 实例对象
     * @return 实例对象
     */
    @Override
    public Fileupload insert(Fileupload fileupload) {
        this.fileuploadDao.insert(fileupload);
        return fileupload;
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
    public boolean deleteById(Integer id) {
        return this.fileuploadDao.deleteById(id) > 0;
    }
}
