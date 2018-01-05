package com.common.zhuz.base;

/**
 * Created by zhuzhen on 2017/11/2.
 * MVP==========View对应activity
 * 基类BaseView每个activity 都应该应该有友好的交互 显示与隐藏loading框
 */

public interface BaseView {
    void showLoading();
    void hideLoading();
}
