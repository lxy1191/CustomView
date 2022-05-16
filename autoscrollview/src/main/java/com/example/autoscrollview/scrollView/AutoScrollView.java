package com.example.autoscrollview.scrollView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.autoscrollview.R;

import java.util.List;

public class AutoScrollView extends LinearLayout {
    private int orientation= LinearLayout.HORIZONTAL;
    private int duration=0;
    private int showLine=1;

    private TempScrollView tempView;

    public AutoScrollView(Context context) {
        super(context);

        initView(context);
    }

    public AutoScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView(context);
        initAttrs(context,attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        if(heightMode==MeasureSpec.AT_MOST){
            int lineHeight=tempView.getSingleLineHeight()*showLine;
            heightMeasureSpec=MeasureSpec.makeMeasureSpec(lineHeight,heightMode);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if(orientation==HORIZONTAL){
            int textWidth=tempView.getWidth();
            int screenWidth=tempView.getScreenWidth();
            ObjectAnimator transXAnim = ObjectAnimator.ofFloat(tempView, "translationX", screenWidth, -textWidth);
            transXAnim.setRepeatCount(Animation.INFINITE);
            AnimatorSet set = new AnimatorSet();
            set.playSequentially(transXAnim);
            set.setInterpolator(new LinearInterpolator()); //设置速率
            if (duration == 0)
                duration = textWidth > screenWidth ? textWidth * 4 : screenWidth * 4;
            set.setDuration(duration);
            set.start();
        }else{
            int lineHeight=tempView.getSingleLineHeight()*showLine;
            ObjectAnimator transYAnim = ObjectAnimator.ofFloat(tempView, "translationY", lineHeight, -tempView.getHeight());
            transYAnim.setRepeatCount(Animation.INFINITE);
            AnimatorSet set = new AnimatorSet();
            set.playSequentially(transYAnim);
            set.setInterpolator(new LinearInterpolator()); //设置速率
            if (duration == 0)
                duration = 1000*((int)Math.ceil(tempView.getHeight()*1.0/lineHeight));
            set.setDuration(duration);
            set.start();
        }
    }

    private void initView(Context context){
        View view=LayoutInflater.from(context).inflate(R.layout.view_autoscroll,this);
        tempView = view.findViewById(R.id.tempScrollView);
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoScrollView);
        orientation=typedArray.getInt(R.styleable.AutoScrollView_android_orientation,LinearLayout.HORIZONTAL);
        duration=typedArray.getInt(R.styleable.AutoScrollView_duration,0);

        showLine =typedArray.getInt(R.styleable.AutoScrollView_showLine,1);
        int color=typedArray.getColor(R.styleable.AutoScrollView_android_textColor,0);
        float textSize=typedArray.getDimension(R.styleable.AutoScrollView_android_textSize,-1);
        String text=typedArray.getString(R.styleable.AutoScrollView_android_text);

        tempView.setOrientation(orientation);
        if(textSize!=-1){
            tempView.setTextSize(textSize);
        }
        if(color!=0){
            tempView.setTextColor(color);
        }
        tempView.setText(text);
    }

    public void setText(String content){
        tempView.setText(content);
        requestLayout();
    }

    public void setTextList(List<String> list){
        tempView.setTextList(list);
        requestLayout();
    }

    public void setShowOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setShowLine(int showLine) {
        this.showLine = showLine;
    }
}
