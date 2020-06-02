package com.liuchuanzheng.study_zidingyiview.floatview2;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.LayoutRes;
import androidx.core.view.ViewCompat;

import com.liuchuanzheng.study_zidingyiview.R;

import java.lang.ref.WeakReference;

/**
 * @author 刘传政
 * @date 2020/6/1 17:56
 * QQ:1052374416
 * 电话:18501231486
 * 作用: 悬浮view的管理器
 * 注意事项:
 */
public class FloatView2Maneger implements IFloatView2Maneger {
    private static volatile FloatView2Maneger instance;
    private WeakReference<FrameLayout> mWeakContainer;
    private FloatView mFloatView = null;
    @LayoutRes
    private int mLayoutId = R.layout.en_floating_view;
    private ViewGroup.LayoutParams mLayoutParams = getParams();

    private FloatView2Maneger(){}

    /**
     * 获取管理器单例
     * @return
     */
    public static FloatView2Maneger getInstance(){
        if (instance == null) {
            synchronized (FloatView2Maneger.class){
                if (instance == null) {
                    instance = new FloatView2Maneger();
                }
            }
        }
        return instance;
    }
    @Override
    public IFloatView2Maneger remove() {
        return this;
    }

    @Override
    public IFloatView2Maneger add() {
        ensureFloatView();
        return this;
    }
    //默认一个floatview
    private void ensureFloatView() {
        synchronized (this) {
            if (mFloatView != null) {
                return;
            }
            FloatView floatView = new DefaultFloatView(ContextUtil.getApplication(),mLayoutId);
            mFloatView = floatView;
            floatView.setLayoutParams(mLayoutParams);
            addViewToWindow(mFloatView);
        }
    }
    private void addViewToWindow(final View view) {
        if (getContainer() == null) {
            return;
        }
        getContainer().addView(view);
    }

    @Override
    public IFloatView2Maneger attach(Activity activity) {
        attach(getActivityRoot(activity));
        return this;
    }

    @Override
    public IFloatView2Maneger attach(FrameLayout container) {
        if (container == null || mFloatView == null) {
            mWeakContainer = new WeakReference<>(container);
            return this;
        }
        if (mFloatView.getParent() == container) {
            //如果已经添加到activity的根布局了
            return this;
        }
        if (getContainer() != null && mFloatView.getParent() == getContainer()) {
            //因为要每个activity都无缝显示.所以这里先移除,后面会添加到新的activity
            getContainer().removeView(mFloatView);
        }
        mWeakContainer = new WeakReference<>(container);
        container.addView(mFloatView);
        return this;
    }

    @Override
    public IFloatView2Maneger detach(Activity activity) {
        detach(getActivityRoot(activity));
        return this;
    }

    @Override
    public IFloatView2Maneger detach(FrameLayout container) {
        if (mFloatView != null && container != null && ViewCompat.isAttachedToWindow(mFloatView)) {
            container.removeView(mFloatView);
        }
        if (getContainer() == container) {
            mWeakContainer = null;
        }
        return this;
    }

    @Override
    public FloatView getView() {
        return mFloatView;
    }

    @Override
    public IFloatView2Maneger customView(FloatView viewGroup) {
        mFloatView = viewGroup;
        return this;
    }

    @Override
    public IFloatView2Maneger customView(@LayoutRes int resource) {
        mLayoutId = resource;
        return this;
    }

    @Override
    public IFloatView2Maneger layoutParams(ViewGroup.LayoutParams params) {
        return this;
    }

    @Override
    public IFloatView2Maneger listener(FloatViewListener listener) {
        if (mFloatView == null) {
            mFloatView.setFloatViewListener(listener);
        }
        return this;
    }

    @Override
    public IFloatView2Maneger hide() {
        mFloatView.setVisibility(View.GONE);
        return this;
    }

    @Override
    public IFloatView2Maneger show() {
        mFloatView.setVisibility(View.VISIBLE);
        return this;
    }

    private FrameLayout getActivityRoot(Activity activity) {
        if (activity == null) {
            return null;
        }
        try {
            //找到activiy的原生根布局
            return (FrameLayout) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private FrameLayout getContainer() {
        if (mWeakContainer == null) {
            return null;
        }
        return mWeakContainer.get();
    }
    private FrameLayout.LayoutParams getParams() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        params.setMargins(0, params.topMargin, params.rightMargin, 500);
        return params;
    }

}
