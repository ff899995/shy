package com.base.controller;

import com.base.domain.faq.dto.PageDto;
import com.base.domain.republic.dto.RepublicAddDto;
import com.base.domain.republic.dto.RepublicUpdateDto;
import com.base.http.ResultMap;
import com.base.service.RepublicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "通知相关接口")
@RestController
@RequestMapping("/republic")
public class RepublicController {

    private static Logger logger = LoggerFactory.getLogger(RepublicController.class);

    @Autowired
    private RepublicService republicService;

    // 新增通知
    @ApiOperation(value = "添加通知", notes = "", consumes = "application/json")
    @PostMapping("/add")
    public ResultMap republicAdd(@RequestBody RepublicAddDto republicAddDto){
        logger.info("success into republicAddApi");

        return republicService.republicServiceAdd(republicAddDto);
    }

    // 批量删除通知
    @ApiOperation(value = "批量删除通知", notes = "", consumes = "application/json")
    @PostMapping("/delete")
    public ResultMap delete(@RequestBody List<String> list){
        logger.info("success into republicDeleteApi");

        return republicService.republicDelete(list);
    }

    // 通知修改
    @ApiOperation(value = "修改通知", notes = "", consumes = "application/json")
    @PostMapping("/update")
    public ResultMap update(@RequestBody RepublicUpdateDto republicUpdateDto){
        logger.info("success into republicUpdateApi");

        return republicService.republicUpdate(republicUpdateDto);
    }

    // 分页查询通知，按创建时间排序
    @ApiOperation(value = "分页查询", notes = "", consumes = "application/json")
    @RequestMapping(value = "/pageList",method = RequestMethod.POST)
    public ResultMap pageRepublicList(@RequestBody PageDto pageDto){
        logger.info("success into pageRepublicListApi");

        return republicService.pageRepublicList(pageDto);
    }

    // 查看通知详情
    @ApiOperation(value = "查看通知详情", notes = "", consumes = "application/json")
    @ApiImplicitParam(name = "rowguid", value = "通知主键", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/find/{rowguid}", method = RequestMethod.POST)
    public ResultMap find(@PathVariable("rowguid") String rowguid){
        logger.info("success into republicFindApi");

        return republicService.republicFind(rowguid);
    }
}
