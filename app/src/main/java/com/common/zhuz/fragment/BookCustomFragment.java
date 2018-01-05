package com.common.zhuz.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.common.zhuz.R;
import com.common.zhuz.adapter.BookAdapter;
import com.common.zhuz.base.BaseFragment;
import com.common.zhuz.entity.BookBean;
import com.common.zhuz.presenter.BookCustomFragmentPresenter;
import com.common.zhuz.tools.CommonUtils;
import com.common.zhuz.view.BookCustomFragmentView;

import butterknife.BindView;

/**
 * 书籍 fragment
 */
public class BookCustomFragment extends BaseFragment<BookCustomFragmentPresenter> implements BookCustomFragmentView{

    @BindView(R.id.srl_book)
    SwipeRefreshLayout srl_book;
    @BindView(R.id.rv_book)
    RecyclerView rv_book;
    private static final String TYPE = "param1";
    private String mType = "综合";
    // 开始请求的角标
    private int mStart = 0;
    // 一次请求的数量
    private int mCount = 18;
    private BookAdapter mBookAdapter;
    private GridLayoutManager mLayoutManager;


    public static BookCustomFragment newInstance(String param1) {
        BookCustomFragment fragment = new BookCustomFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_book_custom;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        srl_book.setRefreshing(true);
        srl_book.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFragmentPresenter.getBook(mType, mStart, mCount);
            }
        }, 500);
    }

    @Override
    protected void release() {

    }

    @Override
    protected BookCustomFragmentPresenter loadMPresenter() {
        return new BookCustomFragmentPresenter();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        srl_book.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        srl_book.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl_book.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mStart = 0;
                        mFragmentPresenter.getBook(mType, mStart, mCount);
                    }
                }, 1000);

            }
        });

        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        rv_book.setLayoutManager(mLayoutManager);
        scrollRecycleView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(TYPE);
        }
    }

    public void scrollRecycleView() {
        rv_book.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                    if (mBookAdapter == null) {
                        return;
                    }
                    if (mLayoutManager.getItemCount() == 0) {
                        mBookAdapter.updateLoadStatus(BookAdapter.LOAD_NONE);
                        return;

                    }
                    if (lastVisibleItem + 1 == mLayoutManager.getItemCount()
                            && mBookAdapter.getLoadStatus() != BookAdapter.LOAD_MORE) {
                        mBookAdapter.updateLoadStatus(BookAdapter.LOAD_MORE);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mStart += mCount;
                                mFragmentPresenter.getBook(mType, mStart, mCount);
                            }
                        }, 1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void successCallBack(BookBean bookBean) {
        if (mStart == 0) {
            if (bookBean != null && bookBean.getBooks() != null && bookBean.getBooks().size() > 0) {

                if (mBookAdapter == null) {
                    mBookAdapter = new BookAdapter(getActivity());
                }
                mBookAdapter.setList(bookBean.getBooks());
                mBookAdapter.notifyDataSetChanged();
                rv_book.setAdapter(mBookAdapter);
            }
        } else {
            mBookAdapter.addAll(bookBean.getBooks());
            mBookAdapter.notifyDataSetChanged();
        }
        if (mBookAdapter != null) {
            mBookAdapter.updateLoadStatus(BookAdapter.LOAD_PULL_TO);
        }
        if (srl_book.isRefreshing()) {
            srl_book.setRefreshing(false);
        }
    }

    @Override
    public void failCallBack(String msg) {
        if (srl_book.isRefreshing()) {
            srl_book.setRefreshing(false);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
