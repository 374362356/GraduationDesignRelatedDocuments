package com.pzh.zp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 到会时间表(Reach)实体类
 *
 * @author makejava
 * @since 2021-01-25 16:45:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reach implements Serializable {
    private static final long serialVersionUID = -49091086868642669L;

    private Integer id;
    /**
     * 到会时间
     */
    private Date reachTime;
    /**
     * 到会确认
     */
    private Object confirm;
    /**
     * 参会人id
     */
    private Integer personId;
    /**
     * 接待人员id
     */
    private Integer staffId;


}