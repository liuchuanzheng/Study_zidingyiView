package com.liuchuanzheng.study_zidingyiview.floatview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.liuchuanzheng.study_zidingyiview.R;
import com.liuchuanzheng.study_zidingyiview.floatview.FloatView;

public class Test2Activity extends AppCompatActivity {
    FloatView floatView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        floatView = new FloatView(this) {
            @Override
            public int getResourcesId() {
                return R.layout.layout_floating_view;
            }
        };
        floatView.show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (floatView != null) {
            floatView.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (floatView != null) {
            floatView.hide();
        }
    }
}