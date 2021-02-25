/*********************************************************************************
 * Copyright (c)2021 CEC Health
 * FILE: ResultVo
 * 版本      DATE             BY               REMARKS
 * ----  -----------  ---------------  ------------------------------------------
 * 1.0   2021-01-28        赵鹏
 ********************************************************************************/
package com.pzh.zp.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo<T> {
    private Integer code;
    private String msg;
    private T data;

    public static ResultVo success(Integer code,String msg,Object data){
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        resultVo.setData(data);
        return resultVo;
    }
    public static ResultVo success(Object data){return success(200,"成功",data);}

    public static ResultVo fail(Integer code,String msg,Object data){
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        resultVo.setData(data);
        return resultVo;
    }
    public static ResultVo fail(String msg){return fail(400,"失败",null);}
}
