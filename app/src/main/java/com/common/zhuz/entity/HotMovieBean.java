package com.common.zhuz.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhuzhen
 */

public class HotMovieBean implements Serializable {

    private int count;
    private int start;
    private int total;
    private String title;
    private List<MoviesBean> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MoviesBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<MoviesBean> subjects) {
        this.subjects = subjects;
    }
}
