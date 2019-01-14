package com.base.config;


import com.base.http.ResultMap;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler
    @ResponseBody
    public ResultMap exceptionHandler(HttpServletRequest request, HttpServletResponse httpResponse, Exception ex) {
        ResultMap retBody = new ResultMap();
        ex.printStackTrace();

        try {
            System.out.println("异常：" + ex);
            if (ex instanceof SystemReturnException) {
                SystemReturnException systemReturnException = (SystemReturnException) ex;
                retBody.setMsg(systemReturnException.getMessage());
                retBody.setCode(systemReturnException.getCode());
                return retBody;
            } else if (ex instanceof MethodArgumentNotValidException) {
                retBody.setCode(BaseExceptionEnums.ILLEGAL_PARAM.getCode());
                String message = ex.getMessage();
                int start = message.lastIndexOf("default message");
                message = message.substring(start, message.length()).replace("default message", "").replace("]]", "]");
                MethodParameter methodParameter = ((MethodArgumentNotValidException)ex).getParameter();
                retBody.setMsg(BaseExceptionEnums.ILLEGAL_PARAM.getMsg() + message);
                return retBody;
            } else if (ex instanceof DataAccessException) {
                retBody.setMsg(BaseExceptionEnums.SQL_ERROR.getMsg());
                retBody.setCode(BaseExceptionEnums.SQL_ERROR.getCode());
                return retBody;
            } else if (ex instanceof org.apache.lucene.index.IndexNotFoundException){
                retBody.setMsg(BaseExceptionEnums.LUCENE.getMsg());
                retBody.setCode(BaseExceptionEnums.LUCENE.getCode());
                return retBody;
            } else {
                retBody.setMsg(BaseExceptionEnums.INTERNAL_ERROR.getMsg());
                retBody.setCode(BaseExceptionEnums.INTERNAL_ERROR.getCode());
                return retBody;
            }
        } catch (Exception var8) {
            retBody.setMsg(BaseExceptionEnums.INTERNAL_ERROR.getMsg());
            retBody.setCode(BaseExceptionEnums.INTERNAL_ERROR.getCode());
            return retBody;
        }
    }

}