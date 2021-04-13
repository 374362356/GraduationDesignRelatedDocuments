package com.pzh.zp.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReachVo {
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
     * 参会人名
     */
    private String userName;
    /**
     * 会议负责人员名
     */
    private String staffName;

    private String conferenceName;
}
