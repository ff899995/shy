package com.base.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class M_PageUserDto {

    @ApiModelProperty("第几页")
    private Integer start;

    @ApiModelProperty("每页数据数量")
    private Integer size;

    @ApiModelProperty("权限id，1.普通用户，2.操作员，默认为空字符串")
    private String roleNum;
}
