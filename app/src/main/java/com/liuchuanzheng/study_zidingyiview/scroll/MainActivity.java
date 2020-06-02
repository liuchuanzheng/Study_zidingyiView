package com.liuchuanzheng.study_zidingyiview.scroll;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.liuchuanzheng.study_zidingyiview.R;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mLayout = (LinearLayout) findViewById(R.id.layout);
        //不管是 scrollTo() 还是 scrollBy() 方法，滚动的都是该 View 内部的内容
        Button btn_scrollto = findViewById(R.id.btn_scrollto);
        btn_scrollto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("test", "点击前getScrollX值：" + mLayout.getScrollX());
                Log.e("test", "点击前getScrollY值：" + mLayout.getScrollY());
                mLayout.scrollTo(-50, -50);
                Log.e("test", "点击后getScrollX值：" + mLayout.getScrollX());
                Log.e("test", "点击后getScrollY值：" + mLayout.getScrollY());
            }
        });

        findViewById(R.id.btn_scrollby).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("test", "点击前getScrollX值：" + mLayout.getScrollX());
                Log.e("test", "点击前getScrollY值：" + mLayout.getScrollY());
                mLayout.scrollBy(-50, -50);
                Log.e("test", "点击后getScrollX值：" + mLayout.getScrollX());
                Log.e("test", "点击后getScrollY值：" + mLayout.getScrollY());
            }
        });

//        ObjectAnimator anim = ObjectAnimator.ofFloat(btn_scrollto, "translationX", 0, 100);
//        anim.setDuration(3000);
//        anim.start();

        //通过改变 View 的 LayoutParams 使 View 重新布局从而实现滑动.View 移动后的位置就是其真实的位置
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) btn_scrollto.getLayoutParams();
        lp.leftMargin += 300;
        btn_scrollto.setLayoutParams(lp);
    }
}