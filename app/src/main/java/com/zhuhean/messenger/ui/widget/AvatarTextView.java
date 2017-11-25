package com.zhuhean.messenger.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;

import java.util.Random;

/**
 * @author zhuhean
 * @date 17/11/2017.
 */
public class AvatarTextView extends AppCompatTextView {

    private static final String[] COLOR_STRING = {
            "#F44336",
            "#2196F3",
            "#E91E63",
            "#673AB7",
            "#3F51B5",
            "#00BCD4",
            "#009688",
            "#FF5722",
            "#4CAF50",
            "#8BC34A",
            "#03A9F4",
            "#9C27B0",
    };

    private static class Holder {
        static final Random RANDOM = new Random();
    }

    private CircleDrawable drawable;

    public AvatarTextView(Context context) {
        this(context, null);
    }

    public AvatarTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextColor(Color.WHITE);
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(24);
        setGravity(Gravity.CENTER);
        setShadowLayer(2, 1, 1, Color.GRAY);
        drawable = new CircleDrawable();
    }

    public void setTitle(String title) {
        if (title.length() > 1) title = title.substring(0, 1);
        setText(title);
        int color = Color.parseColor(COLOR_STRING[indexFor(title)]);
        drawable.setBackgroundColor(color);
    }

    private int indexFor(String title) {
        int index;
        final int bound = COLOR_STRING.length - 1;
        if (TextUtils.isDigitsOnly(title)) {
            index = Holder.RANDOM.nextInt(bound);
        } else {
            index = title.hashCode() & bound;
        }
        return index;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setBackgroundDrawable(drawable);
    }

}
