package com.pzh.zp.controller.DbController;

import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.entity.Locale;
import com.pzh.zp.service.LocaleService;
import org.apache.tomcat.jni.Local;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Locale)表控制层
 *
 * @author makejava
 * @since 2021-04-22 18:02:19
 */
@RestController
@RequestMapping("locale")
public class LocaleController {
    /**
     * 服务对象
     */
    @Resource
    private LocaleService localeService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Locale selectOne(Integer id) {
        return this.localeService.queryById(id);
    }

    @GetMapping("/findPlaces")
    public ResultVo findPlaces(){
        return ResultVo.success(localeService.findAllPlaces());
    }

    @GetMapping("/findAll")
    public ResultVo findAllLocale() {
        return ResultVo.success(localeService.queryAll(null));
    }

    @RequestMapping("/fuzzyLocale")
    public ResultVo fuzzyLocale(@RequestParam String places){
        return ResultVo.success(localeService.queryLocaleFuzzy(places));
    }

    @PostMapping("/locale_insert")
    public ResultVo insertLocale(@RequestBody Locale locale){
        return ResultVo.success(localeService.insert(locale));
    }

    @GetMapping("/toUpdate")
    public Locale toupdate(@RequestParam Integer id){
        return localeService.queryById(id);
    }

    @PutMapping("/locale_update")
    public ResultVo updateLocale(@RequestBody Locale locale){
        return ResultVo.success(localeService.update(locale));
    }

    @DeleteMapping("/locale_del")
    public ResultVo delLocale(@RequestParam Integer id){
        return ResultVo.success(localeService.deleteById(id));
    }
}
