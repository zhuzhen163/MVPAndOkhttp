package com.common.zhuz.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.zhuz.R;
import com.common.zhuz.base.ListBaseAdapter;
import com.common.zhuz.base.SuperViewHolder;
import com.common.zhuz.entity.MoviesBean;
import com.common.zhuz.tools.ImgLoadUtil;


/**
 * Created by zhuzhen
 */

public class DoubanTopAdapter extends ListBaseAdapter<MoviesBean> {

    ImageView iv_top_photo;
    TextView tv_name,tv_rate;

    public DoubanTopAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_douban_top;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        iv_top_photo = holder.getView(R.id.iv_top_photo);
        tv_name = holder.getView(R.id.tv_name);
        tv_rate = holder.getView(R.id.tv_rate);

        MoviesBean moviesBean = mDataList.get(position);
        ImgLoadUtil.displayEspImage(moviesBean.getImages().getLarge(), iv_top_photo, 1);
        tv_name.setText(moviesBean.getTitle());
        tv_rate.setText(moviesBean.getRating().getAverage()+"");
    }
}
