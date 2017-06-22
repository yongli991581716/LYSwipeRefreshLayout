package com.ly.LYswiperefreshlayout.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * 适配器基类
 *
 * @author liyong
 * @date 2017/6/22 14:28
 */
public class LYBaseAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    protected LayoutInflater mInflater;
    protected int mCount = 0;
    protected Context mContext = null;

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_HISVIDEO = 1;
    public static final int TYPE_MESSAGE = 2;

    public LYBaseAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindViewHolder(T holder, int position) {

    }

    public void setCount(int count) {
        mCount = count;
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public Object getItem(int position) {
        return null;
    }
}

