package com.base.mapper;

import com.base.domain.school.entity.School;
import tk.mybatis.mapper.common.Mapper;

public interface SchoolMapper extends Mapper<School> {

    // 删除专业
    void deleteSpes(String rowguid);

    // 删除院系
    void deleteDeps(String rowguid);

}