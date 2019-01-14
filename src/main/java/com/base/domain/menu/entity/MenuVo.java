package com.base.domain.menu.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
public class MenuVo {

    private Integer id;

    private Integer parentId;

    private String functionName;

    private String roleNum;

    private String functionHtmlUrl;

    private String functionUrl;

    private Integer functionLevel;

    private Integer functionOrder;

    private List<Menu> functionList;
}
