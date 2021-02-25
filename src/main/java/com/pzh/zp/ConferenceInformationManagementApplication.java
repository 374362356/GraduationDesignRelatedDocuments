/*********************************************************************************
 * Copyright (c)2021 CEC Health
 * FILE: ConferenceInformationManagementApplication
 * 版本      DATE             BY               REMARKS
 * ----  -----------  ---------------  ------------------------------------------
 * 1.0   2021-01-28        赵鹏
 ********************************************************************************/
package com.pzh.zp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pzh.zp.dao")
public class ConferenceInformationManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConferenceInformationManagementApplication.class, args);
    }

}
