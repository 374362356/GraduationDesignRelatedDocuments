package com.pzh.zp.dao;

import com.pzh.zp.entity.Reach;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 到会时间表(Reach)表数据库访问层
 *
 * @author makejava
 * @since 2021-01-25 16:45:30
 */
public interface ReachDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Reach queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Reach> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param reach 实例对象
     * @return 对象列表
     */
    List<Reach> queryAll(Reach reach);

    /**
     * 新增数据
     *
     * @param reach 实例对象
     * @return 影响行数
     */
    int insert(Reach reach);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Reach> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Reach> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Reach> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Reach> entities);

    /**
     * 修改数据
     *
     * @param reach 实例对象
     * @return 影响行数
     */
    int update(Reach reach);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}