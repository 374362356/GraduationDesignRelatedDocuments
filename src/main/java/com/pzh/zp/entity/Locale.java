package com.pzh.zp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Locale)实体类
 *
 * @author makejava
 * @since 2021-04-22 18:02:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Locale implements Serializable {
    private static final long serialVersionUID = -99614676734603262L;

    private Integer id;
    /**
     * 会议地点
     */
    private String places;
    /**
     * 容纳数量
     */
    private Integer quantity;
    /**
     * 地点状态
     */
    private Integer employ;
    /**
     * 会议id
     */
    private Integer conferId;

}
