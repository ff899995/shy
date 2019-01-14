package com.base.domain.school.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author fanl
 * @date 2018/11/17 14:12
 */
@Getter
@Setter
public class SchPageDto {

    @ApiModelProperty(name = "主键")
    private String rowguid;

    @ApiModelProperty("第几页")
    private Integer start;

    @ApiModelProperty("当前页数据大小")
    private Integer size;
}
