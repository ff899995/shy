package com.base.controller;

import com.base.domain.faq.dto.PageDto;
import com.base.domain.school.dto.SchPageDto;
import com.base.http.ResultMap;
import com.base.service.SchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(description = "学校相关管理接口")
@RestController("/schManagement ")
public class SchoolController {

    private static final Logger logger = LoggerFactory.getLogger(SchoolController.class);

    @Autowired
    private SchoolService schoolService;

    // 创建学校
    @ApiOperation(value = "创建学校", notes = "")
    @PostMapping("/schoolAdd")
    public ResultMap schoolAdd(@RequestParam String school){
        logger.info("success into schoolAddApi");

        return schoolService.schoolAdd(school);
    }

    // 创建学院
    @ApiOperation(value = "创建院系", notes = "")
    @PostMapping("/depAdd")
    public ResultMap depAdd(@RequestParam("schoolguid") String schoolguid, @RequestParam("department") String department){
        logger.info("success into depAddApi");

        return schoolService.departmentAdd(schoolguid, department);
    }

    // 创建专业
    @ApiOperation(value = "创建专业", notes = "")
    @PostMapping("/speAdd")
    public ResultMap speAdd(@RequestParam("departmentguid") String departmentguid, @RequestParam("specialty") String specialty){
        logger.info("success into speAddApi");

        return schoolService.speAdd(departmentguid, specialty);
    }

    // 查询所有学校
    @ApiOperation(value = "查询所有学校", notes = "", consumes = "application/json")
    @PostMapping("/schoolList")
    public ResultMap schoolList(@RequestBody PageDto pageDto){
        logger.info("success into schoolListApi");

        return schoolService.allSchool(pageDto);
    }


    // 查询此学校下所有学院
    @ApiOperation(value = "查询此学校下所有学院", notes = "")
    @PostMapping("/departmentList")
    public ResultMap departmentList(@RequestBody SchPageDto schPageDto){
        logger.info("success into departmentListApi");

        return schoolService.departmentList(schPageDto);
    }

    // 查询此学院下所有专业
    @ApiOperation(value = "查询此学院下所有专业", notes = "")
    @PostMapping("/specialtyList")
    public ResultMap specialtyList(@RequestBody SchPageDto schPageDto){
        logger.info("success into specialtyListApi");

        return schoolService.specialtyList(schPageDto);
    }

    // 修改学校名称
    @ApiOperation(value = "修改学校名称", notes = "")
    @PostMapping("/updateSchool")
    public ResultMap updateSchool(@RequestParam("rowguid") String rowguid, @RequestParam("school") String school){
        logger.info("success into updateSchoolApi");

        return schoolService.updateSchool(rowguid, school);
    }

    // 修改学院名称
    @ApiOperation(value = "修改院系名称", notes = "")
    @PostMapping("/updateDepartment")
    public ResultMap updateDepartment(@RequestParam("rowguid") String rowguid, @RequestParam("department") String department){
        logger.info("success into updateDepartmentApi");

        return schoolService.updateDepartment(rowguid, department);
    }

    // 修改专业名称
    @ApiOperation(value = "修改专业名称", notes = "")
    @PostMapping("/updateSpe")
    public ResultMap updateSpe(@RequestParam("rowguid") String rowguid, @RequestParam("specialty") String specialty){
        logger.info("success into updateSpeApi");

        return schoolService.updateSpe(rowguid, specialty);
    }

    // 删除学校
    @ApiOperation(value = "删除学校", notes = "")
    @PostMapping("/deleteSchool")
    public ResultMap deleteSchool(@RequestParam("rowguid") String rowguid){
        logger.info("success into deleteSchoolApi");

        return schoolService.deleteSchool(rowguid);
    }

    // 删除学院
    @ApiOperation(value = "删除学院", notes = "")
    @PostMapping("/deleteDepartment")
    public ResultMap deleteDepartment(@RequestParam("rowguid") String rowguid){
        logger.info("success into deleteDepartmentApi");

        return schoolService.deleteDepartment(rowguid);
    }

    // 删除专业
    @ApiOperation(value = "删除专业", notes = "")
    @PostMapping("/deleteSpe")
    public ResultMap deleteSpe(@RequestParam("rowguid") String rowguid){
        logger.info("success into deleteSpeApi");

        return schoolService.deleteSpe(rowguid);
    }
}
