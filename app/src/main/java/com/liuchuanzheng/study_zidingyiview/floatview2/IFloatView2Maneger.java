package com.liuchuanzheng.study_zidingyiview.floatview2;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;

import com.imuxuan.floatingview.FloatingMagnetView;
import com.imuxuan.floatingview.MagnetViewListener;

public interface IFloatView2Maneger {

    IFloatView2Maneger remove();

    IFloatView2Maneger add();

    IFloatView2Maneger attach(Activity activity);

    IFloatView2Maneger attach(FrameLayout container);

    IFloatView2Maneger detach(Activity activity);

    IFloatView2Maneger detach(FrameLayout container);

    FloatView getView();

    IFloatView2Maneger customView(FloatView viewGroup);

    IFloatView2Maneger customView(@LayoutRes int resource);

    IFloatView2Maneger layoutParams(ViewGroup.LayoutParams params);

    IFloatView2Maneger listener(FloatViewListener listener);
    IFloatView2Maneger hide();
    IFloatView2Maneger show();

}