package com.base.domain.specialty.entity;

import javax.persistence.*;

@Table(name = "t_specialty")
public class Specialty {
    /**
     * 专业表主键
     */
    @Id
    private String rowguid;

    /**
     * 专业名称
     */
    private String specialty;

    /**
     * 系别主键
     */
    private String departmentguid;

    private String cdate;

    /**
     * 获取专业表主键
     *
     * @return rowguid - 专业表主键
     */
    public String getRowguid() {
        return rowguid;
    }

    /**
     * 设置专业表主键
     *
     * @param rowguid 专业表主键
     */
    public void setRowguid(String rowguid) {
        this.rowguid = rowguid == null ? null : rowguid.trim();
    }

    /**
     * 获取专业名称
     *
     * @return specialty - 专业名称
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * 设置专业名称
     *
     * @param specialty 专业名称
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty == null ? null : specialty.trim();
    }

    /**
     * 获取系别主键
     *
     * @return departmentguid - 系别主键
     */
    public String getDepartmentguid() {
        return departmentguid;
    }

    /**
     * 设置系别主键
     *
     * @param departmentguid 系别主键
     */
    public void setDepartmentguid(String departmentguid) {
        this.departmentguid = departmentguid == null ? null : departmentguid.trim();
    }

    /**
     * @return cdate
     */
    public String getCdate() {
        return cdate;
    }

    /**
     * @param cdate
     */
    public void setCdate(String cdate) {
        this.cdate = cdate == null ? null : cdate.trim();
    }
}