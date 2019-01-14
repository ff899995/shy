package com.base.domain.user.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_user")
public class User {
    /**
     * 主键-唯一
     */
    @Id
    private String rowguid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像地址
     */
    private String userimgurl;

    /**
     * 角色权限：1.普通用户，2.操作员，3.管理员
     */
    @Column(name = "role_num")
    private Integer roleNum;

    /**
     * 角色权限名称：普通用户，操作员，管理员
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 名称
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 学校名称
     */
    private String schoolguid;

    /**
     * 系别
     */
    private String departmentguid;

    /**
     * 专业
     */
    private String specialtyguid;

    private String cdate;

    /**
     * 获取主键-唯一
     *
     * @return rowguid - 主键-唯一
     */
    public String getRowguid() {
        return rowguid;
    }

    /**
     * 设置主键-唯一
     *
     * @param rowguid 主键-唯一
     */
    public void setRowguid(String rowguid) {
        this.rowguid = rowguid == null ? null : rowguid.trim();
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取头像地址
     *
     * @return userimgurl - 头像地址
     */
    public String getUserimgurl() {
        return userimgurl;
    }

    /**
     * 设置头像地址
     *
     * @param userimgurl 头像地址
     */
    public void setUserimgurl(String userimgurl) {
        this.userimgurl = userimgurl == null ? null : userimgurl.trim();
    }

    /**
     * 获取角色权限：1.普通用户，2.操作员，3.管理员
     *
     * @return role_num - 角色权限：1.普通用户，2.操作员，3.管理员
     */
    public Integer getRoleNum() {
        return roleNum;
    }

    /**
     * 设置角色权限：1.普通用户，2.操作员，3.管理员
     *
     * @param roleNum 角色权限：1.普通用户，2.操作员，3.管理员
     */
    public void setRoleNum(Integer roleNum) {
        this.roleNum = roleNum;
    }

    /**
     * 获取角色权限名称：普通用户，操作员，管理员
     *
     * @return role_name - 角色权限名称：普通用户，操作员，管理员
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色权限名称：普通用户，操作员，管理员
     *
     * @param roleName 角色权限名称：普通用户，操作员，管理员
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取手机号
     *
     * @return phone - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取学校名称
     *
     * @return schoolguid - 学校名称
     */
    public String getSchoolguid() {
        return schoolguid;
    }

    /**
     * 设置学校名称
     *
     * @param schoolguid 学校名称
     */
    public void setSchoolguid(String schoolguid) {
        this.schoolguid = schoolguid == null ? null : schoolguid.trim();
    }

    /**
     * 获取系别
     *
     * @return departmentguid - 系别
     */
    public String getDepartmentguid() {
        return departmentguid;
    }

    /**
     * 设置系别
     *
     * @param departmentguid 系别
     */
    public void setDepartmentguid(String departmentguid) {
        this.departmentguid = departmentguid == null ? null : departmentguid.trim();
    }

    /**
     * 获取专业
     *
     * @return specialtyguid - 专业
     */
    public String getSpecialtyguid() {
        return specialtyguid;
    }

    /**
     * 设置专业
     *
     * @param specialtyguid 专业
     */
    public void setSpecialtyguid(String specialtyguid) {
        this.specialtyguid = specialtyguid == null ? null : specialtyguid.trim();
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