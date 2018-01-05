package com.common.zhuz.view;

import com.common.zhuz.base.BaseView;
import com.common.zhuz.entity.HotMovieBean;

/**
 * Created by zhuzhen
 */

public interface DoubanTopActivityView extends BaseView{

    void successCallBack(HotMovieBean entity);
    void failCallBack(String msg);
}
