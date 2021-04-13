/*********************************************************************************
 * Copyright (c)2021 CEC Health
 * FILE: AccessService
 * 版本      DATE             BY               REMARKS
 * ----  -----------  ---------------  ------------------------------------------
 * 1.0   2021-03-29        赵鹏
 ********************************************************************************/
package com.pzh.zp.security;

import com.pzh.zp.entity.Permission;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component("accessService")
public class AccessService {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication){

        Object principal = authentication.getPrincipal();
        if(principal!=null && principal instanceof UserDetails){
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            boolean flag=false;
            List<Permission> permissons = userDetails.getPermissons();
            for (Permission permisson : permissons) {
                System.out.println("========AccessService========>"+request.getRequestURI()+"=======>"+permisson.getUrl());
                if(request.getRequestURI().contains(permisson.getUrl())){
                    flag= true;
                    break;
                }
            }
            return flag;
        }
        return false;
    }
}
