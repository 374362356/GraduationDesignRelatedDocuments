/*********************************************************************************
 * Copyright (c)2021 CEC Health
 * FILE: UserVo
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
public class UserVo {
    private Integer id;
    private String userName;
    private String nickName;
    private Integer status;
}
