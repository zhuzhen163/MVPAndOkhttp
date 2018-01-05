package com.common.zhuz.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.common.zhuz.R;
import com.common.zhuz.adapter.ViewPagerAdapter;
import com.common.zhuz.base.BaseFragment;
import com.common.zhuz.entity.BookBean;
import com.common.zhuz.presenter.BookFragmentPresenter;
import com.common.zhuz.view.BookFragmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhuzhen
 */

public class BookFragment extends BaseFragment<BookFragmentPresenter> implements BookFragmentView {

    @BindView(R.id.tab_book)
    TabLayout tab_book;
    @BindView(R.id.vp_book)
    ViewPager vp_book;
    private ViewPagerAdapter adapter;
    private List<String> mTitle = new ArrayList<>(3);
    private List<Fragment> mListFragment = new ArrayList<>(3);

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_book;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻2个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        adapter = new ViewPagerAdapter(getChildFragmentManager(), mListFragment, mTitle);
        vp_book.setAdapter(adapter);
        // 左右预加载页面的个数
        vp_book.setOffscreenPageLimit(2);
        adapter.notifyDataSetChanged();
        tab_book.setTabMode(TabLayout.MODE_FIXED);
        tab_book.setupWithViewPager(vp_book);
    }

    @Override
    protected void release() {

    }

    @Override
    protected BookFragmentPresenter loadMPresenter() {
        return new BookFragmentPresenter();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mTitle.add("文学");
        mTitle.add("文化");
        mTitle.add("生活");
        mListFragment.add(new BookCustomFragment().newInstance("文学"));
        mListFragment.add(new BookCustomFragment().newInstance("文化"));
        mListFragment.add(new BookCustomFragment().newInstance("生活"));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void successCallBack(BookBean entity) {

    }

    @Override
    public void failCallBack(String msg) {

    }
}
