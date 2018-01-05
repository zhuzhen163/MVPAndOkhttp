package com.common.zhuz.entity;

import java.util.ArrayList;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class RaidersDataEntity {
    private ArrayList<RaidersEntity> data;
    private int pageCount;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public ArrayList<RaidersEntity> getData() {
        return data;
    }

    public void setData(ArrayList<RaidersEntity> data) {
        this.data = data;
    }
}
