package com.cui.java.study.web.spring.errorhandle.validation;

import lombok.Data;

/**
 * @author cuihanze
 * @param <T>
 */
@Data
public class Response<T> {
    private int code;
    private String msg;
    private T data;

    public Response() {
    }

    public Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Response success(int code, String msg, T data) {
        return new Response(code, msg, data);
    }
}
