package com.tony.blog.utils;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class WebPageVo {
    private int code;
    private String msg;
    private long count;
    private Collection<?> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Collection<?> getData() {
        return data;
    }

    public void setData(Collection<?> data) {
        this.data = data;
    }
}

