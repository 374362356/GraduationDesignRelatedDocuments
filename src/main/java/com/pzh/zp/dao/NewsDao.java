package com.pzh.zp.dao;

import com.pzh.zp.entity.News;
import com.pzh.zp.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 公告表(News)表数据库访问层
 *
 * @author makejava
 * @since 2021-01-25 16:45:27
 */
public interface NewsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    News queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<News> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param news 实例对象
     * @return 对象列表
     */
    List<News> queryAll(News news);

    /**
     * 新增数据
     *
     * @param news 实例对象
     * @return 影响行数
     */
    int insert(News news);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<News> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<News> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<News> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<News> entities);

    /**
     * 修改数据
     *
     * @param news 实例对象
     * @return 影响行数
     */
    int update(News news);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 模糊查询
     *
     * @param newTime
     * @param contentTitle
     * @return 对象列表
     */
    List<News> fuzzySearch(String newTime, String contentTitle);

    /**
     * 根据公告id和state_id得到发布人的id和nickname
     */
    User findPublishName(Integer id,Integer stateId);

    Integer counting();
}