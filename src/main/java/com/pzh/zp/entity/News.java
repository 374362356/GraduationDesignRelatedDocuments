package com.pzh.zp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告表(News)实体类
 *
 * @author makejava
 * @since 2021-01-25 16:45:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News implements Serializable {
    private static final long serialVersionUID = -20161982728838032L;

    private Integer id;
    /**
     * 发布时间
     */
    private Date newTime;
    /**
     * 发布标题
     */
    private String contentTitle;
    /**
     * 发布内容
     */
    private String content;
    /**
     * 发布人员id
     */
    private Integer staffId;

}