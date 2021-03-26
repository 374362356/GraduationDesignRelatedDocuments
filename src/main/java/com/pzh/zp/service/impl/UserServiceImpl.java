package com.pzh.zp.service.impl;

import com.pzh.zp.dao.UserDao;
import com.pzh.zp.entity.User;
import com.pzh.zp.enumState.UserEnum;
import com.pzh.zp.service.UserService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2021-01-25 16:45:35
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(new Date());
        Date date = dateFormat.parse(format);
        //插入时间
        user.setCreateTime(date);
        user.setStatus(UserEnum.user.toInt());      //enum
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) throws ParseException {
        User oldUser = userDao.queryById(user.getId());
        if (!oldUser.getStatus().equals(UserEnum.user.toInt())){
            User newUser = new User(user.getId(), user.getNickName(), oldUser.getUserName(), user.getPassword(), oldUser.getCreateTime(),
                    user.getGender(), user.getPhone(), oldUser.getStatus());
            userDao.update(newUser);
            return this.queryById(newUser.getId());
        }

        user.setCreateTime(user.getCreateTime());
        user.setStatus(UserEnum.user.toInt());

        this.userDao.update(user);
        return this.queryById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userDao.deleteById(id) > 0;
    }

    /**
     *
     * @param nickName
     * @param phone
     * @return 对象列表
     */
    @Override
    public List<User> fuzzySearch(String nickName, String phone) {
        return userDao.fuzzySearch(nickName, phone);
    }


    @Override
    public List<User> queryAll(User user) {
        return userDao.queryAll(user);
    }

    @Override
    public User queryByuserName(String userName) {
        return userDao.queryByuserName(userName);
    }
}