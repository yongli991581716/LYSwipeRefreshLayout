package com.ly.library.view.header;

import android.support.v7.widget.RecyclerView;

/**
 * 继承RecyclerView装饰类ItemDecoration，赋予RecyclerView到顶部距离（即真实的header距离）
 *
 * @author liyong
 * @date 2017/6/21 14:07
 */
public class BaseHeaderView extends RecyclerView.ItemDecoration {
    /**
     * header距离
     */
    protected int mHeaderHeight;

    public int getHeight() {
        return mHeaderHeight;
    }

    public void setHeight(int headerHeight) {
        this.mHeaderHeight = headerHeight;
    }
}
