package com.base.domain.department.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_department")
public class Department {
    /**
     * 系别表主键
     */
    @Id
    private String rowguid;

    /**
     * 系别名称
     */
    private String department;

    /**
     * 学校主键
     */
    private String schoolguid;

    private String cdate;

    /**
     * 获取系别表主键
     *
     * @return rowguid - 系别表主键
     */
    public String getRowguid() {
        return rowguid;
    }

    /**
     * 设置系别表主键
     *
     * @param rowguid 系别表主键
     */
    public void setRowguid(String rowguid) {
        this.rowguid = rowguid == null ? null : rowguid.trim();
    }

    /**
     * 获取系别名称
     *
     * @return department - 系别名称
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 设置系别名称
     *
     * @param department 系别名称
     */
    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    /**
     * 获取学校主键
     *
     * @return schoolguid - 学校主键
     */
    public String getSchoolguid() {
        return schoolguid;
    }

    /**
     * 设置学校主键
     *
     * @param schoolguid 学校主键
     */
    public void setSchoolguid(String schoolguid) {
        this.schoolguid = schoolguid == null ? null : schoolguid.trim();
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
        this.cdate = cdate;
    }
}