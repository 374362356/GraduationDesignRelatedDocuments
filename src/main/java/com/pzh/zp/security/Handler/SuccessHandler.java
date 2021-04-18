package com.pzh.zp.security.Handler;

import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.VO.TokenVo;
import com.pzh.zp.VO.UserVo;
import com.pzh.zp.dao.RoleDao;
import com.pzh.zp.entity.Role;
import com.pzh.zp.entity.User;
import com.pzh.zp.security.CustomUserDetails;
import com.pzh.zp.service.UserService;
import com.pzh.zp.utils.JWTUtil;
import com.pzh.zp.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    UserService userService;
    
    @Resource
    RoleDao roleDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.queryByuserName(userDetails.getUsername());
        UserVo uservo = new UserVo(user.getId(), user.getUserName(),user.getNickName(),user.getStatus());

        int roleId = roleDao.findRoleIdByUserId(user.getId());
        String rName = roleDao.queryById(roleId).getRName();

        String token = JWTUtil.generateToken(uservo);
        //
        HashMap<String, Object> hs = new HashMap<>();
        hs.put("user",uservo);
        hs.put("role",rName);
        hs.put("token",token);
        if (token!=null){
            response.setHeader("token",token);
            response.addHeader("Access-Control-Expose-Headers","token");
            ResponseUtil.out(response,ResultVo.success(hs));
        }
        //ResponseUtil.out(response, ResultVo.success(new TokenVo(uservo,token)));
    }
}
