package com.example.customview.utils;

import android.view.View;

public class ViewLocationUtil {
    public static int getViewLocationYInScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[1];
    }
}
