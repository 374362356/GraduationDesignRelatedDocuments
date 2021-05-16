package com.pzh.zp.service.impl;

import com.pzh.zp.dao.RoleDao;
import com.pzh.zp.entity.Role;
import com.pzh.zp.enumState.UserEnum;
import com.pzh.zp.service.RoleService;
import com.pzh.zp.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



/**
 * 角色表(Role)表服务实现类
 *
 * @author makejava
 * @since 2021-01-25 16:45:31
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Role queryById(Integer id) {
        return this.roleDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Role> queryAllByLimit(int offset, int limit) {
        return this.roleDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Role insert(Role role) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(new Date());
        Date date = dateFormat.parse(format);
        role.setCreateTime(date);
        role.setStatus(UserEnum.UNFROZEN.getKey());
        this.roleDao.insert(role);
        return role;
    }

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Role update(Role role) {
        Role byId = roleDao.queryById(role.getId());
        role.setCreateTime(byId.getCreateTime());
        role.setStatus(UserEnum.UNFROZEN.getKey());
        this.roleDao.update(role);
        return this.queryById(role.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.roleDao.deleteById(id) > 0;
    }

    @Override
    public List<Role> queryAll(Role role) {
        return roleDao.queryAll(role);
    }

    @Override
    @Transactional
    public int updateUserRole(HttpServletRequest request,String role_id, String user_id) {
        Claims token = JWTUtil.getClaimByToken(request.getHeader("token"));
        System.out.println("======>>>>>>>"+role_id+"========"+user_id);
        //String NewId = roleDao.findRoleNameById(role_id);
        Integer id = (Integer) token.get("id");
        int OldId = roleDao.findRoleIdByUserId(id);
        Role role = roleDao.queryById(OldId);
        String rId = null;
        if (role.getRName().equals(UserEnum.root.getValue())) {
            if (UserEnum.user.getValue().equals(role_id)) {
                rId = UserEnum.user.getKey().toString();
            } else
            if (UserEnum.root.getValue().equals(role_id)) {
                return -1;
            } else
            if (UserEnum.admin.getValue().equals(role_id)) {
                rId = UserEnum.admin.getKey().toString();
            }

        }else if (role.getRName().equals(UserEnum.admin.getValue())){
            if (UserEnum.user.getValue().equals(role_id)) {
                rId = UserEnum.user.getKey().toString();
            } else
            if (UserEnum.root.getValue().equals(role_id)) {
                return -1;
            } else
            if (UserEnum.admin.getValue().equals(role_id)) {
                return  -1;
            }
        }
        if (rId!=null) {
            return roleDao.updateUserRole(rId, user_id);
        }
        return -1;
    }

    @Override
    public String findRole(String userId) {
        return roleDao.findRole(userId);
    }
}