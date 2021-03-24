package com.pzh.zp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 参会人员表(Person)实体类
 *
 * @author makejava
 * @since 2021-01-25 16:45:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {
    private static final long serialVersionUID = 770480769859064856L;

    public Person(String pName, Integer age, Object gender, String email, String phone, String title) {
        this.pName = pName;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.title = title;
    }

    private Integer id;
    /**
     * 姓名
     */
    private String pName;
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

}