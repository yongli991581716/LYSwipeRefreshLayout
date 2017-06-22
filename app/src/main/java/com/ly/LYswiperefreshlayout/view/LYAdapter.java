package com.ly.LYswiperefreshlayout.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ly.LYswiperefreshlayout.R;

/**
 * 适配器
 *
 * @author liyong
 * @date 2017/6/22 14:28
 */
public class LYAdapter extends LYBaseAdapter<LYAdapter.ViewHolder> {
    public LYAdapter(Context context) {
        super(context);
    }


    @Override
    public LYAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_common, null);
        return new LYAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(LYAdapter.ViewHolder holder, int position) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}