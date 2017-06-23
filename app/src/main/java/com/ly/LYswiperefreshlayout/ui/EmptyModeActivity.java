package com.ly.LYswiperefreshlayout.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ly.LYswiperefreshlayout.R;
import com.ly.LYswiperefreshlayout.view.LYAdapter;
import com.ly.library.view.LYSwipeRefreshLayout;
import com.ly.library.view.footer.DefaultFooterView;

/**
 * 空视图
 *
 * @author liyong
 * @date 2017/6/22 15:52
 */
public class EmptyModeActivity extends AppCompatActivity {
    /**
     * 默认页面item数量
     */
    private static final int DEFAULT_PAGE_SIZE = 20;
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
        mLYSwipeRefreshLayout.setEnabled(false);

        //初始化header footer empty
        mLYSwipeRefreshLayout.setHeaderView(View.inflate(this, R.layout.view_header, null));
        mLYSwipeRefreshLayout.setEmptyView(View.inflate(this, R.layout.view_empty, null));
        DefaultFooterView loadMoreView = new DefaultFooterView(this, mLYSwipeRefreshLayout.getRecyclerView());
        mLYSwipeRefreshLayout.setFooter(loadMoreView);

        //初始化适配器
        mAdapter = new LYAdapter(this);
        mAdapter.setCount(0);
        mLYSwipeRefreshLayout.setAdapter(mAdapter);
        mLYSwipeRefreshLayout.setOnRefreshUpComplete(true);
    }


}
