package com.common.zhuz.exception;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class ApiException extends RuntimeException{
    public int code;
    public String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
