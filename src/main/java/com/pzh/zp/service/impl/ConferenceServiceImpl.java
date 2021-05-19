package com.pzh.zp.service.impl;

import com.pzh.zp.VO.ConferUpdate;
import com.pzh.zp.VO.ConferenceVo;
import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.dao.ConferenceDao;
import com.pzh.zp.dao.LocaleDao;
import com.pzh.zp.dao.RoleDao;
import com.pzh.zp.entity.Conference;
import com.pzh.zp.entity.Locale;
import com.pzh.zp.entity.Role;
import com.pzh.zp.enumState.UserEnum;
import com.pzh.zp.service.ConferenceService;
import com.pzh.zp.utils.JWTUtil;
import com.pzh.zp.utils.Stamp2date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    
    @Resource
    private LocaleDao localeDao;
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
    @Transactional
    public Conference insert(Conference conference) {
        //会议名字唯一
        List<String> allName = conferenceDao.queryAllName();
        if (null!=conference ) {
            for (String name : allName) {
                if (name.equals(conference.getName())) {
                    return null;
                }
            }
//插入会议时应该改变locale表数据
            List<String> collect = conferenceDao.queryAll(null).stream().map(Conference::getPlace).collect(Collectors.toList());
            if (collect.contains(conference.getPlace())) {
                //根据地点找出已有的会议，判断会议时间是都重合
                List<Conference> conferences = conferenceDao.findConferenceByplace(conference.getPlace());
                for (Conference con : conferences) {
                    //选择相同地点判断时间是否冲突
                    if (conference.getStartTime().after(con.getEndTime()) || conference.getEndTime().before(con.getStartTime())) {
                        //conference.setOngoing(UserEnum.UNGOING.getKey());
                        conferenceDao.insert(conference);
                        return  conference;
                    } else {
                        return null;
                    }

                }
            }
            this.conferenceDao.insert(conference);
            return conference;
        }
        return null;
    }

    /**
     * 修改数据
     *
     * @param conference 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Conference update(Conference conference) {
        //更新时怎么判断名字重复
        if (conference!=null) {
            //更新地点表
            Locale locale = localeDao.queryByPlace(conference.getPlace());
            if(locale!=null) {
                locale.setConferId(conference.getId());
                localeDao.update(locale);
                this.conferenceDao.update(conference);
                return this.queryById(conference.getId());
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
    @Transactional
    public List<Conference> queryAll(Conference conference, HttpServletRequest request) {
        List<Conference> list = new ArrayList<>();
        //判断会议状态
        List<Date> dates = conferenceDao.queryAll(conference).stream().map(Conference::getEndTime).collect(Collectors.toList());

        String token = request.getHeader("token");
        Integer id = (Integer) JWTUtil.getClaimByToken(token).get("id");
        int roleId = roleDao.findRoleIdByUserId(id);
        Role role = roleDao.queryById(roleId);
        if (!StringUtils.isBlank(id.toString()) && UserEnum.user.getValue().equals(role.getRName())){
            //查中间表得到用户参加的会议
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

    @Override
    public List<ConferenceVo> findConferenceInfo() {
        return conferenceDao.findConferenceInfo();
    }

    /**
     *  更新会议状态
     * @return
     */
    @Override
    @Transactional
    public List<ConferUpdate> findConferStatus() {
        List<ConferUpdate> status = conferenceDao.findConferStatus();
        List<Date> endtime = status.stream().map(ConferUpdate::getEndTime).collect(Collectors.toList());
        List<Date> starttime = status.stream().map(ConferUpdate::getStartTime).collect(Collectors.toList());
        Map<Date, Integer> collect = status.stream().collect(Collectors.toMap(x -> x.getEndTime(), x -> x.getId()));
        for (Date date : endtime){
            int flag = Stamp2date.compareToCurrentTime(date);
            //如果结束时间存在相同会报错
            Integer id = collect.get(date);
            Conference conference = conferenceDao.queryById(id);
            if (flag==1){
                //未结束
                for (Date d : starttime){
                    //开始时间相同会报错
                    int sign = Stamp2date.compareToCurrentTime(d);
                    Integer conferId = collect.get(d);
                    if (sign==-1){
                        conference.setOngoing(UserEnum.ONGOING.getKey());
                    }else if (sign==1){
                        conference.setOngoing(UserEnum.UNGOING.getKey());
                    }else if (sign==0){
                        conference.setOngoing(UserEnum.ONGOING.getKey());
                    }
                }
            }
            if (flag==-1){
                conference.setOngoing(UserEnum.ENDGOING.getKey());
            }
            if (flag==0){
                conference.setOngoing(UserEnum.ONGOING.getKey());
            }
            conferenceDao.update(conference);
        }
        return status;
    }
}