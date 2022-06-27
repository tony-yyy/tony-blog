package com.tony.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
public class ResultInfo<T> implements Serializable {
    private T data;
    private Boolean error = false;
    private String errorInfo = null;

    public ResultInfo() {
    }

    public ResultInfo(T data, Boolean error, String errorInfo) {
        this.data = data;
        this.error = error;
        this.errorInfo = errorInfo;
    }

    public ResultInfo(T data) {
        this.data = data;
    }

    public ResultInfo(Boolean error, String errorInfo) {
        this.error = error;
        this.errorInfo = errorInfo;
    }
}
