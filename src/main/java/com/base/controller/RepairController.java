package com.base.controller;

import com.base.domain.faq.dto.PageDto;
import com.base.domain.repair.dto.RepairAddDto;
import com.base.domain.repair.dto.RepairEndPageDto;
import com.base.http.ResultMap;
import com.base.service.RepairService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(description = "维修管理相关接口")
@RestController
@RequestMapping("/repair")
public class RepairController {

    private static Logger logger = LoggerFactory.getLogger(RepairController.class);

    @Autowired
    private RepairService repairService;

    // 普通用户新增维修管理
    @ApiOperation(value = "普通用户新增报修", notes = "", consumes = "application/json")
    @PostMapping("/add")
    public ResultMap add(HttpServletRequest request, @RequestBody RepairAddDto repairAddDto){
        logger.info("success into repairAddApi");

        return repairService.repairAdd(request, repairAddDto);
    }

    // 待报修列表
    @ApiOperation(value = "待报修列表", notes = "", consumes = "application/json")
    @PostMapping("/notreatList")
    public ResultMap notreatList(HttpServletRequest request, @RequestBody PageDto pageDto){
        logger.info("success into notreatListApi");

        return repairService.notreatList(request, pageDto);
    }

    // 正在处理列表
    @ApiOperation(value = "正在处理列表", notes = "", consumes = "application/json")
    @PostMapping("/ingList")
    public ResultMap ingList(HttpServletRequest request, @RequestBody PageDto pageDto){
        logger.info("success into ingListApi");

        return repairService.ingList(request, pageDto);
    }

    // 完成列表（可以根据成功或者失败字段）
    @ApiOperation(value = "完成列表", notes = "", consumes = "application/json")
    @PostMapping("/endList")
    public ResultMap endList(HttpServletRequest request, @RequestBody RepairEndPageDto pageDto){
        logger.info("success into endListApi");

        return repairService.endList(request, pageDto);
    }

    // 操作员拒绝报修（弹窗输入理由）
    @ApiOperation(value = "操作员拒绝报修-弹窗输入理由", notes = "", consumes = "application/json")
    @ApiImplicitParam(name = "rowguid", value = "报修主键", required = true, dataType = "String", paramType = "path")
    @PostMapping("/reject/{rowguid}")
    public ResultMap reject(HttpServletRequest request, @PathVariable("rowguid") String rowguid, @RequestParam("comment") String comment){
        logger.info("success into rejectApi");

        return repairService.reject(request, rowguid, comment);
    }

    // 操作员同意报修（同意后，会出现在正在处理列表）
    @ApiOperation(value = "操作员同意报修", notes = "", consumes = "application/json")
    @ApiImplicitParam(name = "rowguid", value = "报修主键", required = true, dataType = "String", paramType = "path")
    @PostMapping("/allow/{rowguid}")
    public ResultMap allow(HttpServletRequest request, @PathVariable("rowguid") String rowguid){
        logger.info("success into allowApi");

        return repairService.allow(request, rowguid);
    }

    // 操作员处理输入内容
    @ApiOperation(value = "操作员输入结果", notes = "", consumes = "application/json")
    @ApiImplicitParam(name = "rowguid", value = "报修主键", required = true, dataType = "String", paramType = "path")
    @PostMapping("/comment/{rowguid}")
    public ResultMap comment(HttpServletRequest request, @PathVariable("rowguid") String rowguid, @RequestParam("comment") String comment){
        logger.info("success into commentApi");

        return repairService.comment(request, rowguid, comment);
    }

    // 查看详情接口
    @ApiOperation(value = "查看详情接口", notes = "", consumes = "application/json")
    @ApiImplicitParam(name = "rowguid", value = "报修主键", required = true, dataType = "String", paramType = "path")
    @PostMapping("/details/{rowguid}")
    public ResultMap details(HttpServletRequest request, @PathVariable("rowguid") String rowguid){
        logger.info("success into detailspi");

        return repairService.details(request, rowguid);
    }



}
