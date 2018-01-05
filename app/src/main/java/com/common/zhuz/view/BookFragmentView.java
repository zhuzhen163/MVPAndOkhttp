package com.common.zhuz.view;

import com.common.zhuz.base.BaseView;
import com.common.zhuz.entity.BookBean;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public interface BookFragmentView extends BaseView{

    void successCallBack(BookBean entity);
    void failCallBack(String msg);
}
