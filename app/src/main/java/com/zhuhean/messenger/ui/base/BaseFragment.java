package com.zhuhean.messenger.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements ContentViewHolder {

    protected View rootView;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getContentView(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
