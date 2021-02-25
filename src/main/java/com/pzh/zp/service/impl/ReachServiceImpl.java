package com.pzh.zp.service.impl;


import com.pzh.zp.dao.ReachDao;
import com.pzh.zp.entity.Reach;
import com.pzh.zp.service.ReachService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 到会时间表(Reach)表服务实现类
 *
 * @author makejava
 * @since 2021-01-25 16:45:30
 */
@Service("reachService")
public class ReachServiceImpl implements ReachService {
    @Resource
    private ReachDao reachDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Reach queryById(Integer id) {
        return this.reachDao.queryById(id);
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
    public Reach update(Reach reach) {
        this.reachDao.update(reach);
        return this.queryById(reach.getId());
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
}