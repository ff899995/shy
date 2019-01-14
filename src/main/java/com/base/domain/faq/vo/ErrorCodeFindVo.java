package com.base.domain.faq.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author fanl
 * @date 2018/9/22 16:17
 */
@Getter
@Setter
public class ErrorCodeFindVo {

    @JsonProperty("errorId")
    private String rowguid;

    private String errorCode;

}
