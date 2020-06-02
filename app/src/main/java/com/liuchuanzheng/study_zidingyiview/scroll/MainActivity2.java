package com.liuchuanzheng.study_zidingyiview.scroll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.liuchuanzheng.study_zidingyiview.R;

public class MainActivity2 extends AppCompatActivity {
    private ScrollerLinearLayout mLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mLayout = (ScrollerLinearLayout) findViewById(R.id.layout);

    }
    public void start(View v){
        mLayout.startScroll();
    }
}