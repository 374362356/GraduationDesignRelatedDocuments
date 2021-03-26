package com.pzh.zp;

import com.pzh.zp.entity.Reports;
import com.pzh.zp.dao.ReportsDao;
import com.pzh.zp.enumState.UserEnum;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        System.out.println(UserEnum.user.toInt());
    }
}
