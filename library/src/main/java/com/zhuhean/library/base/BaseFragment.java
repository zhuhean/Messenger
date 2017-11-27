package com.zhuhean.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements ContentViewHolder {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    public void post(Runnable runnable) {
        BaseActivity activity = getBaseActivity();
        if (activity != null) activity.post(runnable);
    }

    public void postDelayed(Runnable runnable, long delayMillis) {
        BaseActivity activity = getBaseActivity();
        if (activity != null) activity.postDelayed(runnable, delayMillis);
    }

    private BaseActivity getBaseActivity() {
        if (getActivity() instanceof BaseActivity) return (BaseActivity) getActivity();
        return null;
    }

}
