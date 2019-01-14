package com.base.domain.republic.entity;

import javax.persistence.*;

@Table(name = "t_republic")
public class Republic {
    @Id
    private String rowguid;

    /**
     * 公告名称
     */
    @Column(name = "republic_name")
    private String republicName;

    private String udate;

    /**
     * 阅读数
     */
    @Column(name = "read_num")
    private Integer readNum;

    /**
     * 公告内容
     */
    @Column(name = "republic_content")
    private String republicContent;

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
     * 获取公告名称
     *
     * @return republic_name - 公告名称
     */
    public String getRepublicName() {
        return republicName;
    }

    /**
     * 设置公告名称
     *
     * @param republicName 公告名称
     */
    public void setRepublicName(String republicName) {
        this.republicName = republicName == null ? null : republicName.trim();
    }

    /**
     * @return udate
     */
    public String getUdate() {
        return udate;
    }

    /**
     * @param udate
     */
    public void setUdate(String udate) {
        this.udate = udate == null ? null : udate.trim();
    }

    /**
     * 获取阅读数
     *
     * @return read_num - 阅读数
     */
    public Integer getReadNum() {
        return readNum;
    }

    /**
     * 设置阅读数
     *
     * @param readNum 阅读数
     */
    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    /**
     * 获取公告内容
     *
     * @return republic_content - 公告内容
     */
    public String getRepublicContent() {
        return republicContent;
    }

    /**
     * 设置公告内容
     *
     * @param republicContent 公告内容
     */
    public void setRepublicContent(String republicContent) {
        this.republicContent = republicContent == null ? null : republicContent.trim();
    }
}