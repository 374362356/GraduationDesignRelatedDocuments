package com.pzh.zp.security;

import com.pzh.zp.dao.UserDao;
import com.pzh.zp.entity.Permission;
import com.pzh.zp.entity.User;
import com.pzh.zp.enumState.UserEnum;
import com.pzh.zp.service.PermissionService;
import com.pzh.zp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;
    @Autowired
    PermissionService permissionService;
    @Resource
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("===========>name："+username);
        User user = userDao.queryByuserName(username);
        if(user==null){
            throw new InternalAuthenticationServiceException("用户不存在");
        }

        CustomUserDetails userDetails = new CustomUserDetails();
        if (user.getStatus().equals(UserEnum.FROZEN.getKey())){
            userDetails.setIsEnabled(false);
            return userDetails;
        }
        //通过用户的id 查找权限(就是数据中的路径)
        List<Permission> permissons = permissionService.findByUserId(user.getId());
        // 设值 然后返回
        userDetails.setUsername(user.getUserName());
        userDetails.setPassword(user.getPassword());
        userDetails.setPermissons(permissons);
        //System.out.println("====loadUserByUsername==="+userDetails);
        return userDetails;
    }
}