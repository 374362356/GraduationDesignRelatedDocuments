package com.pzh.zp.controller.DbController;

import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.VO.UserMapVo;
import com.pzh.zp.entity.User;
import com.pzh.zp.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 用户表(User)表控制层
 *
 * @author makejava
 * @since 2021-01-25 16:45:36
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 增加一个标识字段表明是管理员还是用户，注册时默认是用户
     * 管理员通过页面的按钮来决定用户状态，是否冻结
     * 插入判断用户账号唯一
     * 系统管理员增加页面付角色功能
     */

    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public User selectOne(Integer id) {
        return this.userService.queryById(id);
    }

    /**
     * 通过用户名和电话模糊查询
     *
     * @param nickName
     * @param phone
     * @return 对象列表
     */
    @RequestMapping("/fuzzy")
    public List<User> fuzzySearch(@RequestParam(required = false,defaultValue = "") String nickName, @RequestParam(required = false,defaultValue = "") String phone){
        System.out.println("==============>"+nickName+phone);
        return userService.fuzzySearch(nickName,phone);
    }

    @GetMapping("/findAll")
    public List<UserMapVo> findAll(){
        return userService.queryAll(null);
    }

    /**
     * 插入用户
     * @param user
     * @return
     */
    @PostMapping("/user_insert")
    public ResultVo insertUser(@RequestBody User user) throws ParseException {
        User insert = userService.insert(user);
        //System.out.println("------------------>"+insert);
        return ResultVo.success(insert);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/user_delete")
    public ResultVo deleteUser(@RequestParam Integer id){
        System.out.println("-----------删除-------------");
        boolean b = userService.deleteById(id);
        if (b){
            return ResultVo.success(b);
        }
        return ResultVo.fail("删除失败");
    }

    /**
     * 找到需要更新得用户信息
     * @param id
     * @return
     */
    @GetMapping("/toUpdate/{id}")
    public ResultVo toUpdate(@PathVariable Integer id){
        User user = userService.queryById(id);
        return ResultVo.success(user);
    }

    /**
     * 更新
     * @param user
     * @return
     */
    @PutMapping("/update")
    public ResultVo updateUser(@RequestBody User user) throws ParseException {
        //System.out.println("--------------------->"+user);
        User update = userService.update(user);
        return ResultVo.success(update);
    }

    @GetMapping("/getUserInfo")
    public ResultVo getUserInfo(@RequestParam String username){
        return ResultVo.success(userService.queryByuserName(username));
    }
}