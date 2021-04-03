package com.pzh.zp.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pzh.zp.VO.ResultVo;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {
    public static void out(HttpServletResponse response, ResultVo result){
        //传入自定义工具类R
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        //统一返回的JSON数据
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = null;
        try {
            writer=response.getWriter();
            //通过Response输出流 返回前端
            writer.write(mapper.writeValueAsString(result));
        } catch (IOException e) {
            throw  new RuntimeException(e.getMessage());
        }finally {
            assert writer != null;
            writer.close();
        }
    }
}
