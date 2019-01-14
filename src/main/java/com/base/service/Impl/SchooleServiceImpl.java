package com.base.service.Impl;

import com.base.domain.department.entity.Department;
import com.base.domain.faq.dto.PageDto;
import com.base.domain.school.dto.SchPageDto;
import com.base.domain.school.entity.School;
import com.base.domain.specialty.entity.Specialty;
import com.base.domain.user.entity.User;
import com.base.http.ErrType;
import com.base.http.PageVo;
import com.base.http.ResultMap;
import com.base.mapper.DepartmentMapper;
import com.base.mapper.SchoolMapper;
import com.base.mapper.SpecialtyMapper;
import com.base.mapper.UserMapper;
import com.base.service.SchoolService;
import com.base.util.BaseUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SchooleServiceImpl implements SchoolService {

    private static Logger logger = LoggerFactory.getLogger(SchooleServiceImpl.class);

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private SpecialtyMapper specialtyMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultMap schoolAdd(String schoolName) {
        logger.info("添加学校名称：" + schoolName);
        // 名称不允许为空
        if(BaseUtils.TrimIsEmpty(schoolName)){
            return new ResultMap(ErrType.PARAM_NULL);
        }
        // 去除前后空格
        schoolName = BaseUtils.OverTrim(schoolName);
        // 学校名称不允许重复
        Example example = new Example(School.class);
        example.createCriteria()
                .andEqualTo("school", schoolName);
        List<School> list = schoolMapper.selectByExample(example);
        if (list.size() > 0){
            return new ResultMap(ErrType.NAME_REPEAT);
        }
        // 学校入库
        School school = new School();
        school.setRowguid(UUID.randomUUID().toString());
        school.setCdate(BaseUtils.dateCreate());
        school.setSchool(schoolName);

        schoolMapper.insertSelective(school);
        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
    public ResultMap allSchool(PageDto pageDto) {
        logger.info("查询所有的学校");
        String isPage = "";
        int allCount = 0;
        List<School> list = new ArrayList<>();
        try{
            PageHelper.startPage(pageDto.getStart(), pageDto.getSize());
            isPage = "查询成功-分页";
            list = schoolMapper.selectAll();
        } catch (Exception e){
            isPage = "查询成功-未分页";
            list = schoolMapper.selectAll();
        }
        allCount = schoolMapper.selectAll().size();
        PageVo pageVo = new PageVo();
        pageVo.setPage(list);
        pageVo.setAllcount(allCount);
        return new ResultMap("200",isPage, pageVo);
    }

    @Override
    public ResultMap departmentAdd(String schoolguid, String department) {
        logger.info("院系添加，名称为：" + department);
        // 名称不允许为空
        if(BaseUtils.TrimIsEmpty(department)){
            return new ResultMap(ErrType.PARAM_NULL);
        }
        // 去除前后空格
        department = BaseUtils.OverTrim(department);
        // 判断学校是否还存在
        School school = schoolMapper.selectByPrimaryKey(schoolguid);
        if (school == null){
            return new ResultMap(ErrType.SCHOOL_NOEXIST);
        }
        // 判断院系名称是否重复
        Department department1 = new Department();
        department1.setDepartment(department);
        department1.setSchoolguid(schoolguid);
        Department isRepeat = departmentMapper.selectOne(department1);
        if (isRepeat != null){
            return new ResultMap(ErrType.NAME_REPEAT);
        }
        // 院系入库
        department1.setCdate(BaseUtils.dateCreate());
        department1.setRowguid(UUID.randomUUID().toString());
        departmentMapper.insertSelective(department1);

        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
   public ResultMap departmentList(SchPageDto schPageDto) {
        logger.info("查询此id：" + schPageDto.getRowguid() + "下的所有学院");
        int allCount = 0;
        String isPage = "";
        List<Department> list = new ArrayList<>();
        // 判断学校是否还存在
        School school = schoolMapper.selectByPrimaryKey(schPageDto.getRowguid());
        if (school == null){
            return new ResultMap(ErrType.SCHOOL_NOEXIST);
        }
        // 查询此学校下所有学院
        Example example = new Example(Department.class);
        example.createCriteria()
                .andEqualTo("schoolguid", schPageDto.getRowguid());
        try {
            // 分页插件
            PageHelper.startPage(schPageDto.getStart(), schPageDto.getSize());
            isPage = "查询成功-分页";
            logger.info("分页查询");
            list = departmentMapper.selectByExample(example);
        } catch (Exception e){
            logger.info("未分页");
            isPage = "查询成功-未分页";
            list = departmentMapper.selectByExample(example);
        }
        allCount = departmentMapper.selectCountByExample(example);
        PageVo pageVo = new PageVo();
        pageVo.setAllcount(allCount);
        pageVo.setPage(list);
        return new ResultMap("200",isPage,pageVo);
    }

    @Override
    public ResultMap speAdd(String departmentguid, String specialty) {
        logger.info("专业添加，名称为：" + specialty);
        // 名称不允许为空
        if(BaseUtils.TrimIsEmpty(specialty)){
            return new ResultMap(ErrType.PARAM_NULL);
        }
        // 去除前后空格
        specialty = BaseUtils.OverTrim(specialty);
        // 判断院系是否还存在
        Department department = departmentMapper.selectByPrimaryKey(departmentguid);
        if (department == null){
            return new ResultMap(ErrType.DEPARTMENT_NOEXIST);
        }
        // 判断此专业名称是否重复
        Specialty specialty1 = new Specialty();
        specialty1.setSpecialty(specialty);
        specialty1.setDepartmentguid(departmentguid);
        Specialty isRepeat = specialtyMapper.selectOne(specialty1);
        if (isRepeat != null){
            return new ResultMap(ErrType.NAME_REPEAT);
        }
        // 专业信息入库
        specialty1.setRowguid(UUID.randomUUID().toString());
        specialty1.setCdate(BaseUtils.dateCreate());
        specialtyMapper.insertSelective(specialty1);

        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
    public ResultMap specialtyList(SchPageDto schPageDto) {
        logger.info("查询此id：" + schPageDto.getRowguid() + "下的所有专业");
        String isPage = "";
        List<Specialty> list = new ArrayList<>();
        int allCount = 0;
        // 判断院系是否还存在
        Department department = departmentMapper.selectByPrimaryKey(schPageDto.getRowguid());
        if (department == null){
            return new ResultMap(ErrType.DEPARTMENT_NOEXIST);
        }
        // 查询此学院下所有专业
        Example example = new Example(Specialty.class);
        example.createCriteria()
                .andEqualTo("departmentguid", schPageDto.getRowguid());
        try{
            // 分页插件
            PageHelper.startPage(schPageDto.getStart(), schPageDto.getSize());
            isPage = "查询成功-分页";
            list = specialtyMapper.selectByExample(example);
        } catch (Exception e){
            isPage = "查询成功-未分页";
            list = specialtyMapper.selectByExample(example);
        }
        allCount = specialtyMapper.selectCountByExample(example);
        PageVo pageVo = new PageVo();
        pageVo.setAllcount(allCount);
        pageVo.setPage(list);
        return new ResultMap("200",isPage,pageVo);
    }

    @Override
    public ResultMap updateSchool(String rowguid, String school) {
        logger.info("更新id为：" + rowguid +"的学校名称为：" + school);
        // 判断学校是否还存在
        School isRepeat = schoolMapper.selectByPrimaryKey(rowguid);
        if (isRepeat == null){
            return new ResultMap(ErrType.SCHOOL_NOEXIST);
        }
        // 名称不允许为空
        if(BaseUtils.TrimIsEmpty(school)){
            return new ResultMap(ErrType.PARAM_NULL);
        }
        // 去除前后空格
        school = BaseUtils.OverTrim(school);
        // 学校名称不允许重复
        Example example = new Example(School.class);
        example.createCriteria()
                .andEqualTo("school", school);
        List<School> list = schoolMapper.selectByExample(example);
        if (list.size() > 0){
            return new ResultMap(ErrType.NAME_REPEAT);
        }
        // 学校更新入库
        isRepeat.setSchool(school);

        schoolMapper.updateByPrimaryKeySelective(isRepeat);
        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
    public ResultMap updateDepartment(String rowguid, String department) {
        logger.info("更新id为：" + rowguid +"的院系名称为：" + department);
        // 判断院系是否还存在
        Department isRepeat = departmentMapper.selectByPrimaryKey(rowguid);
        if (isRepeat == null){
            return new ResultMap(ErrType.DEPARTMENT_NOEXIST);
        }
        // 名称不允许为空
        if(BaseUtils.TrimIsEmpty(department)){
            return new ResultMap(ErrType.PARAM_NULL);
        }
        // 去除前后空格
        department = BaseUtils.OverTrim(department);
        // 院系名称不允许重复
        Example example = new Example(Department.class);
        example.createCriteria()
                .andEqualTo("department", department);
        List<Department> list = departmentMapper.selectByExample(example);
        if (list.size() > 0){
            return new ResultMap(ErrType.NAME_REPEAT);
        }
        // 院系更新入库
        isRepeat.setDepartment(department);

        departmentMapper.updateByPrimaryKeySelective(isRepeat);
        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
    public ResultMap updateSpe(String rowguid, String specialty) {
        logger.info("更新id为：" + rowguid +"的专业名称为：" + specialty);
        // 判断院系是否还存在
        Specialty isRepeat = specialtyMapper.selectByPrimaryKey(rowguid);
        if (isRepeat == null){
            return new ResultMap(ErrType.SPE_NOEXIST);
        }
        // 名称不允许为空
        if(BaseUtils.TrimIsEmpty(specialty)){
            return new ResultMap(ErrType.PARAM_NULL);
        }
        // 去除前后空格
        specialty = BaseUtils.OverTrim(specialty);
        // 院系名称不允许重复
        Example example = new Example(Specialty.class);
        example.createCriteria()
                .andEqualTo("specialty", specialty);
        List<Specialty> list = specialtyMapper.selectByExample(example);
        if (list.size() > 0){
            return new ResultMap(ErrType.NAME_REPEAT);
        }
        // 院系更新入库
        isRepeat.setSpecialty(specialty);

        specialtyMapper.updateByPrimaryKeySelective(isRepeat);
        return new ResultMap(ErrType.SUCCESS);
    }

    @Transactional
    @Override
    public ResultMap deleteSpe(String rowguid) {
        logger.info("删除id为：" + rowguid +"的专业");
        // 若专业还有学生绑定，则提醒做学生信息修改工作
        Example us = new Example(User.class);
        us.createCriteria()
                .andEqualTo("specialtyguid", rowguid);
        List<User> list = new ArrayList<>();
        list = userMapper.selectByExample(us);
        // 判断含有绑定关系
        if (list.size() > 0){
            return new ResultMap(ErrType.SPE_USER_BIND);
        }
        specialtyMapper.deleteByPrimaryKey(rowguid);

        return new ResultMap(ErrType.SUCCESS);
    }

    @Transactional
    @Override
    public ResultMap deleteDepartment(String rowguid) {
        logger.info("删除id为：" + rowguid +"的院系");
//
//        // 删除院系下的专业
//        Example example = new Example(Specialty.class);
//        example.createCriteria()
//                .andEqualTo("departmentguid", rowguid);
//        specialtyMapper.deleteByExample(example);
        // 若院系下还有专业，禁止删除
        Example sp = new Example(Specialty.class);
        sp.createCriteria()
                .andEqualTo("departmentguid", rowguid);
        List<Specialty> list = new ArrayList<>();
        list = specialtyMapper.selectByExample(sp);
        // 判断是否含有专业
        if (list.size() > 0){
            return new ResultMap(ErrType.SPE_EXIST);
        }

        // 删除院系
        departmentMapper.deleteByPrimaryKey(rowguid);

        return new ResultMap(ErrType.SUCCESS);
    }

    @Transactional
    @Override
    public ResultMap deleteSchool(String rowguid) {
        logger.info("删除id为：" + rowguid + "的学校");

        // 若学校下还有院系，禁止删除
        Example de = new Example(Department.class);
        de.createCriteria()
                .andEqualTo("schoolguid", rowguid);
        List<Department> list = new ArrayList<>();
        list = departmentMapper.selectByExample(de);
        // 判断是否院系
        if (list.size() > 0){
            return new ResultMap(ErrType.DEPARTMENT_EXIST);
        }

        // 删除学校下所有院系
        Example example = new Example(Department.class);
        example.createCriteria()
                .andEqualTo("schoolguid", rowguid);
        departmentMapper.deleteByExample(example);

        // 删除学校
        schoolMapper.deleteByPrimaryKey(rowguid);

        return new ResultMap(ErrType.SUCCESS);
    }
}
