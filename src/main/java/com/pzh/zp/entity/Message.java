package com.pzh.zp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 信息表(Message)实体类
 *
 * @author makejava
 * @since 2021-01-25 16:45:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private static final long serialVersionUID = 832829320269640575L;

    private Integer id;
    /**
     * 发送时间
     */
    private Date sendTime;
    /**
     * 发送内容
     */
    private String content;
    /**
     * 发送人员id
     */
    private Integer staffId;


}