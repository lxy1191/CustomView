package com.example.customview;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.addsubtractView.AddSubtractView;
import com.example.customview.addsubtractView.NumListener;
import com.example.customview.scrollView.AutoScrollView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String TAG="MainActivity";
    private TextView tv;
    private AutoScrollView asv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList list=new ArrayList();
        for(int i=0;i<10;i++){
            list.add(i+"");
        }

////        tv = findViewById(R.id.tv);
//        asv = findViewById(R.id.asv);
//        String str="sjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj";
////        tv.setText(str);
////        asv.setText(str);
//        asv.setTextList(list);

        AddSubtractView view=findViewById(R.id.numView);
        view.setMinNum(-10);
        view.setMaxNum(1);
        view.setNumListener(new NumListener() {
            @Override
            public void setNumListener(int num) {
                Log.e("qqq",num+"");
            }
        });

//        View rootView= getWindow().getDecorView().findViewById(android.R.id.content);
//        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { //视图树发生变化时调用
//            Rect rect=new Rect();
//            int oldHeight=0;
//            @Override
//            public void onGlobalLayout() {
//                rootView.getWindowVisibleDisplayFrame(rect);
//                int newHeight=rect.height();
//                if(newHeight<oldHeight){ //弹出软键盘
//                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY",  -500);
//                    objectAnimator.setDuration(1000);
//                    objectAnimator.start();
//                }else if(newHeight!=oldHeight){
//                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", 0);
//                    objectAnimator.setDuration(1000);
//                    objectAnimator.start();
//                }
//                oldHeight=newHeight;
//            }
//        });



    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

//        Paint.FontMetrics metrics= tv.getPaint().getFontMetrics();
//        float textHeight=Math.abs(metrics.descent-metrics.ascent);
//
//        Log.e("getLineSpacingMultiplier", tv.getLineSpacingMultiplier()+"-");
//        Log.e("getLineCount", tv.getLineCount()+"-");
//        Log.e("getLineHeight", tv.getLineHeight()+"-");
//
//        Log.e("height", tv.getHeight()+"-");
//        Log.e("textHeight",textHeight+"-");

        ///87*1326
    }
}