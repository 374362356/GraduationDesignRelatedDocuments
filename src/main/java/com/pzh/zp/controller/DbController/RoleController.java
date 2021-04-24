package com.pzh.zp.controller.DbController;

import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.entity.Role;
import com.pzh.zp.service.RoleService;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;


/**
 * 角色表(Role)表控制层
 *
 * @author makejava
 * @since 2021-01-25 16:45:32
 */
@RestController
@RequestMapping("role")
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Role selectOne(@RequestParam Integer id) {
        return this.roleService.queryById(id);
    }

    @GetMapping("/role_list")
    public ResultVo findAll(){
        return ResultVo.success(roleService.queryAll(null));
    }

    @PostMapping("/role_insert")
    public ResultVo addRole(@RequestBody Role role) throws ParseException {
        return ResultVo.success(roleService.insert(role));
    }

    @PutMapping("/role_update")
    public ResultVo update(@RequestBody Role role){
        return ResultVo.success(roleService.update(role));
    }

    @DeleteMapping("/role_del")
    public ResultVo delete(@RequestParam Integer id){
        return ResultVo.success(roleService.deleteById(id));
    }

    @PutMapping("/updateRole")
    public ResultVo updateRoleUser(HttpServletRequest request,@RequestParam String role_id, @RequestParam String user_id){
        int update = roleService.updateUserRole(request, role_id, user_id);
        if (update!=-1){
            return ResultVo.success(update);
        }
        return ResultVo.fail("更新失败，无此权限");
    }

    @GetMapping("/findRole")
    public ResultVo findRole(@RequestParam String userId){
        return ResultVo.success(roleService.findRole(userId));
    }
}