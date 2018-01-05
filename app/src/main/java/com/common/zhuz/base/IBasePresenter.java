package com.common.zhuz.base;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public interface IBasePresenter<V extends BaseView> {
    /**
     * @param view 绑定
     */
    void attachView(V view);
    /**
     * 防止内存的泄漏,清除presenter与activity之间的绑定
     */
    void detachView();
    /**
     * @return 获取View
     */
    BaseView getView();
}
