package com.common.zhuz.view;

import com.common.zhuz.base.BaseView;
import com.common.zhuz.entity.GankIoDataBean;

/**
 * Created by zhuzhen
 */

public interface AndroidFragmentView extends BaseView{

    void successCallBack(GankIoDataBean entity);
    void failCallBack(String msg);
}
