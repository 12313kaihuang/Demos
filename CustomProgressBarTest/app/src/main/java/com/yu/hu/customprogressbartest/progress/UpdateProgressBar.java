package com.yu.hu.customprogressbartest.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by Hy on 2019/12/05 18:56
 **/
@SuppressWarnings("unused")
public class UpdateProgressBar extends View {

    private static final String TAG = "UpdateProgressBar";

    private static final int DEFAULT_TEXT_SIZE = 10; //sp  默认字体大小
    private static final int DEFAULT_TEXT_COLOR = 0xFF259D58;  //默认字体颜色
    private static final int DEFAULT_TEXT_OFFSET = 5; //dp  默认字体向下偏移量

    private static final int DEFAULT_PROGRESS_BACKGROUND_COLOR = 0x35249D57;  //默认进度条背景颜色
    private static final int DEFAULT_PROGRESS_BACKGROUND_HEIGHT = 12; //dp    默认进度条背景高度

    private static final int DEFAULT_REACH_COLOR = DEFAULT_TEXT_COLOR;  //默认进度条当前进度颜色
    private static final int DEFAULT_REACH_HEIGHT = 8; //dp    默认进度条当前进度高度

    private static final int DEFAULT_RHOMBUS_WIDTH = 15; // dp  默认菱形的宽度
    private static final float DEFAULT_SLOPE = 0.3f;  // 默认斜率
    private static final int DEFAULT_SPEED = 10; // 默认偏移增量

    private static final int[] DEFAULT_COLOR_LIST = new int[]{0xFF4ACC90, 0xFF249D57};

    /* *****************text***************** */
    private int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private int mTextOffset = dip2px(DEFAULT_TEXT_OFFSET);


    /* *****************background***************** */
    private int mBackgroundColor = DEFAULT_PROGRESS_BACKGROUND_COLOR;
    private int mBackgroundHeight = dip2px(DEFAULT_PROGRESS_BACKGROUND_HEIGHT);

    /* *****************进度条***************** */
    private int mReachColor = DEFAULT_REACH_COLOR;
    private int mReachHeight = dip2px(DEFAULT_REACH_HEIGHT);  //进度条高度

    private int mCorner = dip2px(16);
    private int max = 100;
    private int mProgress = 0;

    private int mFirstColor = DEFAULT_COLOR_LIST[0];
    private int mSecondColor = DEFAULT_COLOR_LIST[1];

    /* *****************偏移相关***************** */
    private int translateX;  //偏移量
    private int mSpeed = DEFAULT_SPEED;
    private int mRhombusWidth = dip2px(DEFAULT_RHOMBUS_WIDTH);  //菱形的宽度
    private Path mSlopePath;  //用于辅助绘制菱形
    private PorterDuffXfermode mDstATopMode;

    private RectF mBackgroundRect;  //用于背景绘制
    private RectF mForegroundRect;  //用于当前进度绘制
    private int mRectPadding;  //两个矩形之间的距离
    private Paint mPaint;  //画笔


    /**
     * 进度条实际能显示的最大宽度
     */
    private int mRealWidth;

    private boolean autoMock = true;

    private Runnable mMockAction = new Runnable() {
        @Override
        public void run() {
            translateX += mSpeed;
            invalidate();
            mock();
        }
    };

    public UpdateProgressBar(Context context) {
        this(context, null);
    }

    public UpdateProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mSlopePath = new Path();
        mBackgroundRect = new RectF(0, 0, 0, mBackgroundHeight);
        mForegroundRect = new RectF(0, 0, 0, mReachHeight);
        mRectPadding = (mBackgroundHeight - mReachHeight) / 2;
        mPaint.setTextSize(mTextSize);

        mDstATopMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);

        //关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //需要设置固定宽度 或者 march_parent  wrap_content的话无法进行绘制
        //int widthMode = MeasureSpec.getMode(widthMeasureSpec); //宽度模式
        int widthVal = MeasureSpec.getSize(widthMeasureSpec); //宽度值

        int height = measureHeight(heightMeasureSpec);
        Log.d(TAG, "onMeasure: widthVal = " + widthVal + ",height = " + height);

        setMeasuredDimension(widthVal, height);
        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        mBackgroundRect.right = mRealWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mProgress < 0) {
            return;
        }

        canvas.save();
        canvas.translate(getPaddingLeft(), 0);

        float radio = getProgress() * 1.0f / getMax();
        float progressX = radio * (mRealWidth - mRectPadding * 2);

        //绘制已加载的进度条
        Log.d(TAG, "onDraw: progressX = " + progressX);
        canvas.translate(mRectPadding, (mBackgroundHeight - mReachHeight) / 2);
        mPaint.setColor(mReachColor);
        mForegroundRect.right = progressX;
        canvas.drawRoundRect(mForegroundRect, mCorner, mCorner, mPaint);
        if (mProgress > 0 && mProgress < max && progressX > (mCorner >> 2)) {
            // 擦除右边的圆角
            mPaint.setColor(mReachColor);
            canvas.drawRect((mCorner >> 2), 0, progressX, mReachHeight, mPaint);
        }

        //绘制渐变进度条
        mPaint.setXfermode(mDstATopMode);
        int slopeWidth = (int) (mRhombusWidth * DEFAULT_SLOPE);  //斜率产生的偏移距离
        translateX = translateX % (mRhombusWidth << 1);
        int rhombusNum = (mRealWidth / mRhombusWidth) + 2;  //多绘制一个
        int starX;
        boolean is = translateX > mRhombusWidth;
        if (is) {
            starX = -(mRhombusWidth * 2 + slopeWidth) + translateX;
        } else {
            starX = -(mRhombusWidth + slopeWidth) + translateX;
        }
        for (int i = 0; i < rhombusNum; i++) {
            mSlopePath.reset();
            mSlopePath.moveTo(starX, 0);  //左上角
            mSlopePath.lineTo(starX + slopeWidth, mReachHeight);  //左下角
            mSlopePath.lineTo(starX + slopeWidth + mRhombusWidth, mReachHeight); //右下角
            mSlopePath.lineTo(starX + mRhombusWidth, 0); //右上角
            int color;
            if (is) {
                color = (i % 2 == 0) ? mFirstColor : mSecondColor;
            } else {
                color = (i % 2 == 0) ? mSecondColor : mFirstColor;
            }
            mPaint.setColor(color);
            canvas.drawPath(mSlopePath, mPaint);
            starX += mRhombusWidth;
        }

        mPaint.setXfermode(null);

        //绘制进度文字
        String text = mProgress + "%";
        int textWidth = (int) mPaint.measureText(text);
        int y = (int) (-(mPaint.descent() + mPaint.ascent()) / 2);
        mPaint.setColor(mTextColor);
        Log.d(TAG, String.format("UpdateProgressBar/onDraw:thread(%s)  text = %s ,textWidth = %d ,progressX = %f", Thread.currentThread(), text, textWidth, progressX));
        if (textWidth > progressX) {
            canvas.translate(0, mBackgroundHeight + mRectPadding + mTextOffset);
            canvas.drawText(text, 0, y, mPaint);
        } else {
            //当进度条足够长时 进度文字才跟随进度条移动
            canvas.translate(-textWidth, mBackgroundHeight + mRectPadding + mTextOffset);
            canvas.drawText(text, progressX, y, mPaint);
        }

        canvas.restore();

        //绘制背景
        canvas.save();
        canvas.translate(getPaddingLeft(), 0);
        mPaint.setColor(mBackgroundColor);
        canvas.drawRoundRect(mBackgroundRect, mCorner, mCorner, mPaint);
        canvas.restore();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (autoMock) {
            mock();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(mMockAction);
    }

    public int getMax() {
        return max;
    }

    public UpdateProgressBar setMax(int max) {
        this.max = max;
        return this;
    }

    /**
     * 是否自动mock
     *
     * @see #mock()
     */
    public UpdateProgressBar setAutoMock(boolean autoMock) {
        this.autoMock = autoMock;
        return this;
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

    public UpdateProgressBar setTextSize(int spSize) {
        this.mTextSize = sp2px(spSize);
        return this;
    }

    public UpdateProgressBar setTextOffset(int dpSize) {
        this.mTextOffset = dip2px(dpSize);
        return this;
    }

    public UpdateProgressBar setTextColor(int color) {
        this.mTextColor = color;
        return this;
    }

    public UpdateProgressBar setmBackgroundColor(int color) {
        this.mBackgroundColor = color;
        return this;
    }

    public UpdateProgressBar setReachColor(int color) {
        this.mReachColor = color;
        return this;
    }

    public UpdateProgressBar setSpeed(int speed) {
        this.mSpeed = speed;
        return this;
    }

    public UpdateProgressBar setRhombusWidth(int dpValue) {
        this.mRhombusWidth = dip2px(dpValue);
        return this;
    }

    private void mock() {
        postDelayed(mMockAction, 30);
    }

    /**
     * 测量高度
     *
     * @param heightMeasureSpec heightMeasureSpec
     */
    private int measureHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            //精确值 如20dp march_parent
            result = size;
        } else {
            //UNSPECIFIED & AT_MOST
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());
            //取最大值
            result = getPaddingTop() + getPaddingBottom()
                    + Math.max(mReachHeight, mBackgroundHeight) + mTextOffset + Math.abs(textHeight);

            if (mode == MeasureSpec.AT_MOST) {
                //有设置最大值
                result = Math.min(result, size);
            }
        }
        return result;
    }

    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
