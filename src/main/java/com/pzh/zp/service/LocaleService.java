package com.pzh.zp.service;

import com.pzh.zp.entity.Locale;

import java.util.List;

/**
 * (Locale)表服务接口
 *
 * @author makejava
 * @since 2021-04-22 18:02:19
 */
public interface LocaleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Locale queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Locale> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param locale 实例对象
     * @return 实例对象
     */
    Locale insert(Locale locale);

    /**
     * 修改数据
     *
     * @param locale 实例对象
     * @return 实例对象
     */
    Locale update(Locale locale);

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
    List<String> findAllPlaces();

    List<Locale> queryAll(Locale locale);

    List<Locale> queryLocaleFuzzy(String locale);
}
