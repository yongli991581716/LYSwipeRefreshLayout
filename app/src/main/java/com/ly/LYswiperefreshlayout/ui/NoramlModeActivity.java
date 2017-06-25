package com.ly.LYswiperefreshlayout.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.ly.LYswiperefreshlayout.R;
import com.ly.library.view.LYSwipeRefreshLayout;

/**
 * @author liyong
 * @date 2017/6/25
 */
public class NoramlModeActivity extends AppCompatActivity implements LYSwipeRefreshLayout.onRefreshListener {

    /**
     * 刷新标记
     */
    private static final int MSG_CODE_REFRESH = 0;

    /**
     * 刷新延迟时间
     */
    private static final int DELAY_TIME = 1000;
    /**
     * 刷新控件
     */
    private LYSwipeRefreshLayout mLYSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        //初始化
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mLYSwipeRefreshLayout = (LYSwipeRefreshLayout) this.findViewById(R.id.swipeRefreshLayout);

        //初始化SwipeRefreshLayout
        mLYSwipeRefreshLayout.setOnRefreshListener(this);
    }

    //更新操作Handler
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE_REFRESH) {
                mLYSwipeRefreshLayout.setOnRefreshUpComplete();

            }
        }
    };

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(MSG_CODE_REFRESH, DELAY_TIME);
    }


}
