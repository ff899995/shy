package com.base.domain.repair.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairAddDto {

//    @ApiModelProperty("普通用户主键 - 此字段仅测试开启")
//    private String userguid;

    @ApiModelProperty("报修名称-必填")
    private String repairname;

    @ApiModelProperty("报修内容-必填")
    private String repaircontent;

    @ApiModelProperty("报修说明图片-必填")
    private String repairimgurl;

    @ApiModelProperty("地址")
    private String position;
}
