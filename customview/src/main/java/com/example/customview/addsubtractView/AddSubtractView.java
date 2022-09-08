package com.example.customview.addsubtractView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.customview.R;
import com.example.customview.utils.NumUtils;
import com.example.customview.utils.SizeUtil;

public class AddSubtractView extends LinearLayout {
    private volatile int minNum=1;
    private int num=minNum;
    private final TextView mTvSubtract;
    private final EditText mEtNum;
    private final TextView mTvAdd;
    private final View view;
    private final LinearLayout mLayout;
    private NumListener numListener;

    public AddSubtractView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        view = LayoutInflater.from(context).inflate(R.layout.view_add_subtract,this,true);
        mTvSubtract = view.findViewById(R.id.tv_subtract);
        mEtNum = view.findViewById(R.id.et_num);
        mTvAdd = view.findViewById(R.id.tv_add);
        mLayout=view.findViewById(R.id.layout);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AddSubtractView);
        //获取title_text属性
        float symbolSize =  SizeUtil.sp2px(typedArray.getDimensionPixelSize(R.styleable.AddSubtractView_symbolSize,10));
        float contentSize =  SizeUtil.dip2px(typedArray.getDimensionPixelSize(R.styleable.AddSubtractView_contentSize,8));

        if(getBackground()!=null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mLayout.setBackground(getBackground());
            }
        }

        mTvSubtract.setTextSize(symbolSize);
        mTvAdd.setTextSize(symbolSize);
        mEtNum.setTextSize(contentSize);

        listener();
    }


    private void listener(){
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            Rect rect=new Rect();
            int oldHeight=0;
            @Override
            public void onGlobalLayout() {
                view.getWindowVisibleDisplayFrame(rect);
                int newHeight=rect.height();
                if(newHeight>oldHeight){ //未弹出软键盘
                    if(mEtNum.getText().toString().isEmpty())  setNum(minNum);
                    mEtNum.clearFocus();

                    if(numListener!=null) numListener.setNumListener(minNum);
                }
                oldHeight=newHeight;
            }
        });

        mEtNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content=s.toString();
                if(content.isEmpty() || content.equals("-")) return;

                if(NumUtils.parseInt(content)<=minNum){
                    unSubtract();
                }else{
                    subtract();
                }
                
                if(numListener!=null) numListener.setNumListener(NumUtils.parseInt(content));
            }
        });

        mTvSubtract.setOnClickListener(v -> {
            if(num<=minNum) {
                unSubtract();
            }else {
                num=NumUtils.parseInt(mEtNum.getText().toString())-1;
                setNum(num);
            }
            movePointToLast();
        });

        mTvAdd.setOnClickListener(v -> {
            num=NumUtils.parseInt(mEtNum.getText().toString())+1;
            setNum(num);
            movePointToLast();
        });
    }

    private void unSubtract(){
        mTvSubtract.setTextColor(getResources().getColor(R.color.c_979797));
        mTvSubtract.setClickable(false);
    }

    private void subtract(){
        mTvSubtract.setTextColor(getResources().getColor(R.color.c_585858));
        mTvSubtract.setClickable(true);
    }

    public int getNum(){
        return NumUtils.parseInt(mEtNum.getText().toString());
    }

    public void setNumListener(NumListener numListener){
        this.numListener=numListener;
    }

    public void setMinNum(int minNum){
        this.minNum=minNum;
    }

    public void setNum(int num){
        mEtNum.setText(num+"");
    }

    private void movePointToLast(){
        if(mEtNum.hasFocus()){
            mEtNum.requestFocus(); //请求焦点
            mEtNum.setSelection(mEtNum.getText().toString().length());//移动光标至指定位置
        }
    }


}
