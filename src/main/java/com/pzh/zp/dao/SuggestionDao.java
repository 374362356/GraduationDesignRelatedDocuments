package com.pzh.zp.dao;

import com.pzh.zp.entity.Suggestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;



/**
 * 建议表(Suggestion)表数据库访问层
 *
 * @author makejava
 * @since 2021-01-25 16:45:34
 */
public interface SuggestionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Suggestion queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Suggestion> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param suggestion 实例对象
     * @return 对象列表
     */
    List<Suggestion> queryAll(Suggestion suggestion);

    /**
     * 新增数据
     *
     * @param suggestion 实例对象
     * @return 影响行数
     */
    int insert(Suggestion suggestion);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Suggestion> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Suggestion> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Suggestion> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Suggestion> entities);

    /**
     * 修改数据
     *
     * @param suggestion 实例对象
     * @return 影响行数
     */
    int update(Suggestion suggestion);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}