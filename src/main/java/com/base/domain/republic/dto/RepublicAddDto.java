package com.base.domain.republic.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepublicAddDto {

    @ApiModelProperty("公告名称")
    private String republicName;

    @ApiModelProperty("公告内容")
    private String republicContent;
}
