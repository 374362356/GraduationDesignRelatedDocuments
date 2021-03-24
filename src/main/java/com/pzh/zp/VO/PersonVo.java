package com.pzh.zp.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonVo {

    private Integer id;
    /**
     * 姓名
     */
    private String pname;
    /**
     * 年龄
     */
    private Integer age;
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
     * 职称
     */
    private String title;

    /**
     * 会议名  (多于数据库一个字段)
     */
    private  String name;
}
