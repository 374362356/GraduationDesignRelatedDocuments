package com.pzh.zp.service;

import com.pzh.zp.VO.UserMapVo;
import com.pzh.zp.entity.User;

import java.text.ParseException;
import java.util.List;



/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2021-01-25 16:45:35
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user) throws ParseException;

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User update(User user) throws ParseException;

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
     * @param nickName
     * @param phone
     * @return 对象列表
     */
    List<User> fuzzySearch(String nickName,String phone);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    List<UserMapVo> queryAll(User user);

    /**
     *
     * @param userName
     * @return
     */
    User queryByuserName(String userName);

    int userStateChange(Integer id);
}