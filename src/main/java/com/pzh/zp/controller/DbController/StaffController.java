package com.pzh.zp.controller.DbController;

import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.VO.StaffVo;
import com.pzh.zp.entity.Staff;
import com.pzh.zp.service.StaffService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.transform.Result;
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
    public ResultVo queryAllByLimit(int offset, int limit){
        return ResultVo.success(staffService.queryAllByLimit(offset, limit));
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
    public List<Staff> fuzzySearch(@RequestParam(required = false) String name, @RequestParam(required = false) String email,
                                   @RequestParam(required = false) String phone, @RequestParam(required = false) String position){
        return staffService.fuzzySearch(name, email, phone, position);
    }

    @PostMapping("/staff_insert")
    public ResultVo insert(@RequestBody StaffVo staff){
       return ResultVo.success(staffService.insert(staff));
    }

    @PutMapping("/staff_update")
    public ResultVo update(@RequestBody StaffVo staffVo){
        return ResultVo.success(staffService.update(staffVo));
    }

    @DeleteMapping("/staff_delete")
    public ResultVo delete(@RequestParam int id){
        return ResultVo.success(staffService.deleteById(id));
    }
}