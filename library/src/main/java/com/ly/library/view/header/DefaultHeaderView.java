package com.ly.library.view.header;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;

/**
 * 默认header样式
 *
 * @author liyong
 * @date 2017/6/21 14:11
 */
public class DefaultHeaderView extends BaseHeaderView {
    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (itemPosition == 0) {
            outRect.set(0, mHeaderHeight, 0, 0);
        }
    }
}
