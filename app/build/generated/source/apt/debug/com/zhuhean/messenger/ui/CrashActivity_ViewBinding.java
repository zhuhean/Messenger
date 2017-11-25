// Generated code from Butter Knife. Do not modify!
package com.zhuhean.messenger.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.zhuhean.messenger.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CrashActivity_ViewBinding implements Unbinder {
  private CrashActivity target;

  private View view2131230784;

  @UiThread
  public CrashActivity_ViewBinding(CrashActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CrashActivity_ViewBinding(final CrashActivity target, View source) {
    this.target = target;

    View view;
    target.crashTV = Utils.findRequiredViewAsType(source, R.id.crashTV, "field 'crashTV'", TextView.class);
    view = Utils.findRequiredView(source, R.id.done, "method 'onClick'");
    view2131230784 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    CrashActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.crashTV = null;

    view2131230784.setOnClickListener(null);
    view2131230784 = null;
  }
}
