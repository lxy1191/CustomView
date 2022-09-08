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
import androidx.annotation.RequiresApi;

import com.example.customview.R;
import com.example.customview.utils.SizeUtil;

public class AddSubtractView extends LinearLayout {
    int num=1;
    private final TextView mTvSubtract;
    private final EditText mEtNum;
    private final TextView mTvAdd;
    private final View view;
    private final LinearLayout mLayout;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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
            mLayout.setBackground(getBackground());
        }

        mTvSubtract.setTextSize(symbolSize);
        mTvAdd.setTextSize(symbolSize);
        mEtNum.setTextSize(contentSize);

        listener();
    }


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void listener(){
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            Rect rect=new Rect();
            int oldHeight=0;
            @Override
            public void onGlobalLayout() {
                view.getWindowVisibleDisplayFrame(rect);
                int newHeight=rect.height();
                if(newHeight>oldHeight){ //未弹出软键盘
                    if(mEtNum.getText().toString().isEmpty()) mEtNum.setText("1");
                    mEtNum.clearFocus();
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
                if(content.isEmpty()) return;

                if(Integer.parseInt(content)<=1){
                    unSubtract();
                }else{
                    subtract();
                }
            }
        });

        mTvSubtract.setOnClickListener(v -> {
            if(num<=1) {
                unSubtract();
            }else {
                num=Integer.parseInt(mEtNum.getText().toString())-1;
                mEtNum.setText(num+"");
            }
        });

        mTvAdd.setOnClickListener(v -> {
            num=Integer.parseInt(mEtNum.getText().toString())+1;
            mEtNum.setText(num+"");
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


}
