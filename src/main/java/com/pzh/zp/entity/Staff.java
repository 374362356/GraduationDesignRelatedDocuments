package com.pzh.zp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 工作人员表(Staff)实体类
 *
 * @author makejava
 * @since 2021-01-25 16:45:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff implements Serializable {
    private static final long serialVersionUID = 148885769105539092L;

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

}