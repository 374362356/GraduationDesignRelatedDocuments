/*********************************************************************************
 * Copyright (c)2021 CEC Health
 * FILE: loginController
 * 版本      DATE             BY               REMARKS
 * ----  -----------  ---------------  ------------------------------------------
 * 1.0   2021-02-05        赵鹏
 ********************************************************************************/
package com.pzh.zp.controller;

import com.alibaba.druid.util.StringUtils;
import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.VO.UserVo;
import com.pzh.zp.entity.User;
import com.pzh.zp.service.UserService;
import com.pzh.zp.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
public class loginController {

    //登录密码加密
    //private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResultVo login(@RequestBody User user, HttpServletResponse response){
        System.out.println("==========>"+user.getUserName());
        User queryUser = userService.queryByuserName(user.getUserName());
        //密码匹配
        boolean matches = StringUtils.equals(user.getPassword(), queryUser.getPassword());
        //
        UserVo userVo = new UserVo(queryUser.getId(),queryUser.getUserName(),queryUser.getNickName(),queryUser.getStatus());
        String token = JWTUtil.generateToken(userVo);
        System.out.println("========Token=========>"+token);
        HashMap<String, Object> hs = new HashMap<>();
        hs.put("user",user);
        hs.put("token",token);
        if (matches){
            response.setHeader("token",token);
            response.addHeader("Access-Control-Expose-Headers","token");
            return ResultVo.success(hs);
        }
        return ResultVo.fail("登录失败");
    }
}