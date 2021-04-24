package com.pzh.zp.dao;

import com.pzh.zp.VO.PersonVo;
import com.pzh.zp.entity.Conference;
import com.pzh.zp.entity.Person;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 参会人员表(Person)表数据库访问层
 *
 * @author makejava
 * @since 2021-01-25 16:45:29
 */
public interface PersonDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Person queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Person> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param person 实例对象
     * @return 对象列表
     */
    List<Person> queryAll(Person person);

    /**
     * 新增数据
     *
     * @param person 实例对象
     * @return 影响行数
     */
    int insert(Person person);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Person> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Person> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Person> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Person> entities);

    /**
     * 修改数据
     *
     * @param person 实例对象
     * @return 影响行数
     */
    int update(Person person);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 获取参会人员的全信息
     * @return
     */
    List<PersonVo> queryAllInfoPerson();

    /**
     *
     * @param pname
     * @param name
     * @param phone
     * @return
     */
    List<Person> fuzzySearchPerson(String pname,String name,String phone);

    /**
     *
     * @param conference_id
     * @param person_id
     * @return
     */
    int insertActivity_person(Integer conference_id,Integer person_id);

    /**
     *
     * @param conference_id
     * @param person_id
     * @param id
     * @return
     */
    int updateActivity_person(Integer conference_id,Integer person_id,Integer id);

    Integer findIdByP_id(Integer person_id);

    /**
     *
     * @param person_id
     * @return
     */
    int findC_idByP_id(Integer person_id);

    List<Integer> findConferIdByPId(Integer id);

    int deleteActivity_person(Integer id);

    List<PersonVo> findPersonByConferId(Integer id);
}