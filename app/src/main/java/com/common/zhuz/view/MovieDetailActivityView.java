package com.common.zhuz.view;

import com.common.zhuz.base.BaseView;
import com.common.zhuz.entity.MovieDetailBean;

/**
 * Created by zhuzhen
 */

public interface MovieDetailActivityView extends BaseView{

    void successCallBack(MovieDetailBean entity);
    void failCallBack(String msg);
}
