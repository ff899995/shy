package com.base.domain.faq.dto;

import io.swagger.annotations.ApiModelProperty;

public class PageDto {

    @ApiModelProperty("第几页")
    private Integer start;

    @ApiModelProperty("每页数据数量")
    private Integer size;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
