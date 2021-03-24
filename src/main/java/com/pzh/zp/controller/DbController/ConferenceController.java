package com.pzh.zp.controller.DbController;

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
     * 会议还存在会议地点是选择还是输入问题
     * 会议的在结束时间后便不能选择的问题
     * 会议的容纳人数上限
     * 前端会议添加空值判断
     */


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

    /**
     * 会议信息修改
     * @param conferenceId
     * @param conference
     * @return
     */
    @PutMapping("/conference_update/{conferenceId}")
    public ResultVo updateConference(@PathVariable Integer conferenceId, @RequestBody Conference conference){
        System.out.println("========update========>"+conference);
        conference.setId(conferenceId);
        Conference update = conferenceService.update(conference);
        return ResultVo.success(update);
    }

    @DeleteMapping("/conference_delete")
    public ResultVo deleteConference(@RequestParam Integer id){
        boolean b = conferenceService.deleteById(id);
        if (b){
            return ResultVo.success(b);
        }
        return ResultVo.fail("删除失败");
    }
}