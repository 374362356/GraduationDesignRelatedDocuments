package com.pzh.zp.controller.DbController;

import com.pzh.zp.entity.Reach;
import com.pzh.zp.service.ReachService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 到会时间表(Reach)表控制层
 *
 * @author makejava
 * @since 2021-01-25 16:45:31
 */
@RestController
@RequestMapping("reach")
public class ReachController {
    /**
     * 服务对象
     */
    @Resource
    private ReachService reachService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Reach selectOne(Integer id) {
        return this.reachService.queryById(id);
    }

}