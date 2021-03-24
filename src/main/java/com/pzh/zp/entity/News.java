package com.pzh.zp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    public News(String contentTitle,String content){
        this.contentTitle=contentTitle;
        this.content=content;
    }
    private Integer id;
    /**
     * 发布时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
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
     * 发布人id
     */
    private Integer stateId;

}