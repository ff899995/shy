package com.base.domain.repair.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairDetailsVo {

    // 报修主键
    private String rowguid;
    // 报修标题
    private String repairname;
    // 报修内容
    private String repaircontent;
    // 报修说明图片
    private String repairimgurl;
    // 报修时间
    private String cdate;
    // 报修人
    private String pname;
    // 学校
    private String school;
    // 院校
    private String department;
    // 专业
    private String specialty;
    // 地点
    private String position;
    // 报修人联系方式
    private String phone;
    // 处理人名称
    private String cname;
    // 处理人联系方式
    private String cphone;
    // 结果
    private String comment;
}
