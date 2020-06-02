package com.liuchuanzheng.study_zidingyiview.floatview2;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.imuxuan.floatingview.MagnetViewListener;


/**
 * @author 刘传政
 * @date 2020/6/2 09:05
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
public class FloatView extends FrameLayout {
    String TAG = getClass().getSimpleName();
    protected int mScreenWidth;
    private int mScreenHeight;
    private int mStatusBarHeight;
    private float mOriginalRawX;
    private float mOriginalRawY;
    private float mOriginalX;
    private float mOriginalY;
    private long mLastTouchDownTime;
    private FloatViewListener listener;
    private static final int TOUCH_TIME_THRESHOLD = 150;
    ValueAnimator valueAnimator;
    CountDownTimer countDownTimer = null;
    float firstAnimalX = getX();
    public FloatView(@NonNull Context context) {
        this(context, null);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        mStatusBarHeight = SystemUtils.getStatusBarHeight(getContext());
        setClickable(true);
        updateSize();
    }
    public void setFloatViewListener(FloatViewListener listener) {
        this.listener = listener;
    }
    protected void updateSize() {
        mScreenWidth = (SystemUtils.getScreenWidth(getContext()) - this.getWidth());
        mScreenHeight = SystemUtils.getScreenHeight(getContext());
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event == null) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                if (countDownTimer!= null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                changeOriginalTouchParams(event);
                updateSize();
                break;
            case MotionEvent.ACTION_MOVE:
                updateViewPosition(event);
                break;
            case MotionEvent.ACTION_UP:
                if (isOnClickEvent()) {
                    dealClickEvent();
                }
                setX(SystemUtils.getScreenWidth(getContext())-getWidth());
                startHideAnimator();
                break;
        }
        return true;
    }
    private void changeOriginalTouchParams(MotionEvent event) {
        mOriginalX = getX();
        mOriginalY = getY();
        mOriginalRawX = event.getRawX();
        mOriginalRawY = event.getRawY();
        mLastTouchDownTime = System.currentTimeMillis();
    }
    private void updateViewPosition(MotionEvent event) {
        //目标x
        float desX = mOriginalX + event.getRawX() - mOriginalRawX;
        //右边缘超出屏幕
        if (desX+getWidth() > SystemUtils.getScreenWidth(getContext())) {
            desX = SystemUtils.getScreenWidth(getContext())-getWidth();
        }
        //左边缘超出屏幕
        if (desX < 0){
            desX = 0;
        }
        setX(desX);
        // 限制不可超出屏幕高度
        float desY = mOriginalY + event.getRawY() - mOriginalRawY;
        if (desY < mStatusBarHeight) {
            desY = mStatusBarHeight;
        }
        if (desY > mScreenHeight - getHeight()) {
            desY = mScreenHeight - getHeight();
        }
        setY(desY);
    }
    protected boolean isOnClickEvent() {
        return System.currentTimeMillis() - mLastTouchDownTime < TOUCH_TIME_THRESHOLD;
    }
    protected void dealClickEvent() {
        if (listener != null) {
            listener.onClick(this);
        }
    }
    //半隐藏动画
    private void startHideAnimator(){
        firstAnimalX = getX();
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
                    valueAnimator = ValueAnimator.ofFloat(0,getWidth()/2L);
                    valueAnimator.setDuration(1*1000L);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {

                            setX(firstAnimalX + (float)animation.getAnimatedValue());
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
}
