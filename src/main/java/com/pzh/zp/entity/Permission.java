package com.pzh.zp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限表(Permission)实体类
 *
 * @author makejava
 * @since 2021-01-25 16:45:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements Serializable {
    private static final long serialVersionUID = -33815737560628859L;

    private Integer id;
    /**
     * 权限名
     */
    private String pName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否启用
     */
    private Integer status;
    /**
     * URL
     */
    private String url;


}