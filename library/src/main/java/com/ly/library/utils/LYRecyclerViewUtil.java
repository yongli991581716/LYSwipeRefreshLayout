package com.ly.library.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * LYRecyclerView 工具
 *
 * @author liyong
 * @date 2017/6/21 14:28
 */
public class LYRecyclerViewUtil {

    /**
     * 首个可见位置(即使部份可见)
     *
     * @param layoutManager
     * @return
     */
    public static int findFirstVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager != null) {
            if (layoutManager instanceof LinearLayoutManager) {
                return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            } else if (layoutManager instanceof GridLayoutManager) {
                return ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
            }
        }


        return RecyclerView.NO_POSITION;
    }

    /**
     * 首个完全可见的View位置
     *
     * @param layoutManager
     * @return
     */
    public static int findFirstCompletelyVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager != null) {
            if (layoutManager instanceof LinearLayoutManager) {
                return ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            } else if (layoutManager instanceof GridLayoutManager) {
                return ((GridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            }
        }


        return RecyclerView.NO_POSITION;
    }

    /**
     * 最后一个可见位置(即使部份可见)
     *
     * @param layoutManager
     * @return
     */
    public static int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager != null) {
            if (layoutManager instanceof LinearLayoutManager) {
                return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof GridLayoutManager) {
                return ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
        }


        return RecyclerView.NO_POSITION;
    }


    /**
     * 最后一个可见位置(即使部份可见)
     *
     * @param layoutManager
     * @return
     */
    public static int findLastCompletelyVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager != null) {
            if (layoutManager instanceof LinearLayoutManager) {
                return ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            } else if (layoutManager instanceof GridLayoutManager) {
                return ((GridLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            }
        }


        return RecyclerView.NO_POSITION;
    }
}
