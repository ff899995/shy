package com.base.domain.repair.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "t_repair")
public class Repair {

    @Id
    private String rowguid;
    // 申请报修人主键
    private String userguid;
    // 操作员主键
    private String cuserguid;
    // 报修标题
    private String repairname;
    // 报修内容
    private String repaircontent;
    // 报修说明图片
    private String repairimgurl;
    // 报修创建时间
    private String cdate;
    // 报修接受时间
    private String rdate;
    // 报修结束时间
    private String edate;
    // 报修状态：1.待处理，2.正在处理，3.完成
    private Integer statu;
    // 废弃原因or处理方法
    private String comment;
    // 是否为正常报修？1.正常报修，0.非正常报修
    private Integer success;
    // 报修地址
    private String position;
}
