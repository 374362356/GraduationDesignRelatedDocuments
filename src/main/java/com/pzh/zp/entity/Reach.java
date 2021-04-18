package com.pzh.zp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 到会时间表(Reach)实体类
 *
 * @author makejava
 * @since 2021-04-10 19:32:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reach implements Serializable {
    private static final long serialVersionUID = -56929158515188978L;

    private Integer id;
    /**
     * 到会时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date reachTime;
    /**
     * 到会确认
     */
    private Integer confirm;
    /**
     * 参会人id
     */
    private Integer userId;
    /**
     * 会议负责人员id
     */
    private Integer staffId;

    private Integer conferenceId;
}