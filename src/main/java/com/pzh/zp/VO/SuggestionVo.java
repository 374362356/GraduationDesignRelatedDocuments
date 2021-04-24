package com.pzh.zp.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionVo {
    private Integer id;
    /**
     * 时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date suggTime;

    /**
     * 建议主题
     */
    private String suggestionTitle;
    /**
     * 建议内容
     */
    private Object content;
    /**
     * 建议人id
     */
    private Integer personId;
    /**
     * 受理人员姓名
     */
    private String conferName;
}
