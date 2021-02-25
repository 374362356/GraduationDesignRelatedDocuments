package com.pzh.zp.controller;

import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.entity.Conference;
import com.pzh.zp.service.ConferenceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 会议表(Conference)表控制层
 *
 * @author makejava
 * @since 2021-01-25 16:45:26
 */
@RestController
@RequestMapping("conference")
public class ConferenceController {
    /**
     * 服务对象
     */
    @Resource
    private ConferenceService conferenceService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Conference selectOne(Integer id) {
        return this.conferenceService.queryById(id);
    }

    /**
     * 模糊查询
     *
     * @param name
     * @param place
     * @param host
     * @return 对象列表
     */
    @RequestMapping("/fuzzy")
    public List<Conference> fuzzySearch(@RequestParam(required = false) String name, @RequestParam(required = false) String place, @RequestParam(required = false) String host){
        return conferenceService.fuzzySearch(name, place, host);
    }

    /**
     * 查询所有
     *
     * @return 对象列表
     */
    @GetMapping("/findAll")
    public List<Conference> findAll(){
        return conferenceService.queryAll(null);
    }

    /**
     * 新增会议
     * @param conference
     * @return
     */
    @PostMapping("/conference_insert")
    public ResultVo insertConference(@RequestBody Conference conference){
        System.out.println("========add=======>"+conference);
        Conference insert = conferenceService.insert(conference);
        return ResultVo.success(insert);
    }

    
    @PutMapping("/update")
    public ResultVo updateConference(@RequestBody Conference conference){
        System.out.println("========update========>"+conference);
        Conference update = conferenceService.update(conference);
        return ResultVo.success(update);
    }
}