package com.pzh.zp.dao;

import com.pzh.zp.entity.Reports;

import java.util.List;

public interface ReportsDao {

    /**
     *
     * @param id
     * @return
     */
    Reports queryById(int id);

    /**
     *
     * @return
     */
    List<Reports> queryAll();

    int insert(Reports reports);

    int update(Reports reports);

    int deleteById(int id);
}
