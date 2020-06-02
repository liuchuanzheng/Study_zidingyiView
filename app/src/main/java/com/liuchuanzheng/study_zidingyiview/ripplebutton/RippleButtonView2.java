package com.liuchuanzheng.study_zidingyiview.ripplebutton;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

/**
 * @author 刘传政
 * @date 2020/6/1 11:32
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
public class RippleButtonView2 extends AppCompatButton {
    float mCenterX;
    float mCenterY;
    private Paint mPaint = new Paint();
    //是否是选中状态
    private boolean isChecked = false;
    float radius;
    ValueAnimator valueAnimator;

    public RippleButtonView2(Context context) {
        this(context, null);
    }

    public RippleButtonView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleButtonView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                mCenterX = event.getX();
                mCenterY = event.getY();

                startAnimator();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isChecked) {
            mPaint.setColor(Color.WHITE);
        } else {
            mPaint.setColor(Color.RED);
        }
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mCenterX,mCenterY,radius,mPaint);

    }

    private void startAnimator(){
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofFloat(0f,(float) Math.hypot(getMeasuredWidth(), getMeasuredHeight()));
            valueAnimator.setDuration(1*1000L);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    radius = (float) animation.getAnimatedValue();
                    //刷新
                    invalidate();
                }
            });
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isChecked  = !isChecked;
                    if (isChecked) {
                        setTextColor(Color.WHITE);
                        setBackgroundColor(Color.RED);
                        setText("未关注");
                    } else {
                        setTextColor(Color.BLACK);
                        setBackgroundColor(Color.WHITE);
                        setText("关注");
                    }

                    radius = 0;
                    invalidate();
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
}
