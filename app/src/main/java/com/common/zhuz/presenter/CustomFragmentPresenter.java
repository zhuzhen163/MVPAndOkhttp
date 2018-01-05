package com.common.zhuz.presenter;

import com.common.zhuz.base.BasePresenter;
import com.common.zhuz.entity.GankIoDataBean;
import com.common.zhuz.fragment.CustomFragment;
import com.common.zhuz.model.CustomFragmentModel;

import static com.common.zhuz.model.CustomFragmentModel.CallBackCustomFragment;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class CustomFragmentPresenter extends BasePresenter <CustomFragmentModel,CustomFragment>{

    public void getCustom(String id, int page, int per_page){
        if (getView() != null){
            getView().showLoading();
        }
        getModel().getCustom(id,page,per_page,new CallBackCustomFragment(){

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
    public CustomFragmentModel loadModel() {
        return new CustomFragmentModel();
    }
}
