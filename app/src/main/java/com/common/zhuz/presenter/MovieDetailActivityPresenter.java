package com.common.zhuz.presenter;

import com.common.zhuz.activity.MovieDetailActivity;
import com.common.zhuz.base.BasePresenter;
import com.common.zhuz.entity.MovieDetailBean;
import com.common.zhuz.model.MovieDetailActivityModel;


/**
 * Created by zhuzhen
 */

public class MovieDetailActivityPresenter extends BasePresenter <MovieDetailActivityModel,MovieDetailActivity>{

    public void getMovieDetail(String id){
        if (getView() != null){
            getView().showLoading();
        }
        getModel().getMovieDetail(id,new MovieDetailActivityModel.CallBackCustomFragment(){

            @Override
            public void successCallBack(MovieDetailBean entity) {
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
    public MovieDetailActivityModel loadModel() {
        return new MovieDetailActivityModel();
    }
}
