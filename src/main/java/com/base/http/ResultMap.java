package com.base.http;


public class ResultMap<T> {
    private String code;
    private String msg;
    private T data;

    public ResultMap() {
        this.code = ErrType.SUCCESS.getErrcode();
        this.msg = ErrType.SUCCESS.getErrmsg();
    }

    public ResultMap(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultMap(ErrType errType) {
        this.code = errType.getErrcode();
        this.msg = errType.getErrmsg();
    }

    public ResultMap(ErrType errType, T data) {
        this.code = errType.getErrcode();
        this.msg = errType.getErrmsg();
        this.data = data;
    }
    
    public ResultMap(String code, String msg, T data) {
    	 this.code = code;
         this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static boolean isSuccess(ResultMap resultMap) {
        if (resultMap != null) {
            return resultMap.getCode().equals(ErrType.SUCCESS.getErrcode());
        }
        return false;
    }

    @Override
    public String toString() {
        return "ResultMap{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + (data==null?data:data.toString()) +
                '}';
    }
    
    public static void main(String[] args) {
		
	}
}
