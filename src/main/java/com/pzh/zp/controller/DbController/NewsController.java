package com.pzh.zp.controller.DbController;

import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.entity.News;
import com.pzh.zp.service.NewsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
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
     * 数据库字段staff_id变更，标识是哪个管理员发布的公告
     */

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

    @PutMapping("/news_update/{newsId}")
    public ResultVo updateNews(@PathVariable Integer newsId,@RequestBody News news){
        news.setId(newsId);
        return ResultVo.success(newsService.update(news));
    }

    @DeleteMapping("/news_delete")
    public ResultVo deleteNews(@RequestParam Integer id){
        return ResultVo.success(newsService.deleteById(id));
    }

    @GetMapping("/findPublishName")
    public ResultVo findPublishName(@RequestParam Integer id, @RequestParam Integer stateId){
        return ResultVo.success(newsService.findPublishName(id, stateId));
    }

    @PostMapping("/news_insert")
    public ResultVo InsertNews(HttpServletRequest request,@RequestBody News news) throws ParseException {
        News insert = newsService.insert(request, news);
        if (insert!=null){
            return ResultVo.success(insert);
        }
        return ResultVo.fail("标题不能为空");
    }

    @PutMapping("/timing_insert")
    public ResultVo InsertTiming(@RequestParam String contentTitle,@RequestParam String content,@RequestParam String timeSelect,HttpServletRequest request) throws ParseException {
        System.out.println("----------------------------"+timeSelect);
        String timing = newsService.InsertTiming(request, new News(contentTitle,content), timeSelect);
        if (timing!=null) {
            return ResultVo.success(timing);
        }
        return ResultVo.fail("定时任务失败");
    }
}