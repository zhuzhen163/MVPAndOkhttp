package com.common.zhuz.entity;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class RaidersEntity {
    private String id;
    private String position;
    private String platform_product_id;
    private String platform_id;
    private String platform_product_name;
    private String product_introduct;
    private String product_logo;
    private String title;
    private String cover_img;
    private int sign;
    private String create_time;
    private String footer_img_h5_link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getFooter_img_h5_link() {
        return footer_img_h5_link;
    }

    public void setFooter_img_h5_link(String footer_img_h5_link) {
        this.footer_img_h5_link = footer_img_h5_link;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPlatform_product_id() {
        return platform_product_id;
    }

    public void setPlatform_product_id(String platform_product_id) {
        this.platform_product_id = platform_product_id;
    }

    public String getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(String platform_id) {
        this.platform_id = platform_id;
    }

    public String getPlatform_product_name() {
        return platform_product_name;
    }

    public void setPlatform_product_name(String platform_product_name) {
        this.platform_product_name = platform_product_name;
    }

    public String getProduct_introduct() {
        return product_introduct;
    }

    public void setProduct_introduct(String product_introduct) {
        this.product_introduct = product_introduct;
    }

    public String getProduct_logo() {
        return product_logo;
    }

    public void setProduct_logo(String product_logo) {
        this.product_logo = product_logo;
    }
}
