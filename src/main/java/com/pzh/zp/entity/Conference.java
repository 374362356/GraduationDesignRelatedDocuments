package com.pzh.zp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 会议表(Conference)实体类
 *
 * @author makejava
 * @since 2021-01-25 16:45:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conference implements Serializable {
    private static final long serialVersionUID = 729244824317594017L;

    private Integer id;
    /**
     * 会议名称
     */
    private String name;
    /**
     * 开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date endTime;
    /**
     * 举办地点
     */
    private String place;
    /**
     * 举办方
     */
    private String host;

    /**
     * 会议简要
     */
    private String desc;

    /**
     * 状态
     */
    private Integer ongoing;
}