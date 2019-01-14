package com.base.domain.repair.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EndRepairVo {

    private String rowguid;

    private String repairname;

    private String pname;

    private String cdate;

    private String edate;

    private String cname;

    private Integer success;

    private Integer orderBy;
}
