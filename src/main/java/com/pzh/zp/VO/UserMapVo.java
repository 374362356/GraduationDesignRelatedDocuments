package com.pzh.zp.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMapVo {
    private Integer id;
    /**
     * 用户名
     */
    private String nickName;
    /**
     * 账号
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    /**
     * 性别
     */
    private Object gender;
    /**
     * 电话
     */
    private String phone;

    /**
     * 状态 1 解冻  0 冻结
     */
    private Integer status;

    private String description;
}
