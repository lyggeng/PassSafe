package com.example.passsafe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.example.passsafe.R;
import com.example.passsafe.storage.MySP;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;

public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity";

    private MySP mMySP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        //初始化组件
        initWidgets();

        //初始化动作
        initActions();

    }

    /**
     * 初始化组件
     */
    void initWidgets() {
        mMySP = new MySP(this).getmMySP();

    }

    /**
     * 初始化动作
     */
    void initActions() {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        Runnable runnable = () -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        };
        scheduledExecutorService.schedule(runnable, 2000, TimeUnit.MILLISECONDS);
    }
}
