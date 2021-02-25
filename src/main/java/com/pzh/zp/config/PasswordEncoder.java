package com.pzh.zp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//@Component
public class PasswordEncoder {

    @Bean
    public PasswordEncoder passWordEncoder(){
        return new PasswordEncoder();
    }
}
