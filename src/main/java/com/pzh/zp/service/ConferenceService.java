package com.pzh.zp.service;

import com.pzh.zp.entity.Conference;

import java.util.List;



/**
 * 会议表(Conference)表服务接口
 *
 * @author makejava
 * @since 2021-01-25 16:45:25
 */
public interface ConferenceService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Conference queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Conference> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param conference 实例对象
     * @return 实例对象
     */
    Conference insert(Conference conference);

    /**
     * 修改数据
     *
     * @param conference 实例对象
     * @return 实例对象
     */
    Conference update(Conference conference);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     *模糊查询
     *
     * @param name
     * @param place
     * @param host
     * @return 对象列表
     */
    List<Conference> fuzzySearch(String name, String place,String host);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param conference 实例对象
     * @return 对象列表
     */
    List<Conference> queryAll(Conference conference);
}