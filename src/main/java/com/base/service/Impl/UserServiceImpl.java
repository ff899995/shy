package com.base.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.domain.menu.entity.Menu;
import com.base.domain.menu.entity.MenuVo;
import com.base.domain.user.dto.*;
import com.base.domain.user.entity.User;
import com.base.domain.user.vo.M_UserDetailsVo;
import com.base.domain.user.vo.PageUserVo;
import com.base.domain.user.vo.UserDetailsVo;
import com.base.http.ErrType;
import com.base.http.PageVo;
import com.base.http.ResultMap;
import com.base.mapper.MenuMapper;
import com.base.mapper.PicUrlMapper;
import com.base.mapper.UserMapper;
import com.base.service.UserService;
import com.base.util.BaseUtils;
import com.base.util.DesEncryptDecrypt;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private PicUrlMapper picUrlMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public ResultMap register(RegisterDto registerDto) throws Exception {
        logger.info(JSON.toJSONString(registerDto));
        // 判断用户名合法性 由字母数字下划线组成且开头必须是字母，不能超过16位
        if (!BaseUtils.checkUsername(registerDto.getUsername())){
            return new ResultMap(ErrType.USERNAME_ERROR);
        }
        // 判断密码合法性 字母和数字构成，不能超过16位；
        if (!registerDto.getPassword().equals(registerDto.getPassword2())){
            return new ResultMap(ErrType.PASSWORD_Inequality);
        }
        if (!BaseUtils.checkPassword(registerDto.getPassword())){
            return new ResultMap(ErrType.PASSWORD_ERROR);
        }
        // 判断名称合法性 字母和数字构成长度大于6小于16；
        if (!BaseUtils.checkName(registerDto.getName())){
            return new ResultMap(ErrType.NAME_ERROR);
        }
        // 验证手机合法性
        if (!BaseUtils.checkCellphone(registerDto.getPhone())){
            return new ResultMap(ErrType.PHONE_ERROR);
        }

        // 学校院校专业不允许为空
        if (StringUtils.isEmpty(registerDto.getSchoolguid()) || StringUtils.isEmpty(registerDto.getDepartmentguid()) || StringUtils.isEmpty(registerDto.getSpecialtyguid())){
            return new ResultMap(ErrType.PARAM_NULL);
        }
        // 判断用户名称是否存在
        User user = new User();
        user.setUsername(registerDto.getUsername());
        if (userMapper.selectOne(user) != null){
            return new ResultMap(ErrType.USERNAME_EXIST);
        }
        // 加密密码
        String encryptedStr = DesEncryptDecrypt.getInstance().encrypt(registerDto.getPassword());
        // 存储角色表
        user.setUsername(registerDto.getUsername());
        user.setPassword(encryptedStr);
        user.setName(registerDto.getName());
        user.setPhone(registerDto.getPhone());
        user.setRowguid(UUID.randomUUID().toString());
        user.setSchoolguid(registerDto.getSchoolguid());
        user.setDepartmentguid(registerDto.getDepartmentguid());
        user.setSpecialtyguid(registerDto.getSpecialtyguid());
        user.setCdate(BaseUtils.dateCreate());
        user.setRoleName("普通用户");
        user.setRoleNum(1);
        user.setUserimgurl(picUrlMapper.selectByPrimaryKey("1").getPicurl());
        userMapper.insertSelective(user);
        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
    public ResultMap login(HttpServletRequest request, LoginDto loginDto) throws Exception {
        logger.info(JSON.toJSONString(loginDto));

        User user = new User();
        user.setUsername(loginDto.getUsername());
        user.setPassword(DesEncryptDecrypt.getInstance().encrypt(loginDto.getPassword()));
        // 查找此用户名密码数据库有无
        User isHave = userMapper.selectOne(user);
        if (isHave == null){
            return new ResultMap(ErrType.USERNAME_PASSWORD_ERROR);
        }
        // 将用户rowguid存入request中
        HttpSession session = request.getSession(true);
        // 30 * 60s 时间内无操作 session失效
        session.setMaxInactiveInterval(30*60);
        System.out.println("===>" + session.hashCode());
        session.setAttribute("rowguid", isHave.getRowguid());
        // 路由
        ErrType errType = null;
        // 判断用户权限
        if (isHave.getRoleNum() == 3){
            // 管理员登录
            errType = ErrType.M_USER;
        } else if (isHave.getRoleNum() == 2){
            // 操作员登录
            errType = ErrType.C_USER;
        } else {
            // 普通用户登录
            errType = ErrType.NORMAL_USER;
        }
        List<Menu> functionList = new ArrayList<>();
        List<Menu> functionList1 = new ArrayList<>();
        List<MenuVo> firstList = new ArrayList<>();
        Example first = new Example(Menu.class);
        first.createCriteria()
                .andLike("roleNum","%" + isHave.getRoleNum().toString() + "%")
                .andEqualTo("functionLevel", 1)
                .andEqualTo("parentId",0);
        first.setOrderByClause("function_order ASC");
        functionList = menuMapper.selectByExample(first);
        for (int i = 0; i < functionList.size(); i++) {
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(functionList.get(i),menuVo);
            // 取第一级别路由下的二级路由
            Example second = new Example(Menu.class);
            second.createCriteria()
                    .andLike("roleNum","%" + isHave.getRoleNum().toString() + "%")
                    .andEqualTo("functionLevel", 2)
                    .andEqualTo("parentId", menuVo.getId());
            second.setOrderByClause("function_order ASC");
            functionList1 = menuMapper.selectByExample(second);
            menuVo.setFunctionList(functionList1);
            firstList.add(menuVo);
        }
        JSONArray array= JSONArray.parseArray(JSON.toJSONString(firstList));
        JSONObject object = new JSONObject();
        object.put("functionList", array);
        return new ResultMap(errType,object);

    }

    @Override
    public ResultMap details(HttpServletRequest request) throws Exception {
        // 是否登录
        HttpSession session = request.getSession();
        if (session.getAttribute("rowguid") == null){
            return new ResultMap(ErrType.USER_NOLOGIN);
        }



        UserDetailsVo userDetailsVo = userMapper.details((String) session.getAttribute("rowguid"));
//        UserDetailsVo userDetailsVo = userMapper.details("58df636e-1850-4195-85d5-783639c0d71a");
        userDetailsVo.setPassword(DesEncryptDecrypt.getInstance().decrypt(userDetailsVo.getPassword()));
        return new ResultMap(ErrType.SUCCESS, userDetailsVo);
    }

    @Override
    public ResultMap modify(HttpServletRequest request, UserModifyDto userModifyDto) throws Exception {
        // 验证name是否合法
        if (BaseUtils.TrimIsEmpty(userModifyDto.getName())){
            return new ResultMap(ErrType.PARAM_NULL);
        }
        // 判断密码合法性 字母和数字构成，不能超过16位；
        if (!BaseUtils.checkPassword(userModifyDto.getPassword())){
            return new ResultMap(ErrType.PASSWORD_ERROR);
        }
        // 验证手机合法性
        if (!BaseUtils.checkCellphone(userModifyDto.getPhone())){
            return new ResultMap(ErrType.PHONE_ERROR);
        }

        // 学校院校专业不允许为空
        if (StringUtils.isEmpty(userModifyDto.getSchoolguid()) || StringUtils.isEmpty(userModifyDto.getDepartmentguid()) || StringUtils.isEmpty(userModifyDto.getSpecialtyguid())){
            return new ResultMap(ErrType.PARAM_NULL);
        }

        // 是否登录
        HttpSession session = request.getSession();
        if (session.getAttribute("rowguid") == null){
            return new ResultMap(ErrType.USER_NOLOGIN);
        }
        User user = userMapper.selectByPrimaryKey(session.getAttribute("rowguid"));
        user.setPassword(DesEncryptDecrypt.getInstance().encrypt(userModifyDto.getPassword()));
        user.setName(userModifyDto.getName());
        user.setPhone(userModifyDto.getPhone());
        user.setUserimgurl(userModifyDto.getFaceimgurl());
        user.setSchoolguid(userModifyDto.getSchoolguid());
        user.setDepartmentguid(userModifyDto.getDepartmentguid());
        user.setSpecialtyguid(userModifyDto.getSpecialtyguid());
        userMapper.updateByPrimaryKeySelective(user);

        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
    public ResultMap c_register(HttpServletRequest request,C_UserRegisterDto c_userRegisterDto) throws Exception {
        logger.info(JSON.toJSONString(c_userRegisterDto));
        // 是否登录
//        HttpSession session = request.getSession();
//        if (session.getAttribute("rowguid") == null){
//            return new ResultMap(ErrType.USER_NOLOGIN);
//        }
//        // 验证是否为管理员用户
//        User muser = userMapper.selectByPrimaryKey(session.getAttribute("rowguid"));
//        if (muser.getRoleNum() != 3){
//            return new ResultMap(ErrType.USER_ROLE_ERROR);
//        }
        // 用户名不允许重复
        User user = new User();
        user.setUsername(c_userRegisterDto.getUsername());
        if (userMapper.selectOne(user) != null){
            return new ResultMap(ErrType.USERNAME_EXIST);
        }
        // 判断用户名合法性 由字母数字下划线组成且开头必须是字母，不能超过16位
        if (!BaseUtils.checkUsername(c_userRegisterDto.getUsername())){
            return new ResultMap(ErrType.USERNAME_ERROR);
        }
        // 判断密码合法性 字母和数字构成，不能超过16位；
        if (!BaseUtils.checkPassword(c_userRegisterDto.getPassword())){
            return new ResultMap(ErrType.PASSWORD_ERROR);
        }
        // 判断名称合法性 字母和数字构成长度大于6小于16；
        if (!BaseUtils.checkName(c_userRegisterDto.getName())){
            return new ResultMap(ErrType.NAME_ERROR);
        }
        // 验证手机合法性
        if (!BaseUtils.checkCellphone(c_userRegisterDto.getPhone())){
            return new ResultMap(ErrType.PHONE_ERROR);
        }
        // 加密密码
        String encryptedStr = DesEncryptDecrypt.getInstance().encrypt(c_userRegisterDto.getPassword());
        // 用户信息如表
        user.setPassword(encryptedStr);
        user.setName(c_userRegisterDto.getName());
        user.setUserimgurl("/pic");
        user.setRoleNum(2);
        user.setRoleName("操作员");
        user.setPhone(c_userRegisterDto.getPhone());
        user.setCdate(BaseUtils.dateCreate());
        user.setRowguid(UUID.randomUUID().toString());
        user.setUserimgurl(picUrlMapper.selectByPrimaryKey("1").getPicurl());
        userMapper.insertSelective(user);
        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
    public ResultMap userList(HttpServletRequest request, M_PageUserDto pageDto) {
        logger.info(JSON.toJSONString(pageDto));
        // 防止出现空指针现象
        try{
            pageDto.getStart();
            pageDto.getSize();
        } catch (Exception e){
            e.printStackTrace();
            return new ResultMap(ErrType.PAGE_NULL);
        }
//        // 是否登录
//        HttpSession session = request.getSession();
//        if (session.getAttribute("rowguid") == null){
//            return new ResultMap(ErrType.USER_NOLOGIN);
//        }
//        // 验证是否为管理员用户
//        User muser = userMapper.selectByPrimaryKey(session.getAttribute("rowguid"));
//        if (muser.getRoleNum() != 3){
//            return new ResultMap(ErrType.USER_ROLE_ERROR);
//        }
        // 满足要求的数据数量
        Example example = new Example(User.class);
        // 是否根据权限id分页
        if (pageDto.getRoleNum().equals("")){
            // 查询所有
            example.createCriteria()
                    .andNotEqualTo("roleNum",3);
        } else {
            // 根据权限查询
            example.createCriteria()
                    .andEqualTo("roleNum",pageDto.getRoleNum());
        }
        // 按权限降序
        example.setOrderByClause("role_num desc");
        int allCount = userMapper.selectCountByExample(example);
        // 分页插件
        PageHelper.startPage(pageDto.getStart(), pageDto.getSize());
        List<User> list = userMapper.selectByExample(example);
        List<PageUserVo> list1 = new ArrayList<>();
        // 遍历存储到VoList中返回
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                PageUserVo pageUserVo = new PageUserVo();
                BeanUtils.copyProperties(list.get(i), pageUserVo);
                pageUserVo.setOrderBy(i+1);
                list1.add(pageUserVo);
            }
        }
        PageVo pageVo = new PageVo();
        pageVo.setPage(list1);
        pageVo.setAllcount(allCount);
        return new ResultMap(ErrType.SUCCESS,pageVo);
    }

    @Transactional
    @Override
    public ResultMap m_details(HttpServletRequest request, String rowguid) throws Exception {
        logger.info("管理员查看用户信息，id：" + rowguid);
        // 是否登录
//        HttpSession session = request.getSession();
//        if (session.getAttribute("rowguid") == null){
//            return new ResultMap(ErrType.USER_NOLOGIN);
//        }
//        // 验证是否为管理员用户
//        User muser = userMapper.selectByPrimaryKey(session.getAttribute("rowguid"));
//        if (muser.getRoleNum() != 3){
//            return new ResultMap(ErrType.USER_ROLE_ERROR);
//        }
        // 查看用户是否存在
        User isHave = userMapper.selectByPrimaryKey(rowguid);
        if (isHave == null){
            return new ResultMap(ErrType.USER_NOEXIST);
        }
        M_UserDetailsVo userDetailsVo = new M_UserDetailsVo();
        // 判断用户是操作员还是普通用户
        if (isHave.getRoleNum() == 2){
            // 用户为操作员
            // 复制
            BeanUtils.copyProperties(isHave,userDetailsVo);
            // 密码解密
            userDetailsVo.setPassword(DesEncryptDecrypt.getInstance().decrypt(isHave.getPassword()));
        } else if (isHave.getRoleNum() == 1){
            // 用户为普通用户
            userDetailsVo = userMapper.m_details_1(rowguid);
            // 密码解密
            userDetailsVo.setPassword(DesEncryptDecrypt.getInstance().decrypt(isHave.getPassword()));
        }
        return new ResultMap(ErrType.SUCCESS, userDetailsVo);
    }

    @Override
    public ResultMap m_modify(HttpServletRequest request, M_UserModifyDto m_userModifyDto) throws Exception {
        logger.info(JSON.toJSONString(m_userModifyDto));
        // 是否登录
//        HttpSession session = request.getSession();
//        if (session.getAttribute("rowguid") == null){
//            return new ResultMap(ErrType.USER_NOLOGIN);
//        }
//        // 验证是否为管理员用户
//        User muser = userMapper.selectByPrimaryKey(session.getAttribute("rowguid"));
//        if (muser.getRoleNum() != 3){
//            return new ResultMap(ErrType.USER_ROLE_ERROR);
//        }
        // 密码和手机格式是否正确
        // 判断密码合法性 字母和数字构成，不能超过16位；
        if (!BaseUtils.checkPassword(m_userModifyDto.getPassword())){
            return new ResultMap(ErrType.PASSWORD_ERROR);
        }
        // 验证手机合法性
        if (!BaseUtils.checkCellphone(m_userModifyDto.getPhone())){
            return new ResultMap(ErrType.PHONE_ERROR);
        }

        // 查看用户是否存在
        User isHave = userMapper.selectByPrimaryKey(m_userModifyDto.getRowguid());
        if (isHave == null){
            return new ResultMap(ErrType.USER_NOEXIST);
        }
        // 密码加密
        isHave.setPassword(DesEncryptDecrypt.getInstance().encrypt(m_userModifyDto.getPassword()));
        isHave.setPhone(m_userModifyDto.getPhone());
        // 更新入库
        userMapper.updateByPrimaryKeySelective(isHave);

        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
    @Transactional
    public ResultMap delete(HttpServletRequest request, List<String> list) {
        logger.info("删除用户");

        // 遍历删除
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                userMapper.deleteByPrimaryKey(list.get(i));
            }
        } else {
            return new ResultMap(ErrType.USER_NO_HOOK);
        }
        return new ResultMap(ErrType.SUCCESS);
    }
}
