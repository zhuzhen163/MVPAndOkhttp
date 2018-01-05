package com.common.zhuz.adapter;

import android.content.Context;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.zhuz.R;
import com.common.zhuz.base.ListBaseAdapter;
import com.common.zhuz.base.SuperViewHolder;
import com.common.zhuz.entity.MoviesBean;
import com.common.zhuz.tools.AppUtils;
import com.common.zhuz.tools.ImgLoadUtil;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;


/**
 * Created by zhuzhen
 */

public class MovieAdapter extends ListBaseAdapter<MoviesBean> {

    ImageView iv_one_photo;
    TextView tv_one_title,tv_one_directors,tv_one_casts,tv_one_genres,tv_one_rating_rate;
    LinearLayout ll_one_item;

    public MovieAdapter(Context context) {
        super(context);
    }


    @Override
    public int getLayoutId() {
        return R.layout.item_movie;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        iv_one_photo = holder.getView(R.id.iv_one_photo);
        tv_one_title = holder.getView(R.id.tv_one_title);
        tv_one_directors = holder.getView(R.id.tv_one_directors);
        tv_one_casts = holder.getView(R.id.tv_one_casts);
        tv_one_genres = holder.getView(R.id.tv_one_genres);
        tv_one_rating_rate = holder.getView(R.id.tv_one_rating_rate);
        ll_one_item = holder.getView(R.id.ll_one_item);

        MoviesBean moviesBean = mDataList.get(position);
        ImgLoadUtil.displayEspImage(moviesBean.getImages().getLarge(), iv_one_photo, 1);
        tv_one_title.setText(moviesBean.getTitle());
        tv_one_directors.setText(AppUtils.formatName(moviesBean.getDirectors()));
        tv_one_casts.setText(AppUtils.formatName(moviesBean.getCasts()));
        tv_one_genres.setText(AppUtils.formatGenres(moviesBean.getGenres()));
        tv_one_rating_rate.setText(moviesBean.getRating().getAverage()+"");

        ViewHelper.setScaleX(ll_one_item,0.8f);
        ViewHelper.setScaleY(ll_one_item,0.8f);
        ViewPropertyAnimator.animate(ll_one_item).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
        ViewPropertyAnimator.animate(ll_one_item).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();

    }
}
