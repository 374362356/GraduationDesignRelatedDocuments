package com.pzh.zp.service;

import com.pzh.zp.entity.News;
import com.pzh.zp.entity.Staff;

import java.util.List;


/**
 * 公告表(News)表服务接口
 *
 * @author makejava
 * @since 2021-01-25 16:45:28
 */
public interface NewsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    News queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<News> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param news 实例对象
     * @return 实例对象
     */
    News insert(News news);

    /**
     * 修改数据
     *
     * @param news 实例对象
     * @return 实例对象
     */
    News update(News news);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param news 实例对象
     * @return 对象列表
     */
    List<News> queryAll(News news);

    /**
     * 模糊查询
     *
     * @param newTime
     * @param contentTitle
     * @return 对象列表
     */
    List<News> fuzzySearch(String newTime, String contentTitle);

}