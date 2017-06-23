package com.ly.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.ly.library.R;
import com.ly.library.bean.Mode;
import com.ly.library.utils.LYRecyclerViewUtil;
import com.ly.library.view.footer.BaseFooterView;
import com.ly.library.view.footer.DefaultFooterView;
import com.ly.library.view.header.BaseHeaderView;
import com.ly.library.view.header.DefaultHeaderView;
import com.nineoldandroids.view.ViewHelper;

/**
 * 下拉刷新 上拉加载控件（可定制header、footer、emptyview  考虑体验，空视图将在第一次加载结束后才会添加到视图中，否则不显示；加载视图默认显示，在一次加载结束后隐藏，待定；）
 *
 * @author liyong
 * @date 2017/6/21 10:34
 */
public class LYSwipeRefreshLayout extends SwipeRefreshLayout {
    /**
     * mode
     */
    private int mMode;
    /**
     * 是否能刷新
     */
    private boolean mIsSwipeEnable = true;
    /************************** 列表模式START *****************************/
    /**
     * 默认item10
     */
    private int mPageSize = 10;
    /**
     * 根view
     */
    private RelativeLayout mRootView;
    /**
     * list 模式下RecyclerView实例
     */
    private RecyclerView mRecyclerView;
    /**
     * 数据观察者
     */
    private AdapterObserver mAdapterObserver;
    /**
     * 头部装饰器
     */
    private BaseHeaderView mHeaderDecoration;
    /**
     * 底部装饰器
     */
    private BaseFooterView mFooterDecoration;
    /**
     * 真实的头部viwe
     */
    private View mHeaderView;
    /**
     * 空view
     */
    private View mEmptyView;
    /**
     * 滚动y轴距离
     */
    private int mScrollY;

    /**
     * 是否正在加载
     */
    private boolean isLoading = false;
    /**
     * 是否有更多
     */
    private boolean hasMore = false;

    /**
     * 滚动监听实例
     */
    private LYOnScrollListener mLYOnScrollListener;
    /**
     * 滚动监听回调
     */
    private OnScrollListener mOnScrollListener;
    /**
     * 刷新监听
     */
    private onRefreshListener mOnRefreshListener;
    /**
     * 加载更多监听
     */
    private onLoadMoreListener mLoadMoreListener;

    /************************** 列表模式END*****************************/

