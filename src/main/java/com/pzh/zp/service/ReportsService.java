package com.pzh.zp.service;

import com.pzh.zp.entity.Reports;

import java.util.List;

public interface ReportsService {
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
    List queryAll();

    int insert(Reports reports);

    int update(Reports reports);

    int delete(int id);
}
