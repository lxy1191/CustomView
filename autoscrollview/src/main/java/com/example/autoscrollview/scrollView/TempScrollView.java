package com.example.autoscrollview.scrollView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

@SuppressLint("AppCompatCustomView")
public class TempScrollView extends TextView {
    private int orientation = LinearLayout.HORIZONTAL;
    private int singleLineHeight;
    private int screenWidth;

    public TempScrollView(Context context) {
        super(context);
    }

    public TempScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        screenWidth = MeasureSpec.getSize(widthMeasureSpec);
        Paint.FontMetrics metrics = getPaint().getFontMetrics();
        singleLineHeight = (int) Math.abs(metrics.descent - metrics.ascent);

        if (orientation == LinearLayout.HORIZONTAL) {
            int textWidth = (int) getPaint().measureText(getText().toString());
            setMeasuredDimension(textWidth, singleLineHeight);
        } else {
            setMeasuredDimension(screenWidth, singleLineHeight * (getLineNum(getText().toString(),screenWidth)));
        }
    }

    private int getLineNum(String str,int screenWidth){
        int lineNum=0;
        String[] strs=str.split("\n");
        for(String s:strs){
            lineNum+=Math.ceil(getPaint().measureText(s)*1.0/screenWidth);
        }
        return lineNum;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getSingleLineHeight() {
        return singleLineHeight;
    }


    public void setOrientation(int orientation){
        this.orientation=orientation;
    }

    public void setTextList(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str);
            if (orientation == LinearLayout.HORIZONTAL) {
                sb.append("                              ");
            }else{
                sb.append("\n");
            }
        }
        setText(sb.toString());
        requestLayout();
    }
}
