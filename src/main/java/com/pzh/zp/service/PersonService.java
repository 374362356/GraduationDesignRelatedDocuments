package com.pzh.zp.service;

import com.pzh.zp.VO.PersonVo;
import com.pzh.zp.entity.Conference;
import com.pzh.zp.entity.Person;

import java.util.List;



/**
 * 参会人员表(Person)表服务接口
 *
 * @author makejava
 * @since 2021-01-25 16:45:29
 */
public interface PersonService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Person queryById(Integer id);

    PersonVo toUpdate(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Person> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param personVo 实例对象
     * @return 实例对象
     */
    Person insert(PersonVo personVo);

    /**
     * 修改数据
     *
     * @param personVo 实例对象
     * @return 实例对象
     */
    Person update(PersonVo personVo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     *
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
}