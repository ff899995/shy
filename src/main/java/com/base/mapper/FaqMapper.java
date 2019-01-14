package com.base.mapper;

import com.base.domain.faq.entity.ErrorHandling;
import com.base.domain.faq.vo.PageFaqListVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FaqMapper extends Mapper<ErrorHandling> {

    List<PageFaqListVo> selectFaqByIds(@Param("list") List<String> list);
}