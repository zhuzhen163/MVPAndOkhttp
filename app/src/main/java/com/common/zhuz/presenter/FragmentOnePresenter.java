package com.common.zhuz.presenter;

import com.common.zhuz.base.BasePresenter;
import com.common.zhuz.fragment.FragmentOne;
import com.common.zhuz.model.FragmentOneModel;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class FragmentOnePresenter extends BasePresenter <FragmentOneModel,FragmentOne>{

    @Override
    public FragmentOneModel loadModel() {
        return new FragmentOneModel();
    }
}
