package com.common.zhuz.entity;

import java.util.List;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class ResultBean {
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public boolean isUsed() {
        return used;
    }

    public String getWho() {
        return who;
    }

    @Override
    public String toString() {
        return "ResultsBean{" +
                "who='" + who + '\'' +
                ", used=" + used +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", source='" + source + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", desc='" + desc + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }

    public List<String> getImages() {
        return images;
    }

}
