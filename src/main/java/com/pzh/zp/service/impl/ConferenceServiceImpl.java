package com.pzh.zp.service.impl;

import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.dao.ConferenceDao;
import com.pzh.zp.dao.RoleDao;
import com.pzh.zp.entity.Conference;
import com.pzh.zp.entity.Role;
import com.pzh.zp.enumState.UserEnum;
import com.pzh.zp.service.ConferenceService;
import com.pzh.zp.utils.JWTUtil;
import com.pzh.zp.utils.Stamp2date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 会议表(Conference)表服务实现类
 *
 * @author makejava
 * @since 2021-01-25 16:45:25
 */
@Service("conferenceService")
public class ConferenceServiceImpl implements ConferenceService {
    @Resource
    private ConferenceDao conferenceDao;

    @Resource
    private RoleDao roleDao;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Conference queryById(Integer id) {
        return this.conferenceDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Conference> queryAllByLimit(int offset, int limit) {
        return this.conferenceDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param conference 实例对象
     * @return 实例对象
     */
    @Override
    public Conference insert(Conference conference) {
        List<String> collect = conferenceDao.queryAll(null).stream().map(Conference::getPlace).collect(Collectors.toList());
        if(collect.contains(conference.getPlace())){
            //根据地点找出已有的会议，判断会议时间是都重合
            List<Conference> conferences = conferenceDao.findConferenceByplace(conference.getPlace());
            for (Conference con : conferences) {
                if (conference.getStartTime().after(con.getEndTime())||conference.getEndTime().before(con.getStartTime())){
                     conferenceDao.insert(conference);
                     break;
                }else {
                    return null;
                }

            }
        }
        this.conferenceDao.insert(conference);
        return conference;
    }

    /**
     * 修改数据
     *
     * @param conference 实例对象
     * @return 实例对象
     */
    @Override
    public Conference update(Conference conference) {
        this.conferenceDao.update(conference);
        return this.queryById(conference.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.conferenceDao.deleteById(id) > 0;
    }

    /**
     *
     * @param name
     * @param place
     * @param host
     * @return 对象列表
     */

    @Override
    public List<Conference> fuzzySearch(String name, String place, String host) {
        return conferenceDao.fuzzySearch(name, place, host);
    }

    /**
     *
     * @param conference 实例对象
     * @return
     */
    @Override
    public List<Conference> queryAll(Conference conference, HttpServletRequest request) {
        List<Conference> list = new ArrayList<>();
        //判断会议状态
        List<Date> dates = conferenceDao.queryAll(conference).stream().map(Conference::getEndTime).collect(Collectors.toList());

        String token = request.getHeader("token");
        Integer id = (Integer) JWTUtil.getClaimByToken(token).get("id");
        int roleId = roleDao.findRoleIdByUserId(id);
        Role role = roleDao.queryById(roleId);
        if (!StringUtils.isBlank(id.toString()) && UserEnum.user.getValue().equals(role.getRName())){
            list.add(this.queryById(id));
            return list;
        }else {
            return conferenceDao.queryAll(conference);
        }
    }

    @Override
    public List<String> queryAllName() {
        return conferenceDao.queryAllName();
    }
}