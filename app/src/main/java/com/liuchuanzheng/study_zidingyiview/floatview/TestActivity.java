package com.liuchuanzheng.study_zidingyiview.floatview;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.imuxuan.floatingview.FloatingView;
import com.liuchuanzheng.study_zidingyiview.R;

public class TestActivity extends AppCompatActivity {
    private WindowManager mWindowManager;
    private View mFloatingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
//        initCommonFloatView();
//        show();
        FloatingView.get().add();

    }

    private void show() {
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_view, null);
        //设置WindowManger布局参数以及相关属性
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //初始化位置
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 100;
        params.y = 500;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //获取WindowManager对象
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);
        //FloatingView的拖动事件
        mFloatingView.setOnTouchListener(new View.OnTouchListener() {
            //获取X坐标
            private int startX;
            //获取Y坐标
            private int startY;
            //初始化X的touch坐标
            private float startTouchX;
            //初始化Y的touch坐标
            private float startTouchY;
            //移动时X的touch坐标
            private float moveTouchX;
            //移动时Y的touch坐标
            private float moveTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = params.x;
                        startY = params.y;
                        startTouchX = event.getRawX();
                        startTouchY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveTouchX = event.getRawX();
                        moveTouchY = event.getRawY();
                        params.x = startX + (int) (moveTouchX - startTouchX);
                        params.y = startY + (int) (moveTouchY - startTouchY);
                        if (params.x <= 0) {
                            params.x = 0;
                        }
                        if (params.y + mFloatingView.getHeight() > getScreenHeight(TestActivity.this)) {
                            params.y = getScreenHeight(TestActivity.this);
                        }
                        //更新View的位置
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        //更新View的位置
                        params.x = getScreenWidth(TestActivity.this)-mFloatingView.getWidth();
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 直接在activity根布局添加悬浮窗
     */
    private void initCommonFloatView() {
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_view, null);
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dpToPx(100), dpToPx(100));
        params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        params.topMargin = (int) (0);
        params.leftMargin = (int) (0);

        mFloatingView.setLayoutParams(params);
        //FloatingView的拖动事件
        mFloatingView.setOnTouchListener(new View.OnTouchListener() {
            //获取X坐标
            private int startX;
            //获取Y坐标
            private int startY;
            //初始化X的touch坐标
            private float startTouchX;
            //初始化Y的touch坐标
            private float startTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = params.width;
                        startY = params.height;
                        float x = mFloatingView.getX();
                        mFloatingView.setX(x + 10);
                        return true;
                    case MotionEvent.ACTION_MOVE:

                        return true;
                }
                return false;
            }
        });
        View rootView = this.getWindow().getDecorView().getRootView();
        FrameLayout contentView = (FrameLayout) rootView.findViewById(android.R.id.content);
        contentView.addView((View) mFloatingView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FloatingView.get().attach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FloatingView.get().detach(this);
    }

    private int dpToPx(float dp) {
        float density = this.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
    }

    public int spToPx(float spValue) {
        float fontScale = this.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽
     *
     * @param context
     * @return
     */
    public int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }

    /**
     * 获取屏幕高
     *
     * @param context
     * @return
     */
    public int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        int height = dm.heightPixels;
        return height;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFloatingView != null) {
            mFloatingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mFloatingView != null) {
            mFloatingView.setVisibility(View.GONE);
        }
    }
}