package com.pzh.zp.security;

import com.pzh.zp.VO.UserVo;
import com.pzh.zp.entity.Permission;
import com.pzh.zp.entity.User;
import com.pzh.zp.service.PermissionService;
import com.pzh.zp.service.UserService;
import com.pzh.zp.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Component
public class CustomAuthorizeFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if("OPTIONS".equals(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_OK);
        }

        //请求头是否有 token
        String token = request.getHeader("token");
        if(token!=null&&!"undefined".equals(token)&&"".equals(token)) {
            //通过jwt工具类 返回用户名
            Claims claim = JWTUtil.getClaimByToken(token);

            Object userName = claim.get("userName");
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userService.queryByuserName(userName.toString());
                UserVo userVo = new UserVo(user.getId(), user.getUserName(), user.getUserName(), user.getStatus());
                List<Permission> permissons = permissionService.findByUserId(userVo.getId());

                Collection<GrantedAuthority> authorities = new ArrayList<>();
                for (Permission permisson : permissons) {
                    if (StringUtils.isEmpty(permisson.getUrl())) {
                        continue;
                    }
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permisson.getUrl());
                    authorities.add(authority);
                }
                //加载用户的详细信息 生成 UsernamePasswordAuthenticationToken 令牌
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName.toString());
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    //设置为已登录
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            System.out.println("=====SecurityContextHolder====>"+SecurityContextHolder.getContext().getAuthentication());
        }
        filterChain.doFilter(request,response);
    }
}
