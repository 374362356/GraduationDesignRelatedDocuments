package com.pzh.zp.dao;

import com.pzh.zp.VO.ConferUpdate;
import com.pzh.zp.VO.ConferenceVo;
import com.pzh.zp.entity.Conference;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会议表(Conference)表数据库访问层
 *
 * @author makejava
 * @since 2021-01-25 16:45:25
 */
public interface ConferenceDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Conference queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Conference> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param conference 实例对象
     * @return 对象列表
     */
    List<Conference> queryAll(Conference conference);

    /**
     * 新增数据
     *
     * @param conference 实例对象
     * @return 影响行数
     */
    int insert(Conference conference);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Conference> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Conference> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Conference> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Conference> entities);

    /**
     * 修改数据
     *
     * @param conference 实例对象
     * @return 影响行数
     */
    int update(Conference conference);

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
     * @param name
     * @param place
     * @param host
     * @return 对象列表
     */
    List<Conference> fuzzySearch(String name, String place,String host);

    List<String> queryAllName();

    /**
     *
     * @param name
     * @return
     */
    int queryIdByName(String name);

    /**
     *
     * @return
     */
    Integer counting();

    List<Conference> findConferenceByplace(String place);

    /**
     *
     * @return
     */
    List<ConferenceVo> findConferenceInfo();

    List<ConferUpdate> findConferStatus();
}