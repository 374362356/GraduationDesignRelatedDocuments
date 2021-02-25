/*********************************************************************************
 * Copyright (c)2021 CEC Health
 * FILE: CorsConfig
 * 版本      DATE             BY               REMARKS
 * ----  -----------  ---------------  ------------------------------------------
 * 1.0   2021-02-02        赵鹏
 ********************************************************************************/
package com.pzh.zp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//跨域
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            //spring-boot-starter-parent版本必须在2.4.0以上，不然会爆红
                .allowedOriginPatterns("*")
                .allowedMethods("GET","POST","PUT","HEAD","DELETE","OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
