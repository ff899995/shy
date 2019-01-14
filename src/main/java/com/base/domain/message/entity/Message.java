package com.base.domain.message.entity;

import javax.persistence.*;

@Table(name = "t_message")
public class Message {
    @Id
    private String rowguid;

    /**
     * 用户主键
     */
    private String userguid;

    /**
     * 创建时间
     */
    private String cdate;

    /**
     * 留言
     */
    private String message;

    /**
     * @return rowguid
     */
    public String getRowguid() {
        return rowguid;
    }

    /**
     * @param rowguid
     */
    public void setRowguid(String rowguid) {
        this.rowguid = rowguid == null ? null : rowguid.trim();
    }

    /**
     * 获取用户主键
     *
     * @return userguid - 用户主键
     */
    public String getUserguid() {
        return userguid;
    }

    /**
     * 设置用户主键
     *
     * @param userguid 用户主键
     */
    public void setUserguid(String userguid) {
        this.userguid = userguid == null ? null : userguid.trim();
    }

    /**
     * 获取创建时间
     *
     * @return cdate - 创建时间
     */
    public String getCdate() {
        return cdate;
    }

    /**
     * 设置创建时间
     *
     * @param cdate 创建时间
     */
    public void setCdate(String cdate) {
        this.cdate = cdate == null ? null : cdate.trim();
    }

    /**
     * 获取留言
     *
     * @return message - 留言
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置留言
     *
     * @param message 留言
     */
    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }
}