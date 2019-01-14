package com.base.controller;

import com.base.domain.faq.dto.AddQuestionDto;
import com.base.domain.faq.dto.FaqPageDto;
import com.base.domain.faq.dto.PageDto;
import com.base.domain.faq.dto.UpdateQuestionDto;
import com.base.http.ResultMap;
import com.base.service.FaqService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Api(description = "FAQ管理接口")
@RestController
@RequestMapping("/faq")
public class FaqController {

    private static final Logger logger = LoggerFactory.getLogger(FaqController.class);

    @Autowired
    private FaqService faqService;

    // 1.添加问题
    @ApiOperation(value = "添加问题", notes = "", consumes = "application/json")
    @RequestMapping(value = "/addQuestion",method = RequestMethod.POST)
    public ResultMap addQuestion(@RequestBody AddQuestionDto addQuestionDto){
        logger.info("success into addQuestion");

        return faqService.addQuestion(addQuestionDto);
    }

    // 2.分页查询所有问题，按创建时间排序
    @ApiOperation(value = "分页查询", notes = "", consumes = "application/json")
    @RequestMapping(value = "/pageFaqList",method = RequestMethod.POST)
    public ResultMap pageFaqList(@RequestBody FaqPageDto pageDto) throws IOException, ParseException {
        logger.info("success into pageFaqList");

        return faqService.pageFaqList(pageDto);
    }

    // 3.批量删除
    @ApiOperation(value = "批量删除问题", notes = "", consumes = "application/json")
    @RequestMapping(value = "/deleteQuestion",method = RequestMethod.POST)
    public ResultMap deleteQuestion(@RequestBody List<String> list){
        logger.info("success into deleteQuestion");

        return faqService.deleteQuestion(list);
    }

    // 4.查看问题详情
    @ApiOperation(value = "查看问题详情", notes = "", consumes = "application/json")
    @ApiImplicitParam(name = "id", value = "问题id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/findQuestionById/{id}",method = RequestMethod.POST)
    public ResultMap findQuestionById(@PathVariable("id") String id){
        logger.info("success into findQuestionById");

        return faqService.findQuestionById(id);
    }

    // 5.修改问题
    @ApiOperation(value = "修改问题", notes = "", consumes = "application/json")
    @RequestMapping(value = "/updateQuestion",method = RequestMethod.POST)
    public ResultMap updateQuestion(@RequestBody UpdateQuestionDto updateQuestionDto){
        logger.info("success into updateQuestion");

        return faqService.updateQuestion(updateQuestionDto);
    }

    // 6.查询所有的error_code
    @ApiOperation(value = "查询所有的erroCode", notes = "")
    @RequestMapping(value = "/findErrorCode",method = RequestMethod.GET)
    public ResultMap findErrorCode(){
        logger.info("success into findErrorCode");

        return faqService.findErrorCode();
    }
}
