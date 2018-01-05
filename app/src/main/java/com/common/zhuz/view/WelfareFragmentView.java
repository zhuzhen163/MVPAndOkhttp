package com.common.zhuz.view;

import com.common.zhuz.base.BaseView;
import com.common.zhuz.entity.GankIoDataBean;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public interface WelfareFragmentView extends BaseView{

    void successCallBack(GankIoDataBean entity);
    void failCallBack(String msg);
}