    public LYSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public LYSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        //初始化
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        // 初始化数据
        initData(context, attrs);
        // 初始化view
        initView(context);
    }

    /**
     * 初始化数据
     */
    private void initData(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LYSwipeRefreshLayout);
        mMode = typedArray.getInt(R.styleable.LYSwipeRefreshLayout_mode, Mode.LIST.value);
    }

    /**
     * 初始化view
     */
    private void initView(Context context) {

        //设置刷新监听
        setRefreshListenter();

        if (mMode == Mode.NORAML.value) {
            //普通模式
        } else if (mMode == Mode.LIST.value) {
            //列表模式
            initListModeView(context);
        } else if (mMode == Mode.NORAML_EXTEND.value) {
            //普通可伸缩模式
        } else if (mMode == Mode.LIST_EXTEND.value) {
            //列表可伸缩模式
        }
    }

    /**
     * 设置刷新监听
     */
    private void setRefreshListenter() {
        this.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mOnRefreshListener != null) {
                    mOnRefreshListener.onRefresh();
                } else {
                    LYSwipeRefreshLayout.this.setOnRefreshComplete();
                }
            }
        });
    }

    /**
     * 初始化列表模式view
     *
     * @param context
     */
    private void initListModeView(Context context) {
        mRootView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.list_mode, null);
        addView(mRootView);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);

        //RecyclerView 优化 setHasFixedSize 的作用就是确保尺寸是通过用户输入从而确保RecyclerView的尺寸是一个常数,避免因重新绘制计算高度，而造成资源浪费
        mRecyclerView.setHasFixedSize(true);

        //设置滚动监听
        setListener();
    }

    /**
     * 设置滚动监听
     */
    private void setListener() {
        mLYOnScrollListener = new LYOnScrollListener();
        mRecyclerView.addOnScrollListener(mLYOnScrollListener);
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
        if (mAdapterObserver == null) {
            mAdapterObserver = new AdapterObserver();
        }
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mAdapterObserver);
            mAdapterObserver.onChanged();
        }
    }

    /**
     * 设置更多
     *
     * @param hasMore
     */
    private void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
        if (mFooterDecoration == null) {
            mFooterDecoration = new DefaultFooterView(getContext(), getRecyclerView());
        }
        if (!this.hasMore) {
            //remove footer装饰器
            mRecyclerView.removeItemDecoration(mFooterDecoration);
        } else {
            //add footer装饰器
            mRecyclerView.removeItemDecoration(mFooterDecoration);
            mRecyclerView.addItemDecoration(mFooterDecoration);
        }
    }

    /**
     * RecyclerView
     *
     * @return
     */
    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    /**
     * 管理器
     *
     * @return
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        if (mRecyclerView != null) {
            return mRecyclerView.getLayoutManager();
        }
        return null;
    }

    /**
     * 设置页码
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.mPageSize = pageSize;
    }

    /**
     * 设置headerview
     *
     * @param headerView
     */
    public void setHeaderView(View headerView) {
        if (headerView != null) {
            mRootView.removeView(headerView);
        }

        mHeaderView = headerView;

        if (mHeaderView == null) {
            return;
        }

        //全局监听view绘制完成
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    mHeaderView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mHeaderView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                if (getRecyclerView() == null || mHeaderView == null) {
                    return;
                }
                if (mHeaderDecoration == null) {
                    mHeaderDecoration = new DefaultHeaderView();
                }
                mHeaderDecoration.setHeight(mHeaderView.getMeasuredHeight());
                getRecyclerView().removeItemDecoration(mHeaderDecoration);
                getRecyclerView().addItemDecoration(mHeaderDecoration);
                getRecyclerView().getAdapter().notifyDataSetChanged();
            }
        });

        //添加headerview 到根视图
        mRootView.addView(mHeaderView);
    }

    /**
     * 移除header及装饰器
     */
    public void removeHeaderView() {
        if (mHeaderDecoration != null) {
            //移除顶部装饰器
            getRecyclerView().removeItemDecoration(mHeaderDecoration);
            mHeaderDecoration = null;
        }
        if (mHeaderView != null) {
            //移除headerview
            mRootView.removeView(mHeaderView);
            mHeaderView = null;
        }
    }

    /**
     * 设置footer
     *
     * @param footerView
     */
    public void setFooter(BaseFooterView footerView) {
        mFooterDecoration = footerView;
    }

    /**
     * 获取footer
     *
     * @return
     */
    public BaseFooterView getFooter() {
        return mFooterDecoration;
    }

    /**
     * 设置空视图
     *
     * @param emptyView
     */
    public void setEmptyView(View emptyView) {
        if (mEmptyView != null) {
            mRootView.removeView(mEmptyView);
        }
        mEmptyView = emptyView;
    }

    /**
     * 设置是否能刷新
     *
     * @param mIsSwipeEnable
     */
    public void setIsSwipeEnable(boolean mIsSwipeEnable) {
        this.mIsSwipeEnable = mIsSwipeEnable;
        this.setEnabled(mIsSwipeEnable);
    }

    /**
     * 加载完成,默认没有更多加载
     */
    public void setOnRefreshUpComplete() {
        //完成刷新动作
        setOnRefreshComplete();
    }

    /**
     * 加载完成,默认没有更多加载
     */
    public void setOnRefreshUpComplete(boolean hasMore) {
        //完成刷新动作
        setOnRefreshComplete();

        //设置是否有更多
        setHasMore(hasMore);
    }

    /**
     * 完成刷新
     */
    private void setOnRefreshComplete() {
        this.setRefreshing(false);
        //标记已经加载过
        if (!isFirstLoadingOver()) {
            setTag(this.getId(), true);
        }
    }

    /**
     * 是否第一次加载结束
     *
     * @return
     */
    private boolean isFirstLoadingOver() {
        Object obj = getTag(this.getId());
        if (obj == null) {
            return false;
        }
        return (boolean) obj;
    }

    /**
     * 完成加载
     *
     * @param hasMore
     */
    public void setOnRefreshDownComplete(boolean hasMore) {

        if (getLayoutManager() == null) {
            return;
        }

        //if (!hasMore && mFooterDecoration != null) {
        //    //如果没有更多，则偏移距离减去FooterDecoration的高度
        //    mScrollY -= mFooterDecoration.getHeight();
        //}

        // 如果item不够页面item 个数，不显示footer
        if (getLayoutManager().getItemCount() < mPageSize) {
            hasMore = false;
        }

        setHasMore(hasMore);

        isLoading = false;
    }

    /**
     * 设置加载描述
     *
     * @param str
     */
    public void setFooterDesc(String str) {
        if (mFooterDecoration != null) {
            mFooterDecoration.setFooterDesc(str);
        }
    }

    /**
     * 快速滚动到某一位置
     * 原理同smoothScrollToPosition
     *
     * @param position
     */
    public void scrollToPosition(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    /**
     * 平滑滚动到某一位置
     * 1、目标position在第一个可见项之前 。
     * 这种情况调用smoothScrollToPosition能够平滑的滚动到指定位置，并且置顶。
     * 2、目标position在第一个可见项之后，最后一个可见项之前。
     * 这种情况下，调用smoothScrollToPosition不会有任何效果···
     * 3、目标position在最后一个可见项之后。
     * 这种情况调用smoothScrollToPosition会把目标项滑动到屏幕最下方···
     *
     * @param position
     */
    public void smoothScrollToPosition(int position) {
        mRecyclerView.smoothScrollToPosition(position);
    }

    /**
     * 布局管理器
     *
     * @param layoutManager
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(layoutManager);
        }
    }

    /**
     * 设置滚动监听
     *
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }


    /**
     * 滚动监听
     */
    public class LYOnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (mOnScrollListener != null) {
                mOnScrollListener.onScrollStateChanged(recyclerView, newState);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            //先判断布局管理器是否为空
            if (getLayoutManager() == null) {
                return;
            }

            //记录滑动y轴距离
            mScrollY += dy;
            //RecyclerView滚动时，同步移动HeaderView的位置,以实现同步滚动
            if (mHeaderView != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    //3.0（11）系统及以上 执行此程序片段，该库支持api等级大于等于14的运行环境，故会进入此片段
                    mHeaderView.setTranslationY(-mScrollY);
                } else {
                    //3.0系统一下 执行此程序片段（若修改build，支持3.0以下，则执行此程序片段）
                    ViewHelper.setTranslationY(mHeaderView, -mScrollY);
                }
            }

//            if (mIsSwipeEnable) {
//                //解决RecyclerView和SwipeRefreshLayout共用存在的bug
//                int position = LYRecyclerViewUtil.findFirstCompletelyVisibleItemPosition(getLayoutManager());
//                if ( position!= 0) {
//                    //此处需要特殊处理，因当item过大，首个完全显示item显示不完全，刷新回无效
//                    LYSwipeRefreshLayout.this.setEnabled(false);
//                } else {
//                    LYSwipeRefreshLayout.this.setEnabled(true);
//                }
//            }
            int firstVisibleItem, visibleItemCount, totalItemCount, lastVisibleItem;
            visibleItemCount = getLayoutManager().getChildCount();
            totalItemCount = getLayoutManager().getItemCount();
            firstVisibleItem = LYRecyclerViewUtil.findFirstVisibleItemPosition(getLayoutManager());
            //如果最后item过大，会导致最后一行在屏幕内不能完全展示
            lastVisibleItem = LYRecyclerViewUtil.findLastVisibleItemPosition(getLayoutManager());

            if (totalItemCount < mPageSize) {
                setHasMore(false);
                isLoading = false;
            } else if (!isLoading && hasMore && ((lastVisibleItem + 1) == totalItemCount)) {
                if (mLoadMoreListener != null) {
                    isLoading = true;
                    mLoadMoreListener.onLoadMore();
                }

            }

            //回调
            if (mOnScrollListener != null) {
                mOnScrollListener.onScrolled(recyclerView, dx, dy);
                mOnScrollListener.onScroll(recyclerView, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        }
    }


    /**
     * 观察者模式 监听数据变化并更新视图
     */
    private class AdapterObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            if (mRecyclerView == null) {
                return;
            }

            RecyclerView.Adapter<?> adapter = mRecyclerView.getAdapter();
            if (adapter != null && mEmptyView != null) {
                if (adapter.getItemCount() == 0) {
                    if (isFirstLoadingOver()) {
                        //若第一次加载结束，则添加空视图
                        if (mEmptyView.getParent() != mRootView) {
                            mRootView.addView(mEmptyView);
                        }
                        mEmptyView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }

                } else {
                    mEmptyView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    /**
     * 滚动回调监听
     */
    public interface OnScrollListener {

        void onScrollStateChanged(RecyclerView recyclerView, int newState);

        void onScrolled(RecyclerView recyclerView, int dx, int dy);

        //类似listview onScroll方法
        void onScroll(RecyclerView recyclerView, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }

    public void setOnRefreshListener(onRefreshListener onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
    }

    public void setLoadMoreListener(onLoadMoreListener loadMoreListener) {
        this.mLoadMoreListener = loadMoreListener;
    }

    public interface onRefreshListener {
        void onRefresh();
    }

    public interface onLoadMoreListener {
        void onLoadMore();
    }

}
