package com.common.frame.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MediumBoldTextView extends androidx.appcompat.widget.AppCompatTextView {
    private float mStrokeWidth = 0f;
    private final TextPaint textPaint;

    public MediumBoldTextView(@NonNull Context context) {
        super(context);
        textPaint = getPaint();
    }

    public MediumBoldTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        textPaint = getPaint();
    }

    public MediumBoldTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textPaint = getPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //设置画笔的描边宽度值
        textPaint.setStrokeWidth(mStrokeWidth);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        super.onDraw(canvas);
    }

    public void setStrokeWidth(float mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        invalidate();
    }
}
