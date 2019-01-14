package com.base.domain.faq.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateQuestionDto {

    @ApiModelProperty("问题id")
    private String rowguid;

    @ApiModelProperty("错误码")
    private String errorCode;

    @ApiModelProperty("问题")
    private String question;

    @ApiModelProperty("答复JSONArray")
    private List<AnswerDto> answer;

}
