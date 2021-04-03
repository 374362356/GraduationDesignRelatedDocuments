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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component("AccessService")
public class AccessService {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication){

        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails){
            CustomUserDetails userDetails=(CustomUserDetails) principal;
            boolean flag=false;
            List<Permission> permissons = userDetails.getPermissons();
            for (Permission permisson : permissons) {
                if(request.getRequestURI().equals(permisson.getUrl())){
                    flag= true;
                    break;
                }
            }
            return flag;
        }
        return false;
    }
}
