package com.pzh.zp.service.impl;

import com.pzh.zp.VO.NewsVo;
import com.pzh.zp.dao.NewsDao;
import com.pzh.zp.dao.UserDao;
import com.pzh.zp.entity.News;
import com.pzh.zp.entity.Staff;
import com.pzh.zp.entity.User;
import com.pzh.zp.service.NewsService;
import com.pzh.zp.utils.JWTUtil;
import com.pzh.zp.utils.Stamp2date;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

    @Resource
    private UserDao userDao;
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
            Integer id = (Integer) token.get("id");
            news.setPublishId(id);
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
                @Override
                public void run() {
                    Thread thread = new Thread(() -> {
                        try {
                            insert(request, news);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    });
                    thread.start();
                }}, Long.parseLong(stamp)-timeMillis);
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
    public News update(NewsVo news) {
        User user = userDao.queryByuserName(news.getPublishName());
        News newOne = new News(news.getId(), news.getNewTime(), news.getContentTitle(), news.getContent(), user.getId());
        this.newsDao.update(newOne);
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
    public List<NewsVo> queryAll(News news) {
        List<News> newsList = newsDao.queryAll(news);
        Map<Integer, Integer> idAndPid = newsList.stream().collect(Collectors.toMap(News::getId, News::getPublishId));
        List<NewsVo> newsVos = new ArrayList<>();
        for (News n : newsList) {
            Integer publishId = idAndPid.get(n.getId());
            List<User> user = newsDao.findPublishName(publishId);
            List<User> collect = user.stream().distinct().collect(Collectors.toList());
            for (User u : collect){
                newsVos.add(new NewsVo(n.getId(), n.getNewTime(), n.getContentTitle(), n.getContent(), u.getUserName()));
            }
        }
        return newsVos;
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
    public List<User> findPublishName(Integer publishId) {
        return newsDao.findPublishName(publishId);
    }

}