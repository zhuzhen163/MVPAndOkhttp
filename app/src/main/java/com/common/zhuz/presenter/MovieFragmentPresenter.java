package com.common.zhuz.presenter;

import com.common.zhuz.base.BasePresenter;
import com.common.zhuz.entity.HotMovieBean;
import com.common.zhuz.fragment.MoviesFragment;
import com.common.zhuz.model.MovieFragmentModel;


/**
 * Created by zhuzhen
 */

public class MovieFragmentPresenter extends BasePresenter <MovieFragmentModel,MoviesFragment>{

    public void getMovie(){
        if (getView() != null){
            getView().showLoading();
        }
        getModel().getMovie(new MovieFragmentModel.CallBackCustomFragment(){

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
    public MovieFragmentModel loadModel() {
        return new MovieFragmentModel();
    }
}
