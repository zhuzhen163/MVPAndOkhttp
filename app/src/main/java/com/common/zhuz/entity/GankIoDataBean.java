package com.common.zhuz.entity;

import java.io.Serializable;
import java.util.List;


public class GankIoDataBean implements Serializable {

    private boolean error;

    private List<ResultBean> results;

    public boolean isError() {
        return error;
    }

    public List<ResultBean> getResults() {
        return results;
    }
}
