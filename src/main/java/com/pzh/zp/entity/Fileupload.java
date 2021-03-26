package com.pzh.zp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 文件上传下载表(Fileupload)实体类
 *
 * @author makejava
 * @since 2021-03-24 23:47:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fileupload implements Serializable {
    private static final long serialVersionUID = -78181860335360620L;

    private Integer id;

    private String fileName;

    private String fileUrl;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

}
