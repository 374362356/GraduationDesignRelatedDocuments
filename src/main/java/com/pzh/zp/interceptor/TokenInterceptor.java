package com.pzh.zp.interceptor;

import com.pzh.zp.utils.JWTUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //跨域请求会首先发一个option请求，直接返回正常状态并通过拦截器
        if("OPTIONS".equals(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("token");
        //System.out.println("==========拦截器========>"+token);
        //判断是否过期
        if(token==null)
        {//return false;这里应该有一个统一异常处理
            //throw new RuntimeException("token不存在");
            System.out.println("==========>token不在");
        }
        if (token!=null) {
            Optional.ofNullable(token)
                    .map(n -> {
                        try {
                            return JWTUtil.getClaimByToken(n);
                        } catch (Exception e) {
                            throw new RuntimeException("token信息不正确");
                        }
                    });
            System.out.println("通过拦截器~");
            return true;
        }

        //token验证失败
        response.setContentType("application/json; charset=utf-8");
        try {
            JSONObject json=new JSONObject();
            json.put("msg","token verify fail");
            json.put("code","500");
            response.getWriter().append(json.toString());
            System.out.println("认证失败，未通过拦截器");
        } catch (Exception e) {
            return false;
        }

        return false;
    }
}
