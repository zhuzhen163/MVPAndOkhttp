package com.common.zhuz.adapter;

import android.content.Context;
import android.widget.TextView;

import com.common.zhuz.R;
import com.common.zhuz.base.ListBaseAdapter;
import com.common.zhuz.base.SuperViewHolder;
import com.common.zhuz.entity.FragmentOneEntity;

/**
 * 首页适配器
 * viewpager
 */

public class FragmentOneAdapter extends ListBaseAdapter<FragmentOneEntity> {

    public FragmentOneAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_fragment_one;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        TextView tv_item = holder.getView(R.id.tv_item);
        tv_item.setText(mDataList.get(position).getData());
    }
}
