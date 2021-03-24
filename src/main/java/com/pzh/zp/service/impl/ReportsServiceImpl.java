package com.pzh.zp.service.impl;

import com.pzh.zp.VO.ReportVo;
import com.pzh.zp.entity.Reports;
import com.pzh.zp.dao.*;
import com.pzh.zp.service.ReportsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("ReportsService")
public class ReportsServiceImpl implements ReportsService {

    @Resource
    private ReportsDao reportsDao;
    @Resource
    private ConferenceDao conferenceDao;
    @Resource
    private UserDao userDao;
    @Resource
    private NewsDao newsDao;
    @Resource
    private SuggestionDao suggestionDao;
    @Resource
    private StaffDao staffDao;

    @Override
    public Reports queryById(int id) {
        return reportsDao.queryById(id);
    }

    @Override
    public List queryAll() {
        List<Reports> reports = reportsDao.queryAll();

        List<String> name = reports.stream().map(r -> r.getName()).collect(Collectors.toList());
        List<String> value = reports.stream().map(Reports::getValue).collect(Collectors.toList());
        ArrayList<Object> list = new ArrayList<>();
        for (Reports r: reports) {
            list.add(new ReportVo(r.getName(),r.getValue()));
        }
        return  list;
    }

    @Override
    public int insert(Reports reports) {
        if(reports!=null) {
            reportsDao.insert(reports);
        }
        return 0;
    }

    @Override
    public int update(Reports reports) {
        String confer = conferenceDao.counting().toString();
        String user = userDao.counting().toString();
        String news = newsDao.counting().toString();
        String sugg = suggestionDao.counting().toString();
        String staff = staffDao.counting().toString();
        if (reports.getName()=="开展会议"){
            reports.setValue(confer);
        }else if(reports.getName()=="公告发布"){
            reports.setValue(news);
        }else if(reports.getName()=="用户人数"){
            reports.setValue(user);
        }else if(reports.getName()=="建议条数"){
            reports.setValue(sugg);
        }else if(reports.getName()=="工作人员"){
            reports.setValue(staff);
        }
        return reportsDao.update(new Reports(reports.getId(),reports.getName(), reports.getValue()));

    }

    @Override
    public int delete(int id) {
        if(id>0) {
            reportsDao.deleteById(id);
        }
        return 0;
    }
}
