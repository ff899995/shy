package com.base.domain.faq.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqPageDto {

    @ApiModelProperty("第几页")
    private Integer start;

    @ApiModelProperty("每页数据数量")
    private Integer size;

    @ApiModelProperty("搜索关键字")
    private String search;
}
