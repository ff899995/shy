package com.base.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("第二个密码")
    private String password2;

    @ApiModelProperty("昵称")
    private String name;

//    @ApiModelProperty("图片地址")
//    private String faceimgurl;

    @ApiModelProperty("学校主键")
    private String schoolguid;

    @ApiModelProperty("院系主键")
    private String departmentguid;

    @ApiModelProperty("专业主键")
    private String specialtyguid;

    @ApiModelProperty("手机号")
    private String phone;
}
