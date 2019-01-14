package com.base.domain.faq.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value="问题添加dto",description="添加问题所用对象")
public class AddQuestionDto {

    @ApiModelProperty(value="错误码",name="errorCode",required=true)
    private String errorCode;

    @ApiModelProperty(value="问题",name="question",required=true)
    private String question;

    @ApiModelProperty(value="答复JSONArray",name="answer")
    private List<AnswerDto> answer;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AnswerDto> getAnswer() {
        return answer;
    }

    public void setAnswer(List<AnswerDto> answer) {
        this.answer = answer;
    }
}
