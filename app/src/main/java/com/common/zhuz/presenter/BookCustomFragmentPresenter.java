package com.common.zhuz.presenter;

import com.common.zhuz.base.BasePresenter;
import com.common.zhuz.entity.BookBean;
import com.common.zhuz.fragment.BookCustomFragment;
import com.common.zhuz.model.BookCustomFragmentModel;


/**
 * Created by zhuzhen
 */

public class BookCustomFragmentPresenter extends BasePresenter <BookCustomFragmentModel,BookCustomFragment>{

    public void getBook(String tag, int start, int count){
        if (getView() != null){
            getView().showLoading();
        }
        getModel().getBook(tag,start,count,new BookCustomFragmentModel.CallBackCustomFragment(){

            @Override
            public void successCallBack(BookBean entity) {
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
    public BookCustomFragmentModel loadModel() {
        return new BookCustomFragmentModel();
    }
}
