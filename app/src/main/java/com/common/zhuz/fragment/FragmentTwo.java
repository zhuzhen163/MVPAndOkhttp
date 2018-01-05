package com.common.zhuz.fragment;

import android.os.Bundle;

import com.common.zhuz.R;
import com.common.zhuz.base.BaseFragment;
import com.common.zhuz.base.BasePresenter;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class FragmentTwo extends BaseFragment {

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_two;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void release() {

    }

    @Override
    protected BasePresenter loadMPresenter() {
        return null;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
