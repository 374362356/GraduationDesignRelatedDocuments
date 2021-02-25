package com.pzh.zp.service;

import com.pzh.zp.entity.Reach;

import java.util.List;



/**
 * 到会时间表(Reach)表服务接口
 *
 * @author makejava
 * @since 2021-01-25 16:45:30
 */
public interface ReachService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Reach queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Reach> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param reach 实例对象
     * @return 实例对象
     */
    Reach insert(Reach reach);

    /**
     * 修改数据
     *
     * @param reach 实例对象
     * @return 实例对象
     */
    Reach update(Reach reach);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}