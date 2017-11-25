package com.zhuhean.messenger.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;

/**
 * @author zhuhean
 * @date 23/11/2017.
 */

public class ViewHelper {

    private final View root;
    private final SparseArray<View> viewCache;

    public ViewHelper(Activity activity) {
        this(activity.getWindow().getDecorView());
    }

    public ViewHelper(View root) {
        this.root = root;
        viewCache = new SparseArray<>();
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V getView(@IdRes int viewId) {
        View view = viewCache.get(viewId);
        if (view == null) {
            view = root.findViewById(viewId);
            viewCache.put(viewId, view);
        }
        return (V) view;
    }

    public ViewHelper setText(@IdRes int textViewId, CharSequence text) {
        TextView textView = getView(textViewId);
        textView.setText(text);
        return this;
    }

    public ViewHelper setChecked(@IdRes int checkableId, boolean checked) {
        View view = getView(checkableId);
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(checked);
        }
        return this;
    }

    public ViewHelper setOnViewClickListenter(@IdRes int viewId, View.OnClickListener onClickListener) {
        View view = getView(viewId);
        view.setOnClickListener(onClickListener);
        return this;
    }

    public ViewHelper setOnViewTouchListenter(@IdRes int viewId, View.OnTouchListener onTouchListener) {
        View view = getView(viewId);
        view.setOnTouchListener(onTouchListener);
        return this;
    }

    public void clear() {
        viewCache.clear();
    }

    public static int dpToPx(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
