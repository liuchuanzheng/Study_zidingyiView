package com.liuchuanzheng.study_zidingyiview.floatview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.liuchuanzheng.study_zidingyiview.R;

import static android.content.Context.WINDOW_SERVICE;

/**
 * @author 刘传政
 * @date 2020/6/1 16:06
 * QQ:1052374416
 * 电话:18501231486
 * 作用: 传统的方式.需要申请悬浮窗权限.
 *       提供拖动,靠边,半隐藏功能
 * 注意事项:
 */
public abstract class FloatView extends RelativeLayout implements View.OnTouchListener {
    private WindowManager mWindowManager;
    private View mContentView;
    Context context;
    WindowManager.LayoutParams contentParams;
    ValueAnimator valueAnimator;
    CountDownTimer countDownTimer = null;
    public FloatView(Context context) {
        this(context,null);

    }

    public FloatView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }


    private void init() {
        mContentView = LayoutInflater.from(context).inflate(getResourcesId(), null);
        //设置WindowManger布局参数以及相关属性
        contentParams = new WindowManager.LayoutParams();
        contentParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        contentParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //初始化位置
        contentParams.gravity = Gravity.TOP | Gravity.LEFT;
        contentParams.x = 0;
        contentParams.y = 0;
        contentParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            contentParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            contentParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        contentParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        contentParams.format = PixelFormat.TRANSLUCENT;
        //获取WindowManager对象
        mWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mContentView, contentParams);
        mContentView.setOnTouchListener(this);
    }

    public abstract int getResourcesId();

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
    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                if (countDownTimer!= null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                startX = contentParams.x;
                startY = contentParams.y;
                startTouchX = event.getRawX();
                startTouchY = event.getRawY();
                if (contentParams.x <= 0) {
                    contentParams.x = 0;
                }
                if (contentParams.x + mContentView.getWidth() > getScreenWidth(context)) {
                    contentParams.x = getScreenWidth(context)-mContentView.getWidth();
                }
                if (contentParams.y <= 0) {
                    contentParams.y = 0;
                }
                if (contentParams.y + mContentView.getHeight() > getScreenHeight(context)) {
                    contentParams.y = getScreenHeight(context)-mContentView.getHeight();
                }
                //更新View的位置
                mWindowManager.updateViewLayout(mContentView, contentParams);
                mContentView.setTranslationX(0);
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouchX = event.getRawX();
                moveTouchY = event.getRawY();
                contentParams.x = startX + (int) (moveTouchX - startTouchX);
                contentParams.y = startY + (int) (moveTouchY - startTouchY);
                if (contentParams.x <= 0) {
                    contentParams.x = 0;
                }
                if (contentParams.x + mContentView.getWidth() > getScreenWidth(context)) {
                    contentParams.x = getScreenWidth(context)-mContentView.getWidth();
                }
                if (contentParams.y <= 0) {
                    contentParams.y = 0;
                }
                if (contentParams.y + mContentView.getHeight() > getScreenHeight(context)) {
                    contentParams.y = getScreenHeight(context)-mContentView.getHeight();
                }
                //更新View的位置
                mWindowManager.updateViewLayout(mContentView, contentParams);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //更新View的位置
                contentParams.x = getScreenWidth(context)-mContentView.getWidth();
                mWindowManager.updateViewLayout(mContentView, contentParams);
                startHideAnimator();
                break;
        }
        return true;
    }
    //半隐藏动画
    private void startHideAnimator(){
        countDownTimer = new CountDownTimer(5000, 1000) {

            /**
             * Callback fired on regular interval.
             *
             * @param millisUntilFinished The amount of time until finished.
             */
            @Override
            public void onTick(long millisUntilFinished) {

            }

            /**
             * Callback fired when the time is up.
             */
            @Override
            public void onFinish() {
                if (valueAnimator == null) {
                    valueAnimator = ValueAnimator.ofInt(0,mContentView.getWidth()/2);
                    valueAnimator.setDuration(1*1000L);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mContentView.setTranslationX((int)animation.getAnimatedValue());
                        }
                    });
                    valueAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                }else {
                    valueAnimator.cancel();
                }

                valueAnimator.start();
            }
        };
        countDownTimer.start();

    }
    public void show(){
        if (getVisibility() != View.VISIBLE) {
            setVisibility(View.VISIBLE);
            mContentView.setVisibility(VISIBLE);
        }
    }



    /**
     * 隐藏悬浮窗
     */
    public void hide() {
        setVisibility(View.GONE);
        mContentView.setVisibility(GONE);
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

}
