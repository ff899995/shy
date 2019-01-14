package com.base.http;

public enum ErrType {

    NORMAL_USER("1", "欢迎普通用户登录"),
    C_USER("2", "欢迎操作员登录"),
    M_USER("3", "欢迎管理员登录"),

    SUCCESS("200","成功"),
    PAGE_PARAM_NULL("201","分页参数不允许空"),
    PARAM_NULL("202","参数可以传空字符串，不能传null"),
    NAME_REPEAT("203","该名称已经存在"),

    // 10000 School
    SCHOOL_NOEXIST("10001","学校已经不存在，请刷新"),
    DEPARTMENT_NOEXIST("10002","院系已经不存在，请刷新"),
    SPE_NOEXIST("10003","专业已经不存在，请刷新"),
    USER_NOLOGIN("10004", "用户未登录"),
    DEPARTMENT_EXIST("10005", "该学校下面还存在院系"),
    SPE_EXIST("10006", "该院系下面还存在专业"),
    SPE_USER_BIND("10007", "有学生绑定该专业，请提前通知学生更改信息后执行删除"),

    // 20000 republic
    REPUBLIC_NOEXIST("20001", "通知不存在，请刷新"),

    // 30000 user
    USERNAME_EXIST("30001", "用户名已存在"),
    USERNAME_PASSWORD_ERROR("30002", "用户名或密码错误"),
    USER_ROLE_ERROR("30003", "没有权限"),
    USER_NOEXIST("30004", "此用户已不存在，请刷新"),
    USER_NO_HOOK("30005", "未勾选"),
    PHONE_ERROR("30006", "手机号码格式错误"),
    USERNAME_ERROR("30007", "用户名格式错误"),
    PASSWORD_Inequality("30008", "两次密码不一致"),
    PASSWORD_ERROR("30009", "密码格式错误"),
    NAME_ERROR("30010", "名称格式错误"),


    // 40000 faq template vib

    ID_NOTEXISTED("40001","id不存在"),
    NAME_REPEATE("40002", "名称重复"),
    NO_TYPE("40003", "没有此型号"),
    PRO_NULL("40004", "参数不能为空"),
    PAGE_NULL("40005", "分页参数不能为空"),
    QUESTION_NOTEXIST("40006", "该问题已不存在，请刷新"),
    ERRORCODE_REPEAT("40007", "此errorCode已存在"),
    VIB_CNAME_REPEAT("40008", "名称不允许重复"),

    // 50000 repair
    REPAIR_NOEXIST("50001", "此报修已经不存在或者状态已经改变");


    String errcode;
    String errmsg;

    private ErrType(String errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }
    public String getErrcode() {
        return errcode;
    }
    public String getErrmsg() {
        return errmsg;
    }
}
