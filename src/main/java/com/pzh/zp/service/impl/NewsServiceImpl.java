package com.pzh.zp.service.impl;

import com.pzh.zp.dao.NewsDao;
import com.pzh.zp.entity.News;
import com.pzh.zp.entity.Staff;
import com.pzh.zp.entity.User;
import com.pzh.zp.service.NewsService;
import com.pzh.zp.utils.JWTUtil;
import com.pzh.zp.utils.Stamp2date;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 公告表(News)表服务实现类
 *
 * @author makejava
 * @since 2021-01-25 16:45:28
 */
@Service("newsService")
public class NewsServiceImpl implements NewsService {
    @Resource
    private NewsDao newsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public News queryById(Integer id) {
        return this.newsDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<News> queryAllByLimit(int offset, int limit) {
        return this.newsDao.queryAllByLimit(offset, limit);
    }

    /**
     *
     * @param request
     * @param news
     * @return
     * @throws ParseException
     */
    @Override
    public News insert(HttpServletRequest request,News news) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(new Date());
        Date date = dateFormat.parse(format);
        news.setNewTime(date);

        if(!news.getContentTitle().isEmpty() && !"".equals(news.getContentTitle())){
            Claims token = JWTUtil.getClaimByToken(request.getHeader("token"));
            Integer status = (Integer) token.get("id");
            news.setStateId(status);
            this.newsDao.insert(news);
            System.out.println("======>"+news);
            return news;
        }
        return null;
    }

    @Override
    public String InsertTiming(HttpServletRequest request,News news,String timeSelect) throws ParseException {
        //String sub = timeSelect.substring(0, timeSelect.lastIndexOf("："));
        String stamp = Stamp2date.dateToStamp(timeSelect);
        //比较定时的时间于当前时间
        long timeMillis = System.currentTimeMillis();
        if(timeMillis<=Long.parseLong(stamp)){
            new Timer().schedule(new TimerTask() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println("-------定时任务指定任务--------");
                    insert(request, news);
                }
            }, Long.parseLong(stamp)-timeMillis);
        }
        return null;
    }
    /**
     * 修改数据
     *
     * @param news 实例对象
     * @return 实例对象
     */
    @Override
    public News update(News news) {
        this.newsDao.update(news);
        return this.queryById(news.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.newsDao.deleteById(id) > 0;
    }

    /**
     *
     * @param news 实例对象
     * @return 对象列表
     */
    @Override
    public List<News> queryAll(News news) {
        return newsDao.queryAll(news);
    }

    /**
     *
     * @param newTime
     * @param contentTitle
     * @return 对象列表
     */
    @Override
    public List<News> fuzzySearch(String newTime, String contentTitle) {
        return newsDao.fuzzySearch(newTime, contentTitle);
    }

    @Override
    public User findPublishName(Integer id, Integer stateId) {
        return newsDao.findPublishName(id,stateId);
    }

}