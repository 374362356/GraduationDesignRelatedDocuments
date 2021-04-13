package com.pzh.zp.controller.DbController;

import com.pzh.zp.VO.ReachVo;
import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.entity.Reach;
import com.pzh.zp.service.ReachService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


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
     *  为每个会议的参加人员都创建一个签到记录，判断是不是当天，会前两小时才允许签到。开始后十分钟停止
     */

    /**
     * 服务对象
     */
    @Resource
    private ReachService reachService;


    @GetMapping("findAll")
    public ResultVo findAll(HttpServletRequest request) {
        return ResultVo.success(reachService.findAll(null,request));
    }

    @PutMapping("/reach_update")
    public ResultVo reachUpdate(@RequestBody ReachVo reach){
        return ResultVo.success(reachService.update(reach));
    }

    @GetMapping("/findOne/{id}")
    public ResultVo toUpdate(@PathVariable Integer id){
        ReachVo reachVo = reachService.queryById(id);
        if (reachVo!=null){
            return ResultVo.success(reachVo);
        }
        return ResultVo.fail("签到出错");
    }

    @DeleteMapping("/del_reach")
    public ResultVo delReach(@RequestParam Integer id){
        return ResultVo.success(reachService.deleteById(id));
    }
}