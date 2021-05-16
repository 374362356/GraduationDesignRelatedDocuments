package com.pzh.zp.service;

import com.pzh.zp.VO.StaffVo;
import com.pzh.zp.entity.Staff;

import java.util.List;

/**
 * 工作人员表(Staff)表服务接口
 *
 * @author makejava
 * @since 2021-01-25 16:45:32
 */
public interface StaffService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Staff queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<StaffVo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param staff 实例对象
     * @return 实例对象
     */
    Staff insert(StaffVo staff);

    /**
     * 修改数据
     *
     * @param staffVo 实例对象
     * @return 实例对象
     */
    Staff update(StaffVo staffVo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 模糊查询
     *
     * @param sName
     * @param email
     * @param phone
     * @param position
     * @return 对象列表
     */
    List<Staff> fuzzySearch(String sName, String email, String phone, String position);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param staff 实例对象
     * @return 对象列表
     */
    List<StaffVo> queryAll(Staff staff);
}