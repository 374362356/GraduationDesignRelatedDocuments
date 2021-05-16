package com.pzh.zp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto implements Serializable {
    String contentTitle;
    String content;
    String timeSelect;
    Integer publishId;
}
