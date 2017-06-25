package com.ly.LYswiperefreshlayout.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ly.LYswiperefreshlayout.R;

/**
 * 主页
 *
 * @author liyong
 * @date 2017/6/21 11:12
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 普通模式
     *
     * @param view
     */
    public void normalClick(View view) {
        startActivity(new Intent(this, NoramlModeActivity.class));
    }

    /**
     * 列表模式
     *
     * @param view
     */
    public void listClick(View view) {
        startActivity(new Intent(this, ListModeActivity.class));
    }

    /**
     * 列表空视图模式
     *
     * @param view
     */
    public void listEmptyClick(View view) {
        startActivity(new Intent(this, EmptyModeActivity.class));
    }


}