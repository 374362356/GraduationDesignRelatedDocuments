package com.pzh.zp.service.impl;

import com.pzh.zp.dao.ConferenceDao;
import com.pzh.zp.entity.Conference;
import com.pzh.zp.service.ConferenceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


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
    public List<Conference> queryAll(Conference conference) {
        return conferenceDao.queryAll(conference);
    }

    @Override
    public List<String> queryAllName() {
        return conferenceDao.queryAllName();
    }
}