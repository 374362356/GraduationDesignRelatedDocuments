package com.pzh.zp;

import com.pzh.zp.dao.NewsDao;
import com.pzh.zp.dao.ReachDao;
import com.pzh.zp.dao.UserDao;
import com.pzh.zp.entity.News;
import com.pzh.zp.entity.Reach;
import com.pzh.zp.entity.Reports;
import com.pzh.zp.dao.ReportsDao;
import com.pzh.zp.entity.User;
import com.pzh.zp.enumState.UserEnum;
import com.pzh.zp.utils.JWTUtil;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class ConferenceInformationManagementApplicationTests {

    @Test
    void contextLoads() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormat.format(new Date()));
    }

    @Test
    void test() throws ParseException {
        String t= "2021-03-18 16:03:43：";
        var s = t.substring(0, t.lastIndexOf("："));

        System.out.println(System.currentTimeMillis());
    }

    @Resource
    private ReportsDao reportsDao;
    @Resource
    private UserDao userDao;
    @Resource
    private NewsDao newsDao;
    @Resource
    ReachDao reachDao;
    @Test
    void findAll(){
        List<Reports> reports = reportsDao.queryAll();
        List<String> name = reports.stream().map(r -> r.getName()).collect(Collectors.toList());
        List<String> value = reports.stream().map(Reports::getValue).collect(Collectors.toList());
        Map<String, String> map = reports.stream().collect(Collectors.toMap(Reports::getName, Reports::getValue));

        List<String> valueList = new ArrayList<String>(Collections.singleton(map.toString()));
        //System.out.println(valueList);
        String list = new String();
        for (String s:valueList){
            list+=s.replace("=",":");
        }
        //System.out.println(list);
        char[] chars = list.toCharArray();
    }

    @Test
    void test07(){
        User u = new User(9,"111","test111","123",new Date(),1,"15176253848",0);
        System.out.println(userDao.update(u));
    }

    @Test
    void bcrypt(){
        String password = "111";
        //密码加密
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        //加密
        String newPassword = passwordEncoder.encode(password);
        System.out.println("加密密码为："+newPassword);
    }

    @Test
    void toMap(){
        List<News> news = newsDao.queryAll(null);
        Map<Integer, Integer> publishId = news.stream().collect(Collectors.toMap(News::getId, News::getPublishId));
        System.out.println(publishId.get(10));
    }

    @Test
    void test1(){
        System.out.println(JWTUtil.getClaimByToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6cCIsImlkIjo2LCJ1c2VybmFtZSI6Im1tIiwibmlja05hbWUiOiJhZG1pbiIsInN0YXR1cyI6MSwiaWF0IjoxNjE3ODEyMTg5LCJleHAiOjE2MTg0MTY5ODl9.eR58BUD8EqiIJJBQ0JaHURXoYeQuotvCI9eBsDKWHBU"));
    }

    @Test
    void z(){
        //System.out.println(reachDao.queryAll(new Reach()));
        System.out.println(reachDao.queryByUserId(1));
    }
}
