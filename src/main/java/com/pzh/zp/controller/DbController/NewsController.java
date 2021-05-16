package com.pzh.zp.controller.DbController;

import com.pzh.zp.DTO.NewsDto;
import com.pzh.zp.VO.NewsVo;
import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.entity.News;
import com.pzh.zp.rabbitmq.XdelaySender;
import com.pzh.zp.service.NewsService;
import com.pzh.zp.utils.JWTUtil;
import com.pzh.zp.utils.Stamp2date;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 定时发布公告存在bug
     * 判断公告只有管理员能发布
     */

    /**
     * 服务对象
     */
    @Resource
    private NewsService newsService;

    @Autowired
    XdelaySender xdelaySender;

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
    public List<NewsVo> findAll(){
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

    @PutMapping("/news_update")
    public ResultVo updateNews(@RequestBody NewsVo news){
        return ResultVo.success(newsService.update(news));
    }

    @DeleteMapping("/news_delete")
    public ResultVo deleteNews(@RequestParam Integer id){
        return ResultVo.success(newsService.deleteById(id));
    }

    @GetMapping("/findPublishName")
    public ResultVo findPublishName(@RequestParam Integer publishId){
        return ResultVo.success(newsService.findPublishName(publishId));
    }

    @PostMapping("/news_insert")
    public ResultVo InsertNews(HttpServletRequest request,@RequestBody News news) throws ParseException {
        News insert = newsService.insertNews(request, news);
        if (insert!=null){
            return ResultVo.success(insert);
        }
        return ResultVo.fail("标题不能为空");
    }

    @PutMapping("/timing_insert")
    public ResultVo InsertTiming(@RequestBody NewsDto newsDto,HttpServletRequest request) throws ParseException {
        //System.out.println("--------------time--------------"+newsDto.getTimeSelect());
        /*System.out.println("-----------"+newsDto.getContentTitle()+"-----------------"+newsDto.getContent());
        String timing =new QuartzConfig().TimingJob(request,new News(newsDto.getContentTitle(),newsDto.getContent())
                , newsDto.getTimeSelect());
        if (timing!=null) {
            return ResultVo.success(timing);
        }*/
        Claims token = JWTUtil.getClaimByToken(request.getHeader("token"));
        Integer id = (Integer) token.get("id");
        newsDto.setPublishId(id);
        String stamp = Stamp2date.dateToStamp(newsDto.getTimeSelect());
        //比较定时的时间于当前时间
        long timeMillis = System.currentTimeMillis();
        Long time = Long.parseLong(stamp)-timeMillis;
        if(timeMillis<=Long.parseLong(stamp)) {
            xdelaySender.send(newsDto, Math.toIntExact(time));
            return ResultVo.success("定时任务设置成功");
        }
        return ResultVo.fail("定时任务时间设置失败");
    }
}