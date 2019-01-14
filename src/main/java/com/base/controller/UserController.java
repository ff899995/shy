package com.base.controller;

import com.base.domain.user.dto.*;
import com.base.http.ResultMap;
import com.base.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Api(description = "用户有关接口")
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // 用户注册
    @ApiOperation(value = "用户注册", notes = "注册的时候不用上传头像，后台会从systemconfig里默认给一个", consumes = "application/json")
    @PostMapping("/register")
    @ResponseBody
    public ResultMap register(@RequestBody RegisterDto registerDto) throws Exception {
        logger.info("success into registerApi");

        return userService.register(registerDto);
    }

    //用户登录
    @ApiOperation(value = "用户登录", notes = "", consumes = "application/json")
    @PostMapping("/login")
    @ResponseBody
    public ResultMap login(HttpServletRequest request, @RequestBody LoginDto loginDto) throws Exception {
        logger.info("success into loginApi");

        return userService.login(request, loginDto);
    }

    // 用户注销
    @ApiOperation(value = "用户注销", notes = "", consumes = "application/json")
    @PostMapping("/logout")
    public void logout(HttpServletRequest request){
        logger.info("success into logoutApi");

        HttpSession session = request.getSession();
        if (session != null){
            request.getSession().invalidate();
        }
    }

    // 查询个人信息
    @ApiOperation(value = "查询个人信息", notes = "", consumes = "appliction/json")
    @PostMapping("/details")
    @ResponseBody
    public ResultMap details(HttpServletRequest request) throws Exception {
        logger.info("success into userDetailsApi");

        return userService.details(request);
    }

    // 用户个人信息修改
    @ApiOperation(value = "用户个人信息修改", notes = "Dto中字段如果未修改就传递原值", consumes = "application/json")
    @PostMapping("/update")
    @ResponseBody
    public ResultMap updateUser(HttpServletRequest request, @RequestBody UserModifyDto userModifyDto) throws Exception {
        logger.info("success into updateUserApi");

        return userService.modify(request, userModifyDto);
    }

    // 管理员注册操作员账户
    @ApiOperation(value = "管理员注册操作员账户", notes = "", consumes = "application/json")
    @PostMapping("/c_register")
    @ResponseBody
    public ResultMap c_register(HttpServletRequest request, @RequestBody C_UserRegisterDto c_userRegisterDto) throws Exception {
        logger.info("success into c_registerApi");

        return userService.c_register(request, c_userRegisterDto);
    }

    // 管理员查询用户列表
    @ApiOperation(value = "管理员查看用户列表", notes = "", consumes = "application/json")
    @PostMapping("/list")
    @ResponseBody
    public ResultMap userList(HttpServletRequest request, @RequestBody M_PageUserDto pageDto){
        logger.info("success into userListApi");

        return userService.userList(request, pageDto);
    }

    // 管理员查看用户信息
    @ApiOperation(value = "管理员查看用户信息", notes = "头像、用户名、名称、学校信息、日期、权限名称、为灰色区 不允许修改   只允许修改密码 手机号", consumes = "application/json")
    @ApiImplicitParam(name = "rowguid", value = "用户主键", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/m_details/{rowguid}", method = RequestMethod.POST)
    @ResponseBody
    public ResultMap m_details(HttpServletRequest request, @PathVariable("rowguid") String rowguid) throws Exception {
        logger.info("success into m_detailsApi");

        return userService.m_details(request, rowguid);
    }

    // 管理员修改用户信息
    @ApiOperation(value = "管理员修改用户信息", notes = "", consumes = "application/json")
    @PostMapping("/m_modify")
    @ResponseBody
    public ResultMap m_modify(HttpServletRequest request, @RequestBody M_UserModifyDto m_userModifyDto) throws Exception {
        logger.info("success into userListApi");

        return userService.m_modify(request, m_userModifyDto);
    }


    // 管理员删除用户（可批量）
    @ApiOperation(value = "批量删除用户", notes = "", consumes = "application/json")
    @PostMapping("/delete")
    @ResponseBody
    public ResultMap delete(HttpServletRequest request, @RequestBody List<String> list){
        logger.info("success into deleteApi");

        return userService.delete(request, list);
    }
}
