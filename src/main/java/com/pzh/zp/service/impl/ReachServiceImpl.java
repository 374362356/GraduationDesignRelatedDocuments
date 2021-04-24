package com.pzh.zp.service.impl;

import com.pzh.zp.VO.ReachVo;
import com.pzh.zp.dao.*;
import com.pzh.zp.entity.Reach;
import com.pzh.zp.entity.Role;
import com.pzh.zp.enumState.UserEnum;
import com.pzh.zp.service.ReachService;
import com.pzh.zp.utils.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * 到会时间表(Reach)表服务实现类
 *
 * @author makejava
 * @since 2021-04-10 19:32:43
 */
@Service("reachService")
public class ReachServiceImpl implements ReachService {
    @Resource
    private ReachDao reachDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private UserDao userDao;

    @Resource
    private ConferenceDao conferenceDao;

    @Resource
    private StaffDao staffDao;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public List<ReachVo> queryByUserId(Integer id) {
        return this.reachDao.queryByUserId(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Reach> queryAllByLimit(int offset, int limit) {
        return this.reachDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param reach 实例对象
     * @return 实例对象
     */
    @Override
    public Reach insert(Reach reach) {
        this.reachDao.insert(reach);
        return reach;
    }

    /**
     * 修改数据
     *
     * @param reach 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public ReachVo update(ReachVo reach) {
        Integer userId = userDao.queryByuserName(reach.getUserName()).getId();
        Integer staffId = staffDao.queryStaffByName(reach.getStaffName()).getId();
        Integer conferId = conferenceDao.queryIdByName(reach.getConferenceName());
        reach.setReachTime(new Date());
        if(!StringUtils.isEmpty(userId.toString()) && !StringUtils.isEmpty(staffId.toString()) && !StringUtils.isEmpty(conferId.toString())) {
            if (reach.getConfirm().equals(UserEnum.CHECKIN.getKey())) {
                reach.setConfirm(UserEnum.UNCHECKIN.getKey());
                this.reachDao.update(new Reach(reach.getId(), reach.getReachTime(), reach.getConfirm(), userId, staffId, conferId));
                return reach;
            } else if (reach.getConfirm().equals(UserEnum.UNCHECKIN.getKey())) {
                reach.setConfirm(UserEnum.CHECKIN.getKey());
                this.reachDao.update(new Reach(reach.getId(), reach.getReachTime(), reach.getConfirm(), userId, staffId, conferId));
                return reach;
            }
        }
        return null;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.reachDao.deleteById(id) > 0;
    }

    /**
     * 管理员界面需要展示reach表字段加参会人员名，详细展示签到情况
     *  普通用户就显示自己需要参加的会议
     * @param reach
     * @return
     */
    @Override
    @Transactional
    public List<ReachVo> findAll(Reach reach, HttpServletRequest request) {
        List<ReachVo> reaches = reachDao.queryAll(reach);
        //
        String token = request.getHeader("token");
        Integer id = (Integer)JWTUtil.getClaimByToken(token).get("id");
        int roleId = roleDao.findRoleIdByUserId(id);
        Role role = roleDao.queryById(roleId);
        if (!StringUtils.isBlank(id.toString()) && UserEnum.user.getValue().equals(role.getRName())){
             return this.queryByUserId(id);
        }else {
            return reaches;
        }
    }

    @Override
    public ReachVo queryById(Integer id) {
        return reachDao.queryById(id);
    }
}