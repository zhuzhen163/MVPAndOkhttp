package com.common.zhuz.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public abstract class ABaseAdapter<T> extends BaseAdapter {
    public Context mContext;
    public List<T> mList;
    public LayoutInflater mInflater;
    public final Object mLock = new Object();

    public ABaseAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<T>();
        if (context != null) {
            mInflater = LayoutInflater.from(mContext);
        }
    }

    public Context getmContext() {
        return mContext;
    }

    public List<T> getmList() {
        return mList;
    }

    public void setmList(List<T> mList) {
        if (mList != null) {
            this.mList = mList;
        }
    }

    public void add(T object) {
        if (object != null) {
            synchronized (mLock) {
                mList.add(object);
            }
            notifyDataSetChanged();
        }
    }

    public void remove(T object) {
        if (object != null) {
            synchronized (mLock) {
                mList.remove(object);
            }
            notifyDataSetChanged();
        }

    }

    public void clear() {
        synchronized (mLock) {
            mList.clear();
        }
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends T> collection) {
        if (collection != null) {
            synchronized (mLock) {
                mList.addAll(collection);
            }
            notifyDataSetChanged();
        }

    }

    public void remove(int position) {
        if (position >= 0) {
            synchronized (mLock) {
                mList.remove(position);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        if (mList != null && mList.size() > 0) {
            return mList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    abstract public View getView(int position, View convertView, ViewGroup parent);
}
