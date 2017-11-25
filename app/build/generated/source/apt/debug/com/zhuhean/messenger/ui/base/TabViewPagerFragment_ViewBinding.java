// Generated code from Butter Knife. Do not modify!
package com.zhuhean.messenger.ui.base;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.zhuhean.messenger.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TabViewPagerFragment_ViewBinding implements Unbinder {
  private TabViewPagerFragment target;

  @UiThread
  public TabViewPagerFragment_ViewBinding(TabViewPagerFragment target, View source) {
    this.target = target;

    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tabLayout, "field 'tabLayout'", TabLayout.class);
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.viewPager, "field 'viewPager'", ViewPager.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TabViewPagerFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tabLayout = null;
    target.viewPager = null;
  }
}
