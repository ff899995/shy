package com.base.service.Impl;

import com.alibaba.fastjson.JSON;
import com.base.domain.faq.dto.PageDto;
import com.base.domain.faq.entity.ErrorHandling;
import com.base.domain.republic.dto.RepublicAddDto;
import com.base.domain.republic.dto.RepublicUpdateDto;
import com.base.domain.republic.entity.Republic;
import com.base.http.ErrType;
import com.base.http.PageVo;
import com.base.http.ResultMap;
import com.base.mapper.RepublicMapper;
import com.base.service.RepublicService;
import com.base.util.BaseUtils;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.UUID;

@Service
public class RepublicServiceImpl implements RepublicService {

    private static Logger logger = LoggerFactory.getLogger(RepublicServiceImpl.class);

    @Autowired
    private RepublicMapper republicMapper;

    @Override
    public ResultMap republicServiceAdd(RepublicAddDto republicAddDto) {
        logger.info(JSON.toJSONString(republicAddDto));
        // 标题和内容不允许为空
        if (BaseUtils.TrimIsEmpty(republicAddDto.getRepublicName()) || BaseUtils.TrimIsEmpty(republicAddDto.getRepublicContent())){
            return new ResultMap(ErrType.PARAM_NULL);
        }
        // 公告信息入库
        Republic republic = new Republic();
        republic.setRowguid(UUID.randomUUID().toString());
        republic.setReadNum(0);
        republic.setUdate(BaseUtils.dateCreate());
        republic.setRepublicName(BaseUtils.OverTrim(republicAddDto.getRepublicName()));
        republic.setRepublicContent(republicAddDto.getRepublicContent());
        republicMapper.insertSelective(republic);

        return new ResultMap(ErrType.SUCCESS);
    }

    @Transactional
    @Override
    public ResultMap republicDelete(List<String> list) {
        logger.info("删除通知");

        // 防止空指针
        if (list.size() > 0){
            // 遍历删除
            for (int i = 0; i < list.size(); i++){
                republicMapper.deleteByPrimaryKey(list.get(i));
            }
        }
        return new ResultMap(ErrType.SUCCESS);
    }

    @Override
    public ResultMap republicUpdate(RepublicUpdateDto republicUpdateDto) {
        logger.info(JSON.toJSONString(republicUpdateDto));
        // 标题和内容不允许为空
        if (BaseUtils.TrimIsEmpty(republicUpdateDto.getRepublicName()) || BaseUtils.TrimIsEmpty(republicUpdateDto.getRepublicContent())){
            return new ResultMap(ErrType.PARAM_NULL);
        }
        // 验证通知是否还存在
        Republic republic = republicMapper.selectByPrimaryKey(republicUpdateDto.getRowguid());
        if (republic == null){
            return new ResultMap(ErrType.REPUBLIC_NOEXIST);
        }
        republic.setUdate(BaseUtils.dateCreate());
        republic.setRepublicName(BaseUtils.OverTrim(republicUpdateDto.getRepublicName()));
        republic.setRepublicContent(republicUpdateDto.getRepublicContent());
        republicMapper.updateByPrimaryKeySelective(republic);
        return new ResultMap(ErrType.SUCCESS);
    }

    @Transactional
    @Override
    public ResultMap republicFind(String rowguid) {
        logger.info("查看通知，id：" + rowguid);
        // 验证通知是否还存在
        Republic republic = republicMapper.selectByPrimaryKey(rowguid);
        if (republic == null){
            return new ResultMap(ErrType.REPUBLIC_NOEXIST);
        }
        republic.setReadNum(republic.getReadNum() + 1);
        republicMapper.updateByPrimaryKeySelective(republic);

        return new ResultMap(ErrType.SUCCESS,republic);
    }

    @Override
    public ResultMap pageRepublicList(PageDto pageDto) {
        logger.info("分页查询通知列表");
        // 防止出现空指针现象
        try{
            pageDto.getStart();
            pageDto.getSize();
        } catch (Exception e){
            e.printStackTrace();
            return new ResultMap(ErrType.PAGE_NULL);
        }
        // 满足要求的数据数量
        int allCount = republicMapper.selectAll().size();
        // 分页插件
        PageHelper.startPage(pageDto.getStart(), pageDto.getSize());
        // 通用mapper 按cdate降序
        Example example = new Example(Republic.class);
        example.setOrderByClause("udate DESC");
        List<Republic> list = republicMapper.selectByExample(example);

        PageVo pageVo = new PageVo();
        pageVo.setAllcount(allCount);
        pageVo.setPage(list);

        return new ResultMap(ErrType.SUCCESS, pageVo);
    }
}
