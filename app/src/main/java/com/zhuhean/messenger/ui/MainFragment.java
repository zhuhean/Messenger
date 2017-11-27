package com.zhuhean.messenger.ui;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuhean.library.base.TabViewPagerFragment;
import com.zhuhean.messenger.model.TextMessage;
import com.zhuhean.messenger.ui.reveal.RevealFrameLayout;
import com.zhuhean.messenger.ui.reveal.ViewRevealManager;

/**
 * @author zhuhean
 * @date 24/11/2017.
 */

public class MainFragment extends TabViewPagerFragment {

    private String[] titles = {
            "短信", "验证码", "垃圾"
    };

    private View child;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        child = super.onCreateView(inflater, container, savedInstanceState);
        if (child != null) {
            child.setVisibility(View.INVISIBLE);
        }
        RevealFrameLayout root = new RevealFrameLayout(getContext());
        root.addView(child);
        return root;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        post(new Runnable() {
            @Override
            public void run() {
                reveal();
            }
        });
    }

    private void reveal() {
        int cx = child.getWidth() >> 1;
        int cy = child.getHeight() >> 1;
        int radius = Math.max(cx, cy);
        radius = radius + (radius >> 1);
        child.setVisibility(View.VISIBLE);
        Animator animator = ViewRevealManager.createCircularReveal(child, cx, cy, 0, radius);
        animator.setDuration(500);
        animator.start();
    }

    @Override
    protected PagerAdapter getPagerAdapter() {
        return new MainPagerAdapter(getChildFragmentManager());
    }

    class MainPagerAdapter extends FragmentPagerAdapter {

        MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MessageFragment.newInstance(TextMessage.TYPE_DEFAULT);
                case 1:
                    return MessageFragment.newInstance(TextMessage.TYPE_SMS_CODE);
                case 2:
                    return MessageFragment.newInstance(TextMessage.TYPE_SPAM);
                default:
                    throw new IllegalStateException("No such fragment");
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }

}
