package com.pzh.zp.controller;


import com.pzh.zp.entity.Suggestion;
import com.pzh.zp.service.SuggestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;



/**
 * 建议表(Suggestion)表控制层
 *
 * @author makejava
 * @since 2021-01-25 16:45:34
 */
@RestController
@RequestMapping("suggestion")
public class SuggestionController {
    /**
     * 服务对象
     */
    @Resource
    private SuggestionService suggestionService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Suggestion selectOne(Integer id) {
        return this.suggestionService.queryById(id);
    }



}