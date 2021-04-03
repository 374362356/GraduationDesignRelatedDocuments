package com.pzh.zp.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsVo {
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
     * 发布人名
     */
    private String publishName;
}
