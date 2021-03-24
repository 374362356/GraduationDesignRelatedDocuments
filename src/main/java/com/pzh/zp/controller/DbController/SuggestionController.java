package com.pzh.zp.controller.DbController;


import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.entity.Suggestion;
import com.pzh.zp.service.SuggestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.transform.Result;


/**
 * 建议表(Suggestion)表控制层
 *
 * @author makejava
 * @since 2021-01-25 16:45:34
 */
@RestController
@RequestMapping("suggestion")
public class SuggestionController {
    /**
     * 服务对象
     */
    @Resource
    private SuggestionService suggestionService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Suggestion selectOne(Integer id) {
        return this.suggestionService.queryById(id);
    }

    @GetMapping("/findAll")
    public ResultVo findAll(@RequestParam int offset, @RequestParam int limit){
        return ResultVo.success(suggestionService.queryAllByLimit(offset, limit));
    }

    @DeleteMapping("/suggestion_delete")
    public ResultVo delSuggestion(@RequestParam Integer id){
        boolean b = suggestionService.deleteById(id);
        if (b){
            return ResultVo.success(b);
        }
        return ResultVo.fail("删除失败");
    }

    @GetMapping("/fuzzy")
    public ResultVo fuzzyInfo(@RequestParam String suggestionTitle){
        return ResultVo.success(suggestionService.fuzzyInfo(suggestionTitle));
    }
}