package com.base.domain.faq.vo;


import com.alibaba.fastjson.JSONArray;
import lombok.Getter;
import lombok.Setter;

/**
 * @author fanl
 * @date 2018/9/19 23:14
 */
@Getter
@Setter
public class QuestionDetailsVo {

    private String rowguid;

    private String errorCode;

    private String question;

    private JSONArray answer;

}
