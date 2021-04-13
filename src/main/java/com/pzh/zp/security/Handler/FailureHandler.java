package com.pzh.zp.security.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class FailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        if(exception instanceof InternalAuthenticationServiceException){
            ResponseUtil.out(response, ResultVo.fail("用户不存在"));
        }else
        if(exception instanceof BadCredentialsException){
            ResponseUtil.out(response, ResultVo.fail(exception.getMessage()));
        }else
        if(exception instanceof UsernameNotFoundException){
            ResponseUtil.out(response, ResultVo.fail("用户不存在"));
        }else
        if(exception instanceof DisabledException){
            ResponseUtil.out(response, ResultVo.fail("用户被禁用"));
        }else
        if(exception instanceof LockedException){
            ResponseUtil.out(response, ResultVo.fail("账户锁定"));
        }else
        if(exception instanceof AccountExpiredException){
            ResponseUtil.out(response, ResultVo.fail("账户过期"));
        }else
        if(exception instanceof CredentialsExpiredException){
            ResponseUtil.out(response,ResultVo.fail("证书过期"));
        }
        else {
            ResponseUtil.out(response, ResultVo.fail("其他错误"));
        }
    }
}
