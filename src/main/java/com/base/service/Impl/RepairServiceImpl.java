package com.base.service.Impl;

import com.alibaba.fastjson.JSON;
import com.base.domain.faq.dto.PageDto;
import com.base.domain.repair.dto.RepairAddDto;
import com.base.domain.repair.dto.RepairEndPageDto;
import com.base.domain.repair.entity.Repair;
import com.base.domain.repair.vo.EndRepairVo;
import com.base.domain.repair.vo.IngRepairVo;
import com.base.domain.repair.vo.NotreatRepairVo;
import com.base.domain.repair.vo.RepairDetailsVo;
import com.base.domain.user.entity.User;
import com.base.http.ErrType;
import com.base.http.PageVo;
import com.base.http.ResultMap;
import com.base.mapper.RepairMapper;
import com.base.mapper.RepublicMapper;
import com.base.mapper.UserMapper;
import com.base.service.RepairService;
import com.base.util.BaseUtils;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RepairServiceImpl implements RepairService {

    private static Logger logger = LoggerFactory.getLogger(RepairServiceImpl.class);

    @Autowired
    private RepairMapper repairMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultMap repairAdd(HttpServletRequest request, RepairAddDto repairAddDto) {
        logger.info(JSON.toJSONString(repairAddDto));
//        是否登录
        HttpSession session = request.getSession();
        if (session.getAttribute("rowguid") == null){
            return new ResultMap(ErrType.USER_NOLOGIN);
        }
        // 判断名称内容图片地址是否为空
        if (BaseUtils.TrimIsEmpty(repairAddDto.getRepairname()) || BaseUtils.TrimIsEmpty(repairAddDto.getRepaircontent()) || BaseUtils.TrimIsEmpty(repairAddDto.getRepairimgurl()) || BaseUtils.TrimIsEmpty(repairAddDto.getPosition())){
            return new ResultMap(ErrType.PARAM_NULL);
        }
        Repair repair = new Repair();
        repair.setRowguid(UUID.randomUUID().toString());
        repair.setUserguid((String) session.getAttribute("rowguid"));
        repair.setStatu(1);
        repair.setRepairname(BaseUtils.OverTrim(repairAddDto.getRepairname()));
        repair.setRepaircontent(repairAddDto.getRepaircontent());
        repair.setRepairimgurl(repairAddDto.getRepairimgurl());
        repair.setCdate(BaseUtils.dateCreate());
        repair.setPosition(repairAddDto.getPosition());
        repairMapper.insertSelective(repair);
        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
    public ResultMap notreatList(HttpServletRequest request,PageDto pageDto) {
        //是否登录
        HttpSession session = request.getSession();
        System.out.println("===>" + session.hashCode());
        if (session.getAttribute("rowguid") == null){
            return new ResultMap(ErrType.USER_NOLOGIN);
        }
        // 防止出现空指针现象
        try{
            pageDto.getStart();
            pageDto.getSize();
        } catch (Exception e){
            e.printStackTrace();
            // 若没给分页值，给定初始值
            pageDto.setSize(10);
            pageDto.setStart(10);
//            return new ResultMap(ErrType.PAGE_NULL);
        }
        // 判断当前用户权限
//        String userguid = "4f14849f-ae38-4329-9ed5-27acf133ae5a";
        String userguid = (String) session.getAttribute("rowguid");
        logger.info("用户id：" + userguid);
        User user = userMapper.selectByPrimaryKey(userguid);
        PageVo pageVo = new PageVo();
        int allCount = 0;
        Example example = new Example(Repair.class);
        List<Repair> list = new ArrayList<>();
        List<NotreatRepairVo> list1 = new ArrayList<>();
        if (user.getRoleNum() == 2){
            // 操作员查看所有待处理
            example.createCriteria()
                    .andEqualTo("statu",1);
            PageHelper.startPage(pageDto.getStart(), pageDto.getSize());
            list1 = repairMapper.notreatList(null);
        } else {
            // 普通用户只能查看自己的
            example.createCriteria()
                    .andEqualTo("statu",1)
                    .andEqualTo("userguid",userguid);
            PageHelper.startPage(pageDto.getStart(), pageDto.getSize());
            list1 = repairMapper.notreatList(userguid);
        }
        allCount = repairMapper.selectCountByExample(example);
        // 分页插件
        if (list1.size() > 0){
            for (int i = 0; i < list1.size(); i++) {
                list1.get(i).setOrderBy(i + 1);
            }
        }
        pageVo.setAllcount(allCount);
        pageVo.setPage(list1);
        return new ResultMap(ErrType.SUCCESS,pageVo);
    }

    @Override
    public ResultMap ingList(HttpServletRequest request, PageDto pageDto) {
        //是否登录
        HttpSession session = request.getSession();
        if (session.getAttribute("rowguid") == null){
            return new ResultMap(ErrType.USER_NOLOGIN);
        }
        // 防止出现空指针现象
        try{
            pageDto.getStart();
            pageDto.getSize();
        } catch (Exception e){
            // 若没给分页值，给定初始值
            pageDto.setSize(10);
            pageDto.setStart(10);
//            return new ResultMap(ErrType.PAGE_NULL);
        }
        // 判断当前用户权限
//        String userguid = "58df636e-1850-4195-85d5-783639c0d71a";
        String userguid = (String) session.getAttribute("rowguid");
        logger.info("用户id：" + userguid);
        User user = userMapper.selectByPrimaryKey(userguid);
        PageVo pageVo = new PageVo();
        int allCount = 0;
        Example example = new Example(Repair.class);
        List<IngRepairVo> list1 = new ArrayList<>();
        if (user.getRoleNum() == 2){
            // 操作员查看所有待处理
            example.createCriteria()
                    .andEqualTo("statu",2)
                    .andEqualTo("cuserguid", userguid);
            PageHelper.startPage(pageDto.getStart(), pageDto.getSize());
            list1 = repairMapper.ingList(null,userguid);
        } else {
            // 普通用户只能查看自己的
            example.createCriteria()
                    .andEqualTo("statu",2)
                    .andEqualTo("userguid",userguid);
            PageHelper.startPage(pageDto.getStart(), pageDto.getSize());
            list1 = repairMapper.ingList(userguid,null);
        }
        allCount = repairMapper.selectCountByExample(example);
        // 分页插件
        if (list1.size() > 0){
            for (int i = 0; i < list1.size(); i++) {
                list1.get(i).setOrderBy(i + 1);
            }
        }
        pageVo.setAllcount(allCount);
        pageVo.setPage(list1);
        return new ResultMap(ErrType.SUCCESS,pageVo);
    }

    @Override
    public ResultMap allow(HttpServletRequest request, String rowguid) {
        //是否登录
        HttpSession session = request.getSession();
        if (session.getAttribute("rowguid") == null){
            return new ResultMap(ErrType.USER_NOLOGIN);
        }
        // 判断用户权限
        String crowguid = (String) session.getAttribute("rowguid");
//        String crowguid = "4f14849f-ae38-4329-9ed5-27acf133ae5a";
        User user = userMapper.selectByPrimaryKey(crowguid);
        if (user.getRoleNum() != 2){
            return new ResultMap(ErrType.USER_ROLE_ERROR);
        }
        // 判断此报修是否还存在或者状态已经改变
        Repair repair = repairMapper.selectByPrimaryKey(rowguid);
        if (repair == null || repair.getStatu() != 1){
            return new ResultMap(ErrType.REPAIR_NOEXIST);
        }
        // 同意报修 入库
        repair.setCuserguid(crowguid);
        repair.setRdate(BaseUtils.dateCreate());
        repair.setStatu(2);
        repairMapper.updateByPrimaryKey(repair);
        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
    public ResultMap reject(HttpServletRequest request, String rowguid, String comment) {
        //是否登录
        HttpSession session = request.getSession();
        if (session.getAttribute("rowguid") == null){
            return new ResultMap(ErrType.USER_NOLOGIN);
        }
        // 判断用户权限
        String crowguid = (String) session.getAttribute("rowguid");
//        String crowguid = "4f14849f-ae38-4329-9ed5-27acf133ae5a";
        User user = userMapper.selectByPrimaryKey(crowguid);
        if (user.getRoleNum() != 2){
            return new ResultMap(ErrType.USER_ROLE_ERROR);
        }
        // 拒绝理由不能为空
        if (BaseUtils.TrimIsEmpty(comment)){
            return new ResultMap(ErrType.PARAM_NULL);
        }
        // 判断此报修是否还存在或者状态已经改变
        Repair repair = repairMapper.selectByPrimaryKey(rowguid);
        if (repair == null || repair.getStatu() != 1){
            return new ResultMap(ErrType.REPAIR_NOEXIST);
        }
        // 拒绝报修 入库
        repair.setCuserguid(crowguid);
        repair.setRdate(BaseUtils.dateCreate());
        repair.setStatu(3);
        repair.setSuccess(0);
        repair.setComment(comment);
        repairMapper.updateByPrimaryKey(repair);
        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
    public ResultMap endList(HttpServletRequest request, RepairEndPageDto pageDto) {
        //是否登录
        HttpSession session = request.getSession();
        if (session.getAttribute("rowguid") == null){
            return new ResultMap(ErrType.USER_NOLOGIN);
        }
        // 防止出现空指针现象
        try{
            pageDto.getStart();
            pageDto.getSize();
        } catch (Exception e){
            // 若没给分页值，给定初始值
            pageDto.setSize(10);
            pageDto.setStart(10);
//            return new ResultMap(ErrType.PAGE_NULL);
        }
        // 判断用户权限
        String rowguid = (String) session.getAttribute("rowguid");
//        String rowguid = "4f14849f-ae38-4329-9ed5-27acf133ae5a";
        User user = userMapper.selectByPrimaryKey(rowguid);
        // 若未传入success则置-1
        try {
            if (pageDto.getSuccess() == null){
                pageDto.setSuccess(-1);
            }
        } catch (Exception e){
            pageDto.setSuccess(-1);
        }
        int allCount = 0;
        Example example = new Example(Repair.class);
        List<EndRepairVo> list1 = new ArrayList<>();
        if (user.getRoleNum() == 2){
            // 操作员可以看到所有
            // 如果传入success字段，加入查询条件
            if (pageDto.getSuccess() != -1){
                example.createCriteria()
                        .andEqualTo("statu",3)
                        .andEqualTo("success",pageDto.getSuccess());
                allCount = repairMapper.selectCountByExample(example);
            } else {
                example.createCriteria()
                        .andEqualTo("statu",3);
                allCount = repairMapper.selectCountByExample(example);
            }
            PageHelper.startPage(pageDto.getStart(), pageDto.getSize());
            list1 = repairMapper.endList(null,pageDto.getSuccess());
        } else {
            // 普通用户只能看到自己的
            if (pageDto.getSuccess() != -1){
                example.createCriteria()
                        .andEqualTo("statu",3)
                        .andEqualTo("userguid",rowguid)
                        .andEqualTo("success",pageDto.getSuccess());
                allCount = repairMapper.selectCountByExample(example);
            } else {
                example.createCriteria()
                        .andEqualTo("userguid",rowguid)
                        .andEqualTo("statu",3);
                allCount = repairMapper.selectCountByExample(example);
            }
            PageHelper.startPage(pageDto.getStart(), pageDto.getSize());
            list1 = repairMapper.endList(rowguid,pageDto.getSuccess());
        }
        // 装填序号
        // 防止空指针
        if (list1.size() > 0){
            for (int i = 0; i < list1.size(); i++) {
                list1.get(i).setOrderBy(i + 1);
            }
        }
        PageVo pageVo = new PageVo();
        pageVo.setPage(list1);
        pageVo.setAllcount(allCount);

        return new ResultMap(ErrType.SUCCESS, pageVo);
    }

    @Override
    public ResultMap comment(HttpServletRequest request, String rowguid, String comment) {
        //是否登录
        HttpSession session = request.getSession();
        if (session.getAttribute("rowguid") == null){
            return new ResultMap(ErrType.USER_NOLOGIN);
        }
        // 判断用户权限
        String crowguid = (String) session.getAttribute("rowguid");
//        String crowguid = "4f14849f-ae38-4329-9ed5-27acf133ae5a";
        User user = userMapper.selectByPrimaryKey(crowguid);
        if (user.getRoleNum() != 2){
            return new ResultMap(ErrType.USER_ROLE_ERROR);
        }
        // 拒绝理由不能为空
        if (BaseUtils.TrimIsEmpty(comment)){
            return new ResultMap(ErrType.PARAM_NULL);
        }
        // 判断此报修是否还存在或者状态已经改变
        Repair repair = repairMapper.selectByPrimaryKey(rowguid);
        if (repair == null || repair.getStatu() == 3){
            return new ResultMap(ErrType.REPAIR_NOEXIST);
        }
        // 评论结果
        repair.setCuserguid(crowguid);
        repair.setRdate(BaseUtils.dateCreate());
        repair.setStatu(3);
        repair.setSuccess(1);
        repair.setComment(comment);
        repairMapper.updateByPrimaryKey(repair);
        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
    public ResultMap details(HttpServletRequest request, String rowguid) {
//        //是否登录
//        HttpSession session = request.getSession();
//        if (session.getAttribute("rowguid") == null){
//            return new ResultMap(ErrType.USER_NOLOGIN);
//        }
        // 是否存在
        Repair repair = repairMapper.selectByPrimaryKey(rowguid);
        if (repair == null){
            return new ResultMap(ErrType.REPAIR_NOEXIST);
        }
        // 详情信息
        RepairDetailsVo repairDetailsVo = repairMapper.repairDetails(rowguid);
        // 报修有无被接手
        if (repair.getCuserguid().equals("") || repair.getCuserguid().isEmpty()){
        } else {
            // 查询操作员信息
            User cuser = userMapper.selectByPrimaryKey(repair.getCuserguid());
            repairDetailsVo.setCname(cuser.getName());
            repairDetailsVo.setCphone(cuser.getPhone());
        }
        // 有无评论
        if (repair.getComment().equals("") || repair.getComment().isEmpty()){
        } else {
            repairDetailsVo.setComment(repair.getComment());
        }

        return new ResultMap(ErrType.SUCCESS,repairDetailsVo);
    }
}
