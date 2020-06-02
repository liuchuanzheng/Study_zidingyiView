package com.liuchuanzheng.study_zidingyiview.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.liuchuanzheng.study_zidingyiview.R;
import com.liuchuanzheng.study_zidingyiview.floatview2.SystemUtils;

/**
 * @author 刘传政
 * @date 2020/6/2 14:02
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
public class TestView extends View {
    public TestView(Context context) {
        this(context,null);
    }
    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //解析自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TestView);
        String titleText = typedArray.getString(R.styleable.TestView_titleText);
        int titleTextColor = typedArray.getColor(R.styleable.TestView_titleTextColor, Color.BLACK);
        float titleTextSize = typedArray.getDimension(R.styleable.TestView_titleTextSize, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //传递进来的两个参数是父布局和xml配置,综合考虑后给我们的一个建议
        //一般我们只处理AT_MOST(也就是xml指定了wrap_content).也就是说这种情况Android不知道你的包裹内容到底多大合适
        //只能给你最大值处理了.
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width ;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY) {
            //既然是确定模式的.我们就照搬使用就好了
            width = widthSize;
        }else {
            //也就是包裹内容情况或UNSPECIFIED(遇不到)
            //这里默认100px外加左右两边的padding
            width = (int) (getPaddingLeft() + 100 + getPaddingRight());
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            //既然是确定模式的.我们就照搬使用就好了
            height = heightSize;
        }else {
            //也就是包裹内容情况或UNSPECIFIED(遇不到)
            //这里默认100px外加上下两边的padding
            height = (int) (getPaddingTop() + 100 + getPaddingBottom());
        }
        //最后调用设置方法.让我们自定义的宽高生效
        setMeasuredDimension(width, height);
    }
    Paint paint = new Paint();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //getWidth()和getHeight()是包括了padding的总长.
        paint.setColor(Color.RED);
        canvas.drawCircle(getWidth()/2,getHeight()/2,Math.min(getWidth()/2,getHeight()/2),paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //对于简单的触摸,无非就是根据手指的触摸变化,改变view的绘制的数据.不停的调用重绘方法
        //ondraw被回调时,根据新数据重新绘制样子.你看到的就是view随手指改变了.
        //实际上触摸和view绘制毫无联系,是我们通过逻辑让他俩建立了联系.
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }
}
