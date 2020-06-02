package com.liuchuanzheng.study_zidingyiview.step;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.liuchuanzheng.study_zidingyiview.R;

/**
 * @author 刘传政
 * @date 2020/6/1 13:55
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
public class StepView extends View {
    /*圆弧宽度*/
    private float borderWidth = 38f;
    /* 画步数的数值的字体大小*/
    private float numberTextSize = 0;
    /**
     * 开始绘制圆弧的角度
     */
    private float startAngle = 135;
    /**
     * 终点对应的角度和起始点对应的角度的夹角
     */
    private float angleLength = 270;
    /**
     * 所要绘制的当前步数的红色圆弧终点到起点的夹角
     */
    private float currentAngleLength = 0;
    private String stepNumber;
    /**
     * 动画时长
     */
    private int animationLength = 3000;

    public StepView(Context context) {
        super(context);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*中心点坐标*/
        float centerX = (getWidth()) / 2;
        /*指定圆弧的外轮廓矩形区域*/
        RectF rectF = new RectF(0 + borderWidth, borderWidth, 2 * centerX - borderWidth, 2 * centerX - borderWidth);
        /*绘制红色圆弧*/
        drawArcYellow(canvas, rectF);
        /*绘制蓝色走过步数*/
        drawArcRed(canvas, rectF);
        /*文字*/
        drawTex(canvas, rectF);
    }

    private void drawTex(Canvas canvas, RectF rectF) {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setAntiAlias(true);
        paint.setTextSize(32);
        int length = String.valueOf(currentAngleLength).length();
        canvas.drawText(currentAngleLength + "", rectF.centerX() - 70, getHeight() / 2, paint);
    }

    /**
     * 1.绘制总步数的黄色圆弧
     *
     * @param canvas 画笔
     * @param rectF  参考的矩形
     */
    private void drawArcYellow(Canvas canvas, RectF rectF) {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorAccent));
        /** 结合处为圆弧*/
        paint.setStrokeJoin(Paint.Join.ROUND);
        /** 设置画笔的样式 Paint.Cap.Round ,Cap.SQUARE等分别为圆形、方形*/
        paint.setStrokeCap(Paint.Cap.ROUND);
        /** 设置画笔的填充样式 Paint.Style.FILL  :填充内部;Paint.Style.FILL_AND_STROKE  ：填充内部和描边;  Paint.Style.STROKE  ：仅描边*/
        paint.setStyle(Paint.Style.STROKE);
        /**抗锯齿功能*/
        paint.setAntiAlias(true);
        /**设置画笔宽度*/
        paint.setStrokeWidth(38f);
        canvas.drawArc(rectF, startAngle, angleLength, false, paint);
    }

    /**
     * 2.绘制当前步数的蓝色圆弧
     *
     * @param canvas
     * @param rectF
     */
    private void drawArcRed(Canvas canvas, RectF rectF) {
        Paint paint = new Paint();
        /**设置结合处的样子，Miter:结合处为锐角， Round:结合处为圆弧：BEVEL：结合处为直线。*/
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);//设置填充样式
        /*** 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式
         Cap.ROUND,或方形样式Cap.SQUARE   */
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);//抗锯齿功能
        paint.setStrokeWidth(borderWidth);//设置画笔宽度
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        canvas.drawArc(rectF, startAngle, currentAngleLength, false, paint);

    }

    /**
     * 所走的步数进度
     *
     * @param totalStepNum  设置的步数
     * @param currentCounts 所走步数
     */
    public void setCurrentCount(int totalStepNum, int currentCounts) {
        stepNumber = currentCounts + "";
/**如果当前走的步数超过总步数则圆弧还是270度，不能成为园*/
        if (currentCounts > totalStepNum) {
            currentCounts = totalStepNum;
        }
/**所走步数占用总共步数的百分比*/
        float scale = (float) currentCounts / totalStepNum;
/**换算成弧度最后要到达的角度的长度-->弧长*/
        float currentAngleLength = scale * angleLength;
/**开始执行动画*/
        setAnimation(0, currentAngleLength, animationLength);
    }

    /**
     * 为进度设置动画
     * ValueAnimator是整个属性动画机制当中最核心的一个类，属性动画的运行机制是通过不断地对值进行操作来实现的，
     * 而初始值和结束值之间的动画过渡就是由ValueAnimator这个类来负责计算的。
     * 它的内部使用一种时间循环的机制来计算值与值之间的动画过渡，
     * 我们只需要将初始值和结束值提供给ValueAnimator，并且告诉它动画所需运行的时长，
     * 那么ValueAnimator就会自动帮我们完成从初始值平滑地过渡到结束值这样的效果。
     *
     * @param last
     * @param current
     */
    private void setAnimation(float last, float current, int length) {
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(last, current);
        progressAnimator.setDuration(length);
        progressAnimator.setTarget(currentAngleLength);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAngleLength = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        progressAnimator.start();
    }
}
