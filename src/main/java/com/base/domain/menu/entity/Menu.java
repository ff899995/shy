package com.base.domain.menu.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "menu")
@Getter
@Setter
public class Menu {

    @ApiModelProperty("路由id")
    @Id
    private Integer id;

    @ApiModelProperty("父路由id")
    @Column(name = "parent_id")
    private Integer parentId;

    @ApiModelProperty("名称")
    @Column(name = "function_name")
    private String functionName;

    @ApiModelProperty("角色权限id，1.普通用户，2.操作员，3.管理员")
    @Column(name = "role_num")
    private String roleNum;

    @ApiModelProperty("路由地址")
    @Column(name = "function_html_url")
    private String functionHtmlUrl;

    @ApiModelProperty("接口地址")
    @Column(name = "function_url")
    private String functionUrl;

    @ApiModelProperty("路由级别")
    @Column(name = "function_level")
    private Integer functionLevel;

    @ApiModelProperty("顺序")
    @Column(name = "function_order")
    private Integer functionOrder;
}
