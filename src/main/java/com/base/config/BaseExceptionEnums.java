package com.base.config;

import lombok.Getter;

@Getter
public enum BaseExceptionEnums {

    EXAMPLE("88888888", "例子"),

    LUCENE("-1","操作员还未添加索引库，暂时无法使用搜索框"),

    INTERNAL_ERROR("500", "系统内部异常"),
    ILLEGAL_PARAM("504", "请求参数不合法"),
    SQL_ERROR("508", "数据库操作异常,字段不合格或者主键冲突等!"),

    FILE_EMPTY("50001", "文件为空"),
    VIB_EMPTY("50002", "请求参数缺少"),
    FILE_UPLOAD_FAIL("50003", "文件上传失败"),
    MULTIPARTFILE_NORECIVE("50004", "当前请求不是multipart请求");


    private String code;
    private String msg;

    BaseExceptionEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}