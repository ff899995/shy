package com.base.service;


import com.base.domain.faq.dto.AddQuestionDto;
import com.base.domain.faq.dto.FaqPageDto;
import com.base.domain.faq.dto.PageDto;
import com.base.domain.faq.dto.UpdateQuestionDto;
import com.base.http.ResultMap;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.List;

public interface FaqService {

    ResultMap addQuestion(AddQuestionDto addQuestionDto);

    ResultMap pageFaqList(FaqPageDto pageDto) throws ParseException, IOException;

    ResultMap deleteQuestion(List<String> list);

    ResultMap findQuestionById(String rowguid);

    ResultMap updateQuestion(UpdateQuestionDto updateQuestionDto);

    ResultMap findErrorCode();
}
