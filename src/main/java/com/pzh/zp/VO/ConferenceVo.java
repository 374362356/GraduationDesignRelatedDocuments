package com.pzh.zp.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceVo {
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

}
