package com.tony.blog.exception;

public enum BusinessMsgEnum {
    PARAMETER_EXCEPTION("102", "参数为空"),
    SERVICE_TIME_OUT("103", "服务器超市等待"),
    PARAMETER_TOO_LARGE_EXCEPTION("104", "参数异常"),
    ALL_EXCEPTION("500", "系统异常");

    private String code;
    private String msg;

    BusinessMsgEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
