package com.common.zhuz.presenter;

import com.common.zhuz.activity.DoubanTopActivity;
import com.common.zhuz.base.BasePresenter;
import com.common.zhuz.entity.HotMovieBean;
import com.common.zhuz.model.DoubanTopActivityModel;


/**
 * Created by zhuzhen
 */

public class DoubanTopActivityPresenter extends BasePresenter <DoubanTopActivityModel,DoubanTopActivity>{

    public void getMovieTop250(int mStart, int mCount){
        if (getView() != null){
            getView().showLoading();
        }
        getModel().getMovieTop250(mStart,mCount,new DoubanTopActivityModel.CallBackCustomFragment(){

            @Override
            public void successCallBack(HotMovieBean entity) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().successCallBack(entity);
                }
            }

            @Override
            public void failCallBack(String msg) {
                if (getView() != null){
                    getView().hideLoading();
                    getView().failCallBack(msg);
                }
            }
        });
    }

    @Override
    public DoubanTopActivityModel loadModel() {
        return new DoubanTopActivityModel();
    }
}
