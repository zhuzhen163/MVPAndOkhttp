package com.common.zhuz.base;

import com.common.zhuz.entity.ResultBean;

import java.util.List;

/**
 * Created by zhuzhen
 * 公共返回数据格式
 * 泛型（T）
 * 标准数据格式
 * {code：200，message："失败或者错误"，data：{具体数据}}
 */

public class BaseHttpResult<T> {
    private int code;
    private String message;
    private T data;
    private boolean error;

    private List<ResultBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultBean> getResults() {
        return results;
    }

    public void setResults(List<ResultBean> results) {
        this.results = results;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseHttpResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
