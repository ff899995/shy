package com.base.config;

public class BaseException {

    public static void throwSystemException(BaseExceptionEnums zencloudExceptionEnums) {
        throw new SystemReturnException(zencloudExceptionEnums.getCode(), zencloudExceptionEnums.getMsg());
    }
}