package com.pzh.zp.controller.DbController;

import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.entity.Permission;
import com.pzh.zp.service.PermissionService;
import com.pzh.zp.service.impl.PermissionServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;


/**
 * 权限表(Permission)表控制层
 *
 * @author makejava
 * @since 2021-01-25 16:45:29
 */
@RestController
@RequestMapping("permission")
public class PermissionController {
    /**
     * 服务对象
     */
    @Resource
    private PermissionService permissionService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Permission selectOne(Integer id) {
        return this.permissionService.queryById(id);
    }

    @GetMapping("/permission_list")
    public ResultVo findAll(@RequestParam int offset,@RequestParam int limit){
        return ResultVo.success(permissionService.queryAllByLimit(offset, limit));
    }

    @PostMapping("/permission_insert")
    public ResultVo addPermission(@RequestBody Permission permission) throws ParseException {
        return ResultVo.success(permissionService.insert(permission));
    }

    @PutMapping("/permission_update")
    public ResultVo updatePermission(@RequestBody Permission permission){
        return  ResultVo.success(permissionService.update(permission));
    }

    @DeleteMapping("/permission_del")
    public  ResultVo delPermission(@RequestParam Integer id){
        return ResultVo.success(permissionService.deleteById(id));
    }

    @GetMapping("/fuzzy")
    public List queryFuzzy(@RequestParam String pName,@RequestParam String path){
        return permissionService.queryFuzzy(pName, path);
    }
}