package com.base.service;

import com.base.domain.faq.dto.PageDto;
import com.base.domain.repair.dto.RepairAddDto;
import com.base.domain.repair.dto.RepairEndPageDto;
import com.base.domain.repair.entity.Repair;
import com.base.http.ResultMap;

import javax.servlet.http.HttpServletRequest;

public interface RepairService {

    ResultMap repairAdd(HttpServletRequest request, RepairAddDto repairAddDto);

    ResultMap notreatList(HttpServletRequest request, PageDto pageDto);

    ResultMap ingList(HttpServletRequest request, PageDto pageDto);

    ResultMap allow(HttpServletRequest request, String rowguid);

    ResultMap reject(HttpServletRequest request, String rowguid, String comment);

    ResultMap endList(HttpServletRequest request, RepairEndPageDto pageDto);

    ResultMap comment(HttpServletRequest request, String rowguid, String comment);

    ResultMap details(HttpServletRequest request, String rowguid);
}
