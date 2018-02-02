package com.common.zhuz.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.zhuz.R;
import com.common.zhuz.base.ListBaseAdapter;
import com.common.zhuz.base.SuperViewHolder;
import com.common.zhuz.entity.PersonBean;
import com.common.zhuz.tools.ImgLoadUtil;


public class MovieDetailAdapter extends ListBaseAdapter<PersonBean> {

    ImageView iv_avatar;
    TextView tv_name,tv_type;

    public MovieDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_movie_detail_person;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        iv_avatar = holder.getView(R.id.iv_avatar);
        tv_name = holder.getView(R.id.tv_name);
        tv_type = holder.getView(R.id.tv_type);
        PersonBean personBean = mDataList.get(position);
        ImgLoadUtil.displayEspImage(personBean.getAvatars().getLarge(),iv_avatar,0);
        tv_name.setText(personBean.getName());
        tv_type.setText(personBean.getType());

    }
}
