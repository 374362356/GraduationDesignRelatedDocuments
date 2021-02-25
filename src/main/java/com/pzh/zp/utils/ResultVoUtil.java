package com.pzh.zp.utils;

import com.pzh.zp.VO.ResultVo;

public final class ResultVoUtil {

    public static ResultVo success(Object data){
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(200);
        resultVo.setMsg("成功");
        resultVo.setData(data);
        return resultVo;
    }

    public static ResultVo fail(String error){
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(400);
        resultVo.setMsg(error);
        resultVo.setData(null);
        return resultVo;
    }
}
