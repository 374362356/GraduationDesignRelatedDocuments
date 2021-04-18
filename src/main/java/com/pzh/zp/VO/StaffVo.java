package com.pzh.zp.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffVo {
    private Integer id;
    /**
     * 姓名
     */
    private String sName;
    /**
     * 性别
     */
    private Object gender;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 职务
     */
    private String position;
    /**
     * 会议名
     */
    private String conferenceName;

    private Integer leave;
}
