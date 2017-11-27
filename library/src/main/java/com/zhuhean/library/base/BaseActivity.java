package com.zhuhean.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements ContentViewHolder {

    private Unbinder unbinder;
    private Handler mainHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);
    }

    public void post(Runnable runnable) {
        postDelayed(runnable, 0);
    }

    public void postDelayed(Runnable runnable, long delayMillis) {
        if (mainHandler == null) mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.postDelayed(runnable, delayMillis);
    }

    public void startActivity(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }

    public void setTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void hideActionBarShadow() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0f);
        }
    }

    @Override
    protected void onDestroy() {
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }
        unbinder.unbind();
        super.onDestroy();
    }

}
