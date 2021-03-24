package com.pzh.zp.controller.DbController;

import com.pzh.zp.entity.Reports;
import com.pzh.zp.VO.ResultVo;
import com.pzh.zp.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reports")
public class ReportsController {

    @Autowired
    private ReportsService reportsService;

    @GetMapping("/queryInfo")
    public ResultVo findAll(){
        return ResultVo.success(reportsService.queryAll());
    }

    @PutMapping("/reports_update")
    public ResultVo update(@RequestBody Reports reports){
        return ResultVo.success(reportsService.update(reports));
    }

    @PostMapping("/reports_insert")
    public ResultVo insert(@RequestBody Reports reports){
        return ResultVo.success(reportsService.insert(reports));
    }

    @DeleteMapping("/reports_delete")
    public ResultVo delete(int id){
        return ResultVo.success(reportsService.delete(id));
    }
}
