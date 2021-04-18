package com.pzh.zp.service.impl;

import com.pzh.zp.VO.StaffVo;
import com.pzh.zp.dao.ConferenceDao;
import com.pzh.zp.dao.StaffDao;
import com.pzh.zp.entity.Conference;
import com.pzh.zp.entity.Staff;
import com.pzh.zp.enumState.UserEnum;
import com.pzh.zp.service.StaffService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 工作人员表(Staff)表服务实现类
 *
 * @author makejava
 * @since 2021-01-25 16:45:33
 */
@Service("staffService")
public class StaffServiceImpl implements StaffService {
    @Resource
    private StaffDao staffDao;

    @Resource
    private ConferenceDao conferenceDao;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Staff queryById(Integer id) {
        return this.staffDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<StaffVo> queryAllByLimit(int offset, int limit) {
        List<StaffVo> staffVos = new ArrayList<>();
        List<Staff> staff = this.staffDao.queryAllByLimit(offset, limit);
        //System.out.println("================>"+staff);
        List<Integer> conferenceId = staff.stream().map(s -> s.getConferenceId()).collect(Collectors.toList());
        for (Integer id:conferenceId) {
            String name = conferenceDao.queryById(id).getName();
            for (Staff s: staff) {
                if(s.getConferenceId().equals(id)) {
                    StaffVo vo = new StaffVo(s.getId(), s.getSName(), s.getGender(), s.getEmail(), s.getPhone(), s.getPosition(), name,s.getLeave());
                    staffVos.add(vo);
                }
            }
            if(staffVos.size()==staff.size()){      //还有问题
                break;
            }
        }
        return staffVos;
    }

    /**
     * 新增数据
     *
     * @param staff 实例对象
     * @return 实例对象
     */
    @Override
    public Staff insert(StaffVo staff) {
        String conferenceName = staff.getConferenceName();
        int id = conferenceDao.queryIdByName(conferenceName);
        Staff newStaff = new Staff(0, staff.getSName(), staff.getGender(), staff.getEmail(), staff.getPhone(), staff.getPosition(), id, UserEnum.UNLEAVE.getKey());
        //得到所有会议的结束时间，判断新增负责人的名下是否有会议主持的开始时间早于存在的会议主持的结束时间
        this.staffDao.insert(newStaff);
        return newStaff;
    }

    /**
     * 修改数据
     *
     * @param staffVo 实例对象
     * @return 实例对象
     */
    @Override
    public Staff update(StaffVo staffVo) {
        int conferenceId = conferenceDao.queryIdByName(staffVo.getConferenceName());
        Staff staff = new Staff(staffVo.getId(), staffVo.getSName(), staffVo.getGender(), staffVo.getEmail(), staffVo.getPhone(),
                staffVo.getPosition(), conferenceId, staffVo.getLeave());
        this.staffDao.update(staff);
        return this.queryById(staff.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.staffDao.deleteById(id) > 0;
    }

    /**
     *
     * @param sName
     * @param email
     * @param phone
     * @param position
     * @return
     */
    @Override
    public List<Staff> fuzzySearch(String sName, String email, String phone, String position) {
        return staffDao.fuzzySearch(sName, email, phone, position);
    }

    /**
     *
     * @param staff 实例对象
     * @return
     */
    @Override
    public List<Staff> queryAll(Staff staff) {
        return staffDao.queryAll(staff);
    }
}