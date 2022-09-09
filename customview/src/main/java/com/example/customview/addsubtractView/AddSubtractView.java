package com.example.customview.addsubtractView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.customview.R;
import com.example.customview.utils.NumUtil;
import com.example.customview.utils.SizeUtil;

public class AddSubtractView extends LinearLayout {
    private volatile int minNum=1;
    private volatile int maxNum=999;
    private volatile boolean isShowTip=true;

    private int num=minNum;
    private final TextView mTvSubtract;
    private final EditText mEtNum;
    private final TextView mTvAdd;
    private final View view;
    private final LinearLayout mLayout;
    private NumListener numListener;
    private Context context;

    public AddSubtractView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context=context;

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
            final Rect rect=new Rect();
            int oldHeight=0;
            @Override
            public void onGlobalLayout() {
                view.getWindowVisibleDisplayFrame(rect);
                int newHeight=rect.height();
                if(oldHeight<newHeight){ //未弹出软键盘
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
                if(content.isEmpty() || content.equals("-") || content.equals("+")) return;

                int num=NumUtil.parseInt(content);
                if(num<=minNum){
                    if(num<minNum) {
                        setNum(minNum);
                        if(isShowTip){
                            Toast.makeText(context, "已达下限", Toast.LENGTH_SHORT).show();
                        }
                    }
                    setClickable(mTvSubtract,false);
                    moveFocusToEnd();
                }else if(num>=maxNum){
                    if(num>maxNum) {
                        setNum(maxNum);
                        if(isShowTip){
                            Toast.makeText(context, "已达上限", Toast.LENGTH_SHORT).show();
                        }
                    }
                    setClickable(mTvAdd,false);
                    moveFocusToEnd();
                }else{
                    setClickable(mTvSubtract,true);
                    setClickable(mTvAdd,true);
                }
                if(numListener!=null) numListener.setNumListener(NumUtil.parseInt(mEtNum.getText().toString()));
            }
        });

        mTvSubtract.setOnClickListener(v -> {
            if(num<=minNum) {
                setClickable(mTvSubtract,false);
            }else {
                num= NumUtil.parseInt(mEtNum.getText().toString())-1;
                setNum(num);
            }

            cleanFocus();
        });

        mTvAdd.setOnClickListener(v -> {
            if(num>=maxNum) {
                setClickable(mTvAdd,false);
            }else {
                num= NumUtil.parseInt(mEtNum.getText().toString())+1;
                setNum(num);
            }

            cleanFocus();
        });
    }

    private void setClickable(TextView tv,boolean clickable){
        if(clickable){
            tv.setTextColor(getResources().getColor(R.color.c_585858));
        }else{
            tv.setTextColor(getResources().getColor(R.color.c_979797));
        }
        tv.setClickable(clickable);
    }

    private void cleanFocus(){
        if(mEtNum.hasFocus()){
            mEtNum.clearFocus();
        }
    }

    private void moveFocusToEnd(){
        try{
            mEtNum.requestFocus();
            mEtNum.setSelection(mEtNum.getText().toString().length());
        }catch (Exception e){
            mEtNum.setSelection(0);
        }
    }


    public int getNum(){
        return NumUtil.parseInt(mEtNum.getText().toString());
    }

    public void setNumListener(NumListener numListener){
        this.numListener=numListener;
    }

    public void setMinNum(int minNum){
        this.minNum=minNum;
        if(minNum>=0){
            mEtNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else{
            mEtNum.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_SIGNED);
        }
    }

    public void setMaxNum(int maxNum){
        this.maxNum=maxNum;
    }

    public void setNum(int num){
        mEtNum.setText(num+"");
    }

    public void showTip(boolean isShowTip){
        this.isShowTip=isShowTip;
    }


}
