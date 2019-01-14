package com.base.domain.picconfig.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "picconfig")
public class PicConfig {

    @Id
    private String rowguid;

    private String picurl;
}
