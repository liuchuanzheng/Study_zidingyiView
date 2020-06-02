package com.liuchuanzheng.study_zidingyiview.floatview2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.imuxuan.floatingview.FloatingView;
import com.liuchuanzheng.study_zidingyiview.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test4);
        FloatView2Maneger.getInstance().customView(R.layout.layout_floating_view);
        FloatView2Maneger.getInstance().add();
        FloatView2Maneger.getInstance().listener(new FloatViewListener() {
            @Override
            public void onClick(FloatView floatView) {

            }
        });
        FloatView2Maneger.getInstance().hide();
        FloatView2Maneger.getInstance().show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        FloatView2Maneger.getInstance().attach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FloatView2Maneger.getInstance().detach(this);
    }
}