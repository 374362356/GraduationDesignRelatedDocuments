package com.pzh.zp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 建议表(Suggestion)实体类
 *
 * @author makejava
 * @since 2021-01-25 16:45:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Suggestion implements Serializable {
    private static final long serialVersionUID = -55968467167468340L;

    private Integer id;
    /**
     * 时间
     */
    private Date suggTime;
    /**
     * 建议内容
     */
    private Object content;
    /**
     * 建议人id
     */
    private Integer personId;
    /**
     * 受理人员id
     */
    private Integer staffId;

}