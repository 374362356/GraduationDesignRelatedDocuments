package com.pzh.zp.service.impl;

import com.pzh.zp.VO.PersonVo;
import com.pzh.zp.dao.*;
import com.pzh.zp.entity.Conference;
import com.pzh.zp.entity.Person;
import com.pzh.zp.entity.Reach;
import com.pzh.zp.entity.Role;
import com.pzh.zp.enumState.UserEnum;
import com.pzh.zp.service.PersonService;
import com.pzh.zp.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 参会人员表(Person)表服务实现类
 *
 * @author makejava
 * @since 2021-01-25 16:45:29
 */
@Service("personService")
public class PersonServiceImpl implements PersonService {
    @Resource
    private PersonDao personDao;

    @Resource
    private ConferenceDao conferenceDao;

    @Resource
    private ReachDao reachDao;

    @Resource
    private StaffDao staffDao;

    @Resource
    private RoleDao roleDao;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Person queryById(Integer id) {
       return this.personDao.queryById(id);
    }

    @Override
    @Transactional
    public PersonVo toUpdate(Integer id) {
        int conferenceId = personDao.findC_idByP_id(id);
        String name = conferenceDao.queryById(conferenceId).getName();
        Person person = this.personDao.queryById(id);
        return new PersonVo(person.getId(),person.getPName(),person.getAge(),person.getGender(),
                person.getEmail(),person.getPhone(),person.getTitle(),name);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Person> queryAllByLimit(int offset, int limit) {
        return this.personDao.queryAllByLimit(offset, limit);
    }

    /**
     *  插入参会人员时需同时建立一个签到记录，reach表中的userid为person表id；
     *  staffid何conferenceid需对应staff表的记录。staff与conference一对多，需判断会议主持在不同的时间负责会议
     *
     * @param personVo 实例对象
     * @return
     */
    @Override
    @Transactional
    public Person insert(PersonVo personVo) {
        Person person = new Person(0,personVo.getPname(), personVo.getAge(),
                personVo.getGender(), personVo.getEmail(), personVo.getPhone(), personVo.getTitle());
        try {
            this.personDao.insert(person);
        } finally {
            int conference_id = conferenceDao.queryIdByName(personVo.getName());
            int person_id = person.getId();       //null
            personDao.insertActivity_person(conference_id, person_id);
            //新增参会人员是需要新增其签到信息
            Integer staffId = staffDao.queryStaffIdByConferenceId(conference_id);
            if(staffId!=null){
                Reach reach = new Reach(0, null, UserEnum.UNCHECKIN.getKey(), person_id, staffId, conference_id);
                reachDao.insert(reach);
            }
        }
        return person;
    }

    /**
     * 修改数据
     *
     * @param personVo 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Person update(PersonVo personVo) {
        Person person = new Person(personVo.getId(),personVo.getPname(), personVo.getAge(),
                personVo.getGender(), personVo.getEmail(), personVo.getPhone(), personVo.getTitle());
        int conferenceId = conferenceDao.queryIdByName(personVo.getName());
        int id = personDao.findIdByP_id(personVo.getId());
        personDao.updateActivity_person(conferenceId,personVo.getId(),id);
        this.personDao.update(person);
        return this.queryById(person.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean deleteById(Integer id) { //人员没有对应的会议这里类型为int返回的却是null会报错
        int idByPId = personDao.findIdByP_id(id);
        System.out.println("=============>"+idByPId);
        if(idByPId!=0 || !String.valueOf(idByPId).equals(null)) {
            personDao.deleteActivity_person(idByPId);
        }
        return this.personDao.deleteById(id) > 0;
    }

    /**
     *
     * @return
     */
    @Override
    @Transactional
    public List<PersonVo> queryAllInfoPerson(HttpServletRequest request) {
        Claims token = JWTUtil.getClaimByToken(request.getHeader("token"));
        Integer id = (Integer)token.get("id");
        int roleId = roleDao.findRoleIdByUserId(id);
        Role role = roleDao.queryById(roleId);

        //Map<Integer,List<PersonVo>> map = new HashMap<>();
        List<PersonVo> person = null;
        //判断用户角色展示不同数据
        if (UserEnum.user.getValue().equals(role.getRName()) && StringUtils.isNotBlank(id.toString())){
            List<Integer> conferList = personDao.findConferIdByPId(id).stream().distinct().collect(Collectors.toList());
            //这里只显示了该用户参加的一个会议里的所有人
            for (Integer c_id : conferList) {
                 person = personDao.findPersonByConferId(c_id);
            }
            return  person;
        }
        return personDao.queryAllInfoPerson();
    }

    /**
     *
     * @param pname
     * @param name
     * @param phone
     * @return
     */
    @Override
    public List<Person> fuzzySearchPerson(String pname, String name, String phone) {
        return personDao.fuzzySearchPerson(pname, name, phone);
    }
}