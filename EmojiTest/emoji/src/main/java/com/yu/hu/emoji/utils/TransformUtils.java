package com.yu.hu.emoji.utils;

import android.content.Context;

/**
 * Created by Hy on 2019/12/30 18:15
 **/
public class TransformUtils {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
