package com.yu.hu.customprogressbartest.progress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.yu.hu.customprogressbartest.R;

/**
 * create by hy on 2019/12/08 15:37
 * <p>
 * 自定义进度条1
 */
public class ProgressView extends View {
    /**
     * 圆角弧度
     */
    private static final int RADIUS = 60;

    private Paint mPaint = new Paint();

    private int mProgress;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setProgress(int progress) {
        if (progress >= 0 && progress <= 100) {
            mProgress = progress;
            invalidate();
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //画底部背景
        mPaint.setColor(Color.LTGRAY);
        canvas.drawRoundRect(new RectF(0, 0, width, height), RADIUS, RADIUS, mPaint);
        //画进度条
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        canvas.drawRoundRect(new RectF(0, 0, width * ((float) mProgress / 100), height), RADIUS, RADIUS, mPaint);
        //画文字图层
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mPaint.setTextSize(sp2px(20));
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setTextAlign(Paint.Align.CENTER);
        Bitmap textBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas textCavas = new Canvas(textBitmap);
        String content = mProgress + "%";
        float textY = height / 2.0f - (mPaint.getFontMetricsInt().descent / 2.0f + mPaint.getFontMetricsInt().ascent / 2.0f);
        textCavas.drawText(content, width / 2.0f, textY, mPaint);
        //画最上层的白色图层，未相交时不会显示出来
        mPaint.setColor(Color.WHITE);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        //setMode 后画的是src
        textCavas.drawRoundRect(new RectF(0, 0, width * ((float) mProgress / 100), height), RADIUS, RADIUS, mPaint);
        //画结合后的图层
        canvas.drawBitmap(textBitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);
        textBitmap.recycle();
    }

    private int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
