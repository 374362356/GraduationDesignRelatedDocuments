package com.pzh.zp.controller;

import com.pzh.zp.entity.News;
import com.pzh.zp.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 公告表(News)表控制层
 *
 * @author makejava
 * @since 2021-01-25 16:45:28
 */
@RestController
@RequestMapping("news")
public class NewsController {
    /**
     * 服务对象
     */
    @Resource
    private NewsService newsService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public News selectOne(Integer id) {
        return this.newsService.queryById(id);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("/findAll")
    public List<News> findAll(){
        return  newsService.queryAll(null);
    }

    /**
     * 模糊查询
     *
     * @param newTime
     * @param contentTitle
     * @return
     */
    @RequestMapping("/fuzzy")
    public List<News> fuzzySearch(@RequestParam(required = false) String newTime, @RequestParam(required = false) String contentTitle){
        return newsService.fuzzySearch(newTime, contentTitle);
    }

}