package com.common.zhuz.view;

import com.common.zhuz.base.BaseView;
import com.common.zhuz.entity.RaidersDataEntity;

import java.io.File;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public interface MainModelView extends BaseView{
    /**
     * 底部攻略 post测试
     */
    void postceshi(RaidersDataEntity entity);
    void  failPostceshi(String msg);
    //更新升级
    void showUpdate(String version);
    void showProgress(int progress);
    void showFail(String msg);
    void showComplete(File file);
}
