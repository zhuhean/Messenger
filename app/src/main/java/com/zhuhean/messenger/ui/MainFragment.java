package com.zhuhean.messenger.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.zhuhean.messenger.model.TextMessage;
import com.zhuhean.library.base.TabViewPagerFragment;

/**
 * @author zhuhean
 * @date 24/11/2017.
 */

public class MainFragment extends TabViewPagerFragment {

    private String[] titles = {
            "短信", "验证码", "垃圾"
    };

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
