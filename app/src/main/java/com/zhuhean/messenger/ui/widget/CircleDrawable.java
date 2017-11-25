package com.zhuhean.messenger.ui.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.ColorInt;

/**
 * Created by zhuhean on 17/11/2017.
 */

public class CircleDrawable extends ShapeDrawable {

    @ColorInt
    private int backgroundColor = Color.parseColor("#E91E63");

    private class Circle extends Shape {

        public Circle() {
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            paint.setColor(backgroundColor);
            Rect rect = getBounds();
            int radius = Math.min((rect.width() >> 1), (rect.height() >> 1));
            canvas.drawCircle(rect.centerX(), rect.centerY(), radius, paint);
        }

    }

    public void setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidateSelf();
    }

    public CircleDrawable() {
        setShape(new Circle());
    }

}
