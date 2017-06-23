package com.ly.LYswiperefreshlayout.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.ly.LYswiperefreshlayout.R;
import com.ly.LYswiperefreshlayout.view.LYAdapter;
import com.ly.library.view.DividerItemDecoration;
import com.ly.library.view.LYSwipeRefreshLayout;
import com.ly.library.view.footer.DefaultFooterView;

/**
 * 适配器基类
 *
 * @author liyong
 * @date 2017/6/22 14:28
 */
public class ListModeActivity extends AppCompatActivity implements LYSwipeRefreshLayout.onRefreshListener, LYSwipeRefreshLayout.onLoadMoreListener {
    /**
     * 默认页面item数量
     */
    private static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 刷新标记
     */
    private static final int MSG_CODE_REFRESH = 0;
    /**
     * 加载更多标记
     */
    private static final int MSG_CODE_LOADMORE = 1;

    /**
     * 刷新延迟时间
     */
    private static final int DELAY_TIME = 1000;
    /**
     * 刷新控件
     */
    private LYSwipeRefreshLayout mLYSwipeRefreshLayout;
    /**
     * 适配器
     */
    private LYAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        //初始化
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mLYSwipeRefreshLayout = (LYSwipeRefreshLayout) this.findViewById(R.id.swipeRefreshLayout);

        //初始化SwipeRefreshLayout
        mLYSwipeRefreshLayout.setLayoutManager(new LinearLayoutManager(this));
        mLYSwipeRefreshLayout.setOnRefreshListener(this);
        mLYSwipeRefreshLayout.setLoadMoreListener(this);
        mLYSwipeRefreshLayout.getRecyclerView().addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));

        //初始化header footer empty
        mLYSwipeRefreshLayout.setHeaderView(View.inflate(this, R.layout.view_header, null));
        mLYSwipeRefreshLayout.setEmptyView(View.inflate(this, R.layout.view_empty, null));
        DefaultFooterView loadMoreView = new DefaultFooterView(this, mLYSwipeRefreshLayout.getRecyclerView());
        mLYSwipeRefreshLayout.setFooter(loadMoreView);

        //初始化适配器
        mAdapter = new LYAdapter(this);
        mAdapter.setCount(DEFAULT_PAGE_SIZE);
        mLYSwipeRefreshLayout.setAdapter(mAdapter);
        mLYSwipeRefreshLayout.setOnRefreshUpComplete(true);
    }

    //更新操作Handler
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE_REFRESH) {
                //先调用刷新完成控件，才会将空视图添加到视图中，否则当数据为空时，空视图不会立即显示出来，会在下一次调用数据变化时显示
                mLYSwipeRefreshLayout.setOnRefreshUpComplete(true);
                mAdapter.setCount(DEFAULT_PAGE_SIZE);
                mAdapter.notifyDataSetChanged();
            } else if (msg.what == MSG_CODE_LOADMORE) {

                mLYSwipeRefreshLayout.setOnRefreshDownComplete(true);
                int size = mAdapter.getItemCount() + DEFAULT_PAGE_SIZE;
                mAdapter.setCount(size);
                if (size == 60) {
                    Toast.makeText(ListModeActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                    mLYSwipeRefreshLayout.setOnRefreshDownComplete(false);
                } else {
                    mLYSwipeRefreshLayout.setOnRefreshDownComplete(true);
                }

                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(MSG_CODE_REFRESH, DELAY_TIME);
    }

    @Override
    public void onLoadMore() {
        mHandler.sendEmptyMessageDelayed(MSG_CODE_LOADMORE, DELAY_TIME);
    }


}
