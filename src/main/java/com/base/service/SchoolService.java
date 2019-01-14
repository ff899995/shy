package com.base.service;

import com.base.domain.faq.dto.PageDto;
import com.base.domain.school.dto.SchPageDto;
import com.base.http.ResultMap;

public interface SchoolService {

    ResultMap schoolAdd(String schoolName);

    ResultMap allSchool(PageDto pageDto);

    ResultMap departmentAdd(String schoolguid, String department);

    ResultMap departmentList(SchPageDto schPageDto);

    ResultMap speAdd(String departmentguid, String specialty);

    ResultMap specialtyList(SchPageDto schPageDto);

    ResultMap updateSchool(String rowguid, String school);

    ResultMap updateDepartment(String rowguid, String department);

    ResultMap updateSpe(String rowguid, String specialty);

    ResultMap deleteSpe(String rowguid);

    ResultMap deleteDepartment(String rowguid);

    ResultMap deleteSchool(String rowguid);

}
