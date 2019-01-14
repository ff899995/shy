package com.base.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyDto {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("用户头像地址")
    private String faceimgurl;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("学校主键")
    private String schoolguid;

    @ApiModelProperty("院系主键")
    private String departmentguid;

    @ApiModelProperty("专业主键")
    private String specialtyguid;
}
