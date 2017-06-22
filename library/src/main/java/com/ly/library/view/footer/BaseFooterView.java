package com.ly.library.view.footer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;

import com.ly.library.R;
import com.ly.library.utils.LYRecyclerViewUtil;

/**
 * 继承RecyclerView装饰类ItemDecoration，赋予RecyclerView footer样式
 *
 * @author liyong
 * @date 2017/6/21 14:18
 */
public class BaseFooterView extends RecyclerView.ItemDecoration {
    /**
     *
     */
    protected Context mContent;
    /**
     * recycler实例
     */
    protected RecyclerView mRecyclerView;

    /**
     * footer描述
     */
    protected String mFooterDesc;
    /**
     * 延迟时间
     */
    protected static final int MSG_INVILIDATE = 1001;
    /**
     * draw更新时间
     */
    protected long mInvalidateTime = 150;

    /**
     * 高度
     */
    protected int mFooterHeight;
    /**
     *
     */
    protected Handler mInvalidateHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mRecyclerView == null || mRecyclerView.getAdapter() == null) {
                return;
            }

            //判断如果在底部则持续重绘
            if (LYRecyclerViewUtil.findLastVisibleItemPosition(mRecyclerView.getLayoutManager()) == mRecyclerView.getAdapter().getItemCount() - 1) {

                //刷新，重绘
                mRecyclerView.invalidate();

            }
        }
    };

    public BaseFooterView(Context context, RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        mInvalidateHanlder.removeMessages(MSG_INVILIDATE);
        onDrawFooter(c, parent);
        mInvalidateHanlder.sendEmptyMessageDelayed(MSG_INVILIDATE, mInvalidateTime);
    }

    /**
     * @param outRect
     * @param itemPosition
     * @param parent
     */
    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (itemPosition == parent.getAdapter().getItemCount() - 1) {
            outRect.set(0, 0, 0, getHeight());
        }
    }

    protected void onDrawFooter(Canvas c, RecyclerView parent) {
    }

    /**
     * 高度
     *
     * @return
     */
    public int getHeight() {
        return mFooterHeight;
    }

    /**
     * 设置描述
     *
     * @param desc
     */
    public void setFooterDesc(String desc) {
        mFooterDesc = desc;
    }


}
