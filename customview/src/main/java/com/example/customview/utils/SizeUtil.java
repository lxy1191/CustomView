package com.example.customview.utils;

import android.content.res.Resources;
import android.util.TypedValue;

public class SizeUtil {
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public static int px2dip(float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static float dip2px(float dipValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dipValue, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp( float pxValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static float sp2px(float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spValue, Resources.getSystem().getDisplayMetrics());
    }

}
