package com.base.domain.school.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_school")
public class School {
    /**
     * 主键
     */
    @Id
    private String rowguid;

    /**
     * 学校名称
     */
    private String school;

    private String cdate;

    /**
     * 获取主键
     *
     * @return rowguid - 主键
     */
    public String getRowguid() {
        return rowguid;
    }

    /**
     * 设置主键
     *
     * @param rowguid 主键
     */
    public void setRowguid(String rowguid) {
        this.rowguid = rowguid == null ? null : rowguid.trim();
    }

    /**
     * 获取学校名称
     *
     * @return school - 学校名称
     */
    public String getSchool() {
        return school;
    }

    /**
     * 设置学校名称
     *
     * @param school 学校名称
     */
    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
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