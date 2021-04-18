package com.pzh.zp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 工作人员表(Staff)实体类
 *
 * @author makejava
 * @since 2021-04-14 19:00:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff implements Serializable {
    private static final long serialVersionUID = -31610463703012308L;

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
     * 会议编号
     */
    private Integer conferenceId;
    /**
     * 是否请假
     */
    private Integer leave;
}