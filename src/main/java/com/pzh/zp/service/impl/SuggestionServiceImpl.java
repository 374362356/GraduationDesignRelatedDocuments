package com.pzh.zp.service.impl;

import com.pzh.zp.dao.SuggestionDao;
import com.pzh.zp.entity.Suggestion;
import com.pzh.zp.service.SuggestionService;
import com.pzh.zp.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



/**
 * 建议表(Suggestion)表服务实现类
 *
 * @author makejava
 * @since 2021-01-25 16:45:34
 */
@Service("suggestionService")
public class SuggestionServiceImpl implements SuggestionService {
    @Resource
    private SuggestionDao suggestionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Suggestion queryById(Integer id) {
        return this.suggestionDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Suggestion> queryAllByLimit(int offset, int limit) {
        return this.suggestionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param suggestion 实例对象
     * @return 实例对象
     */
    @Override
    public Suggestion insert(HttpServletRequest request, Suggestion suggestion) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(new Date());
        Date date = dateFormat.parse(format);
        if(suggestion!= null) {
            Claims token = JWTUtil.getClaimByToken(request.getHeader("token"));
            Integer id = (Integer)token.get("id");
            suggestion.setSuggTime(date);
            suggestion.setPersonId(id);
            //这里设置staffid应改为会议id
            //suggestion.setStaffId();
            this.suggestionDao.insert(suggestion);
            return suggestion;
        }
        return null;
    }

    /**
     * 修改数据
     *
     * @param suggestion 实例对象
     * @return 实例对象
     */
    @Override
    public Suggestion update(Suggestion suggestion) {
        this.suggestionDao.update(suggestion);
        return this.queryById(suggestion.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.suggestionDao.deleteById(id) > 0;
    }

    @Override
    public List<Suggestion> fuzzyInfo(String suggestionTitle) {
        return suggestionDao.fuzzyInfo(suggestionTitle);
    }
}