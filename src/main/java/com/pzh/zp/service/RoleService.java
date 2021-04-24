package com.pzh.zp.service;

import com.pzh.zp.entity.Role;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;



/**
 * 角色表(Role)表服务接口
 *
 * @author makejava
 * @since 2021-01-25 16:45:31
 */
public interface RoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Role queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Role> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    Role insert(Role role) throws ParseException;

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    Role update(Role role);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<Role> queryAll(Role role);

    /**
     *
     * @param request
     * @param role_id
     * @param user_id
     * @return
     */
    int updateUserRole(HttpServletRequest request,String role_id, String user_id);

    String findRole(String userId);
}