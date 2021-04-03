package com.pzh.zp.security.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.utils.JWTUtil;
import com.pzh.zp.utils.ResponseUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutHandlerImpl implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("token");
        if(token!=null){
            Claims claim = JWTUtil.getClaimByToken(token);
            String  username = claim.get("userName").toString();
            System.out.println(username);
        }
        try {
            new ObjectMapper().writeValue(response.getOutputStream(), ResultVo.success("退出成功"));
        } catch (IOException e) {
            ResponseUtil.out(response,ResultVo.fail("退出失败"));
        }
    }
}
