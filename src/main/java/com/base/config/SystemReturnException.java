package com.base.config;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SystemReturnException extends RuntimeException {
    private String code;
    private String msg;

    public SystemReturnException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}