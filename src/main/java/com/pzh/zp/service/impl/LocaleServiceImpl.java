package com.pzh.zp.service.impl;

import com.pzh.zp.entity.Locale;
import com.pzh.zp.dao.LocaleDao;
import com.pzh.zp.service.LocaleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Locale)表服务实现类
 *
 * @author makejava
 * @since 2021-04-22 18:02:19
 */
@Service("localeService")
public class LocaleServiceImpl implements LocaleService {
    @Resource
    private LocaleDao localeDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Locale queryById(Integer id) {
        return this.localeDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Locale> queryAllByLimit(int offset, int limit) {
        return this.localeDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param locale 实例对象
     * @return 实例对象
     */
    @Override
    public Locale insert(Locale locale) {
        this.localeDao.insert(locale);
        return locale;
    }

    /**
     * 修改数据
     *
     * @param locale 实例对象
     * @return 实例对象
     */
    @Override
    public Locale update(Locale locale) {
        this.localeDao.update(locale);
        return this.queryById(locale.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.localeDao.deleteById(id) > 0;
    }

    @Override
    public List<String> findAllPlaces() {
        return localeDao.findAllPlaces();
    }
}
