package com.common.zhuz.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.common.zhuz.R;
import com.common.zhuz.adapter.MovieAdapter;
import com.common.zhuz.base.BaseFragment;
import com.common.zhuz.entity.HotMovieBean;
import com.common.zhuz.module.xrecyclerview.XRecyclerView;
import com.common.zhuz.presenter.MovieFragmentPresenter;
import com.common.zhuz.tools.PerfectClickListener;
import com.common.zhuz.tools.SwitchActivityManager;
import com.common.zhuz.view.MovieFragmentView;

import butterknife.BindView;

/**
 * Created by zhuzhen
 */

public class MoviesFragment extends BaseFragment <MovieFragmentPresenter> implements MovieFragmentView {

    @BindView(R.id.xrv_list)
    XRecyclerView xrv_list;
    private View mHeaderView = null;
    private MovieAdapter movieAdapter;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_four;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mFragmentPresenter.getMovie();
    }

    @Override
    protected void release() {

    }

    @Override
    protected MovieFragmentPresenter loadMPresenter() {
        return new MovieFragmentPresenter();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        movieAdapter = new MovieAdapter(mContext);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrv_list.setLayoutManager(mLayoutManager);
        xrv_list.setAdapter(movieAdapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        setShowLoading(false);
    }

    private void setAdapter(HotMovieBean hotMovieBean) {
        if (mHeaderView == null){
            mHeaderView = View.inflate(getContext(), R.layout.header_item_one, null);
            xrv_list.addHeaderView(mHeaderView);
        }
        initHeadView();

        xrv_list.setPullRefreshEnabled(false);
//        xrv_list.clearHeader();

        xrv_list.setLoadingMoreEnabled(false);
        // 需加，不然滑动不流畅
        xrv_list.setNestedScrollingEnabled(false);
        xrv_list.setHasFixedSize(false);

        movieAdapter.clear();
        movieAdapter.addAll(hotMovieBean.getSubjects());
        movieAdapter.notifyDataSetChanged();
    }

    private void initHeadView() {
        View llMovieTop = mHeaderView.findViewById(R.id.ll_movie_top);
        llMovieTop.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                SwitchActivityManager.startDoubanTopActivity(mContext);
            }
        });
    }


    @Override
    public void successCallBack(HotMovieBean entity) {
        if (entity != null) {
            setAdapter(entity);
        }
    }

    @Override
    public void failCallBack(String msg) {

    }
}
