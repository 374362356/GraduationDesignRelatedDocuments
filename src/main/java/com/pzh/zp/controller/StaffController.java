package com.pzh.zp.controller;

import com.pzh.zp.entity.Staff;
import com.pzh.zp.service.StaffService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 工作人员表(Staff)表控制层
 *
 * @author makejava
 * @since 2021-01-25 16:45:33
 */
@RestController
@RequestMapping("staff")
public class StaffController {
    /**
     * 服务对象
     */
    @Resource
    private StaffService staffService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Staff selectOne(Integer id) {
        return this.staffService.queryById(id);
    }

    /**
     *
     * @return
     */
    @GetMapping("/findAll")
    public List<Staff> findAll(){
        return staffService.queryAll(null);
    }

    /**
     *
     * @param name
     * @param email
     * @param phone
     * @param position
     * @return
     */
    @RequestMapping("/fuzzy")
    public List<Staff> fuzzySearch(@RequestParam(required = false) String name, @RequestParam(required = false) String email, @RequestParam(required = false) String phone, @RequestParam(required = false) String position){
        return staffService.fuzzySearch(name, email, phone, position);
    }
}