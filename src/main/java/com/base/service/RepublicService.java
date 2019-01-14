package com.base.service;

import com.base.domain.faq.dto.PageDto;
import com.base.domain.republic.dto.RepublicAddDto;
import com.base.domain.republic.dto.RepublicUpdateDto;
import com.base.http.ResultMap;

import java.util.List;

public interface RepublicService {

    ResultMap republicServiceAdd(RepublicAddDto republicAddDto);

    ResultMap republicDelete(List<String> list);

    ResultMap republicUpdate(RepublicUpdateDto republicUpdateDto);

    ResultMap republicFind(String rowguid);

    ResultMap pageRepublicList(PageDto pageDto);
}
