package com.liuchuanzheng.study_zidingyiview.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 我们在调用 startScroll() 方法后又调用了 invalidate() 方法使 View 进行了重绘。
 * 那么这时就会走 View 的 draw 方法，在 View 的 draw 方法中又会去调用 View 的 computeScroll() 方法，
 * 而该方法在 View 中是一个空实现。这就解释了我们为什么需要复写 computeScroll() 方法。
 * 在 computeScroll() 方法中，我们调用了 computeScrollOffset() 方法进行判断，
 * 该方法内部会根据时间的流逝来计算出 scrollX 和 scrollY 改变的百分比并计算出当前的值。
 * 这个方法的返回值也很重要，返回 true 表示滑动还未结束，false 表示滑动已经结束。
 * 所以在这里，我们进行了判断，当其返回 true 时，就调用 scrollTo() 方法使 View 滑动，
 * 并调用 invalidate() 重绘，只要滑动没有完成就继续递归下去。
 *
 */
public class ScrollerLinearLayout extends LinearLayout {

    private final Scroller mScroller;

    public ScrollerLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

   //触发mScroller的startScroll。
    //开始后就会一直随时间走下去.爱用不用
    public void startScroll(){
        mScroller.startScroll(getScrollX(), getScrollY() , -100, -100,3000);

        invalidate();
    }

    @Override
    public void computeScroll() {
        //如果在滑动
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //调用重绘,会再次拿到进度值
            invalidate();
        }
    }
}
