package com.common.zhuz.presenter;

import com.common.zhuz.base.BasePresenter;
import com.common.zhuz.entity.GankIoDataBean;
import com.common.zhuz.fragment.AndroidFragment;
import com.common.zhuz.model.AndroidFragmentModel;


/**
 * Created by zhuzhen
 */

public class AndroidFragmentPresenter extends BasePresenter <AndroidFragmentModel,AndroidFragment>{

    public void getCustom(String id, int page, int per_page){
        if (getView() != null){
            getView().showLoading();
        }
        getModel().getCustom(id,page,per_page,new AndroidFragmentModel.CallBackCustomFragment(){

            @Override
            public void successCallBack(GankIoDataBean entity) {
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
    public AndroidFragmentModel loadModel() {
        return new AndroidFragmentModel();
    }
}
