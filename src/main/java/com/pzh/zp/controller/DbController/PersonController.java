package com.pzh.zp.controller.DbController;


import com.pzh.zp.VO.PersonVo;
import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.entity.Person;
import com.pzh.zp.service.ConferenceService;
import com.pzh.zp.service.PersonService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 参会人员表(Person)表控制层
 *
 * @author makejava
 * @since 2021-01-25 16:45:30
 */
@RestController
@RequestMapping("person")
public class PersonController {
    /**
     *  普通用户登录，列表展示跟用户相关的会议参与人员
     */

    /**
     * 服务对象
     */
    @Resource
    private PersonService personService;

    @Resource
    private ConferenceService conferenceService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Person selectOne(@RequestParam Integer id) {
        return this.personService.queryById(id);
    }

    @GetMapping("toUpdate")
    public PersonVo toUpdate(@RequestParam Integer id) {
        return this.personService.toUpdate(id);
    }

    @GetMapping("/findAll")
    public ResultVo findAll(HttpServletRequest request){
        return ResultVo.success(personService.queryAllInfoPerson(request));
    }

    /**
     *
     * @param pname
     * @param name
     * @param phone
     * @return
     */
    @RequestMapping("/fuzzy")
    public ResultVo fuzzySearch(@RequestParam(required = false) String pname,@RequestParam(required = false) String name,@RequestParam(required = false) String phone){
        return ResultVo.success(personService.fuzzySearchPerson(pname, name, phone));
    }

    /**
     * 下拉框显示所有会议名
     * @return
     */
    @GetMapping("/selectAllName")
    public ResultVo selectAllConferenceName(){
        return ResultVo.success(conferenceService.queryAllName());
    }

    @PostMapping("/person_insert")
    public ResultVo addPerson(@RequestBody PersonVo personVo){
        Person insert = personService.insert(personVo);
        if (insert!=null) {
            return ResultVo.success(insert);
        }
        return ResultVo.fail("出状况了");
    }

    @PutMapping("/person_update")
    public ResultVo updatePerson(@RequestBody PersonVo personVo){
        return ResultVo.success(personService.update(personVo));
    }

    @GetMapping("/person_delete")
    public ResultVo deletePerson(@RequestParam Integer id){
        return ResultVo.success(personService.deleteById(id));
    }
}