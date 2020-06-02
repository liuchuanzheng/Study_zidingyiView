package com.liuchuanzheng.study_zidingyiview.step;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.liuchuanzheng.study_zidingyiview.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        StepView stepView = findViewById(R.id.stepView);
        stepView.setCurrentCount(8000,5000);
    }
}