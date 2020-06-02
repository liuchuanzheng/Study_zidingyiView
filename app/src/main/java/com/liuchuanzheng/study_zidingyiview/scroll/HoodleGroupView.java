package com.liuchuanzheng.study_zidingyiview.scroll;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class HoodleGroupView extends ViewGroup {

    private PointF mDownPoint;

    private RectF mChildeRectF;
    private VelocityTracker velocityTracker;
    private Scroller mScroller;
    private int mMaxFlintVelocity, mMinFlintVelocity;
    private int mChildMeasuredWidth,mChildMeasuredHeight;
    private View chileView;
    private boolean isFirPoint = true;
    private float lastX, lastY;

    public HoodleGroupView(Context context) {
        this(context, null);
    }

    public HoodleGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HoodleGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        mDownPoint = new PointF();
        mChildeRectF = new RectF();
        mScroller = new Scroller(context, null, true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mMaxFlintVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        mMinFlintVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMinFlintVelocity = 600;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtil.e("onLayout");
        chileView = getChildAt(0);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        mChildMeasuredWidth = chileView.getMeasuredWidth();
        mChildMeasuredHeight = chileView.getMeasuredHeight();
        mChildeRectF.set(measuredWidth / 2 - mChildMeasuredWidth / 2, measuredHeight / 2 - mChildMeasuredHeight / 2, measuredWidth / 2 + mChildMeasuredWidth / 2, measuredHeight / 2 + mChildMeasuredHeight / 2);
        chileView.layout(measuredWidth / 2 - mChildMeasuredWidth / 2, measuredHeight / 2 - mChildMeasuredHeight / 2, measuredWidth / 2 + mChildMeasuredWidth / 2, measuredHeight / 2 + mChildMeasuredHeight / 2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public void onHoverChanged(boolean hovered) {
        LogUtil.e("onHoverChanged");
        super.onHoverChanged(hovered);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LogUtil.e("onScrollChanged,l=" + l + ",t=" + t + ",oldl=" + oldl + ",oldt=" + oldt);
//        mChildeRectF.set(mChildeRectF.left - l, mChildeRectF.top - t, mChildeRectF.right - l, mChildeRectF.bottom - t);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownPoint.x = event.getX();
                mDownPoint.y = event.getY();
                lastX = event.getX();
                lastY = event.getY();
                LogUtil.e("down_x=" + lastX + ",down_y=" + lastY);
                //判断按下的手指是否在图片上面
                if(!isViewUnderPoint(mDownPoint)){
                    return false;
                }
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float ev_x = event.getX();
                float ev_y = event.getY();
                scrollBy((int) (-ev_x + lastX), (int) (-ev_y + lastY));
//                mChildeRectF.set(mChildeRectF.left + ev_x - lastX, mChildeRectF.top + ev_y - lastY, mChildeRectF.right + ev_x - lastX, mChildeRectF.bottom + ev_y - lastY);
                lastX = ev_x;
                lastY = ev_y;
                break;
            case MotionEvent.ACTION_UP:
                //手指抬起，计算当前速率
                float up_x = event.getX();
                float up_y = event.getY();
                velocityTracker.computeCurrentVelocity(1000, mMaxFlintVelocity);
                int xVelocity = (int) velocityTracker.getXVelocity();
                int yVelocity = (int) velocityTracker.getYVelocity();
                int scrollX = getScrollX();
                int scrollY = getScrollY();
                LogUtil.e("mMinFlintVelocity=" + mMinFlintVelocity + ",xVelocity=" + xVelocity + ",yVelocity=" + yVelocity);
                if (Math.abs(xVelocity) > mMinFlintVelocity && Math.abs(yVelocity) > mMinFlintVelocity) {
                    mScroller.fling(scrollX, scrollY, -xVelocity, -yVelocity, 0, getWidth() - chileView.getWidth(), 0, getHeight() - chileView.getHeight());
                    LogUtil.e("up_x=" + up_x + ",up_y=" + up_y + "scrollX=" + scrollX + ",scrollY=" + scrollY + ", startX=" + mScroller.getStartX() + ", startY=" + mScroller.getStartY() + ", width=" + getWidth() + ",childWidth=" + chileView.getWidth() + ",height=" + getHeight() + ",childheight=" + chileView.getHeight());
                    int startX = mScroller.getStartX();
                    int startY = mScroller.getStartY();
                    int finalX = mScroller.getFinalX();
                    int finalY = mScroller.getFinalY();
                    mChildeRectF.set(chileView.getLeft() - finalX, chileView.getTop() -finalY, chileView.getRight() - finalX, chileView.getBottom() -finalY);
                    int dex_x, dex_y;
                    LogUtil.e("upx=" + up_x + ",upy=" + up_y + ",pontX=" + mDownPoint.x + ",pontY=" + mDownPoint.y);
                    if(up_x > mDownPoint.x) {
                        //证明往右滑动
                        dex_x = finalX - startX;
                    }else {
                        dex_x = startX - finalX;
                    }
                    if (up_y > mDownPoint.y) {
                        dex_y = finalY - startY;
                    }else {
                        dex_y = startY - finalY;
                    }
                    LogUtil.e("dex="+dex_x+",dey="+dex_y+"left="+mChildeRectF.left+",top="+mChildeRectF.top+"finalX=" + mScroller.getFinalX() + ",finalY=" + mScroller.getFinalY());
//                    mChildeRectF.set(mChildeRectF.left + dex_x, mChildeRectF.top + dex_y, mChildeRectF.right + dex_x, mChildeRectF.bottom + dex_y);
                    LogUtil.e("left="+mChildeRectF.left+",top="+mChildeRectF.top);
                    awakenScrollBars(mScroller.getDuration());
                    invalidate();
                }else {
                    mChildeRectF.set(chileView.getLeft() - scrollX, chileView.getTop() -scrollY, chileView.getRight() - scrollX, chileView.getBottom() -scrollY);
                }

                if (velocityTracker != null) {
                    velocityTracker.clear();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtil.e("-----ACTION_CANCEL");
                break;
        }
        return true;
    }

    private boolean isViewUnderPoint(PointF pointF) {
        return mChildeRectF.contains(pointF.x, pointF.y);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            LogUtil.e("computeScroll---"+mScroller.getCurrX()+","+mScroller.getCurrY());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}