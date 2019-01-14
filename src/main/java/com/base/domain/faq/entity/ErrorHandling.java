package com.base.domain.faq.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author fanl
 * @date 2018/9/19 20:44
 */
@Table(name = "t_faq")
@Getter
@Setter
public class ErrorHandling {

    @Id
    private String rowguid;

    @Column(name = "error_code")
    private String errorCode;

    private String question;

    private String answer;

    private String cdate;

    private String udate;

}
