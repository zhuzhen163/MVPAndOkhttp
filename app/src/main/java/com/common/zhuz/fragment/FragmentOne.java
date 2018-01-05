package com.common.zhuz.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.common.zhuz.R;
import com.common.zhuz.adapter.ViewPagerAdapter;
import com.common.zhuz.base.BaseFragment;
import com.common.zhuz.presenter.FragmentOnePresenter;
import com.common.zhuz.view.FragmentOneView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class FragmentOne extends BaseFragment <FragmentOnePresenter> implements FragmentOneView {

    @BindView(R.id.tab_gank)
    TabLayout tab_gank;
    @BindView(R.id.vp_content)
    ViewPager vp_content;
    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(2);
    private ViewPagerAdapter adapter;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void release() {

    }

    @Override
    protected FragmentOnePresenter loadMPresenter() {
        return new FragmentOnePresenter();
    }

    @Override
    protected void initListener() {
    }


    @Override
    protected void initView() {
        initFragment();
    }

    private void initFragment(){
        mTitleList.add("干货");
        mTitleList.add("安卓");
        mFragments.add(new CustomFragment());
        mFragments.add(AndroidFragment.newInstance("Android"));
        adapter = new ViewPagerAdapter(getChildFragmentManager(),mFragments,mTitleList);
        vp_content.setAdapter(adapter);
        // 左右预加载页面的个数
        vp_content.setOffscreenPageLimit(2);
        adapter.notifyDataSetChanged();
        tab_gank.setTabMode(TabLayout.MODE_FIXED);
        tab_gank.setupWithViewPager(vp_content);
        tab_gank.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void showLoading() {
        setShowLoading(true);
    }

    @Override
    public void hideLoading() {
        setShowLoading(false);
    }

}
