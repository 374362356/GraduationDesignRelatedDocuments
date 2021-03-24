package com.pzh.zp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reports {
    private Integer id;
    private String name;
    private String value;

    public Reports(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
