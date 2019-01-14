package com.base.domain.faq.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author fanl
 * @date 2018/9/19 20:45
 */
@Table(name = "error_mapping")
public class ErrorMapping {

    @Id
    private Integer id;

    @Column(name = "errorid")
    private Integer errorId;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "templateid")
    private Integer templateId;

    private String cdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getErrorId() {
        return errorId;
    }

    public void setErrorId(Integer errorId) {
        this.errorId = errorId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }
}
