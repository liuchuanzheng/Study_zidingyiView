package com.liuchuanzheng.study_zidingyiview.floatview2;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.liuchuanzheng.study_zidingyiview.R;

/**
 * @author 刘传政
 * @date 2020/6/2 09:08
 * QQ:1052374416
 * 电话:18501231486
 * 作用:默认的悬浮view
 * 注意事项:
 */
public class DefaultFloatView extends FloatView {
    public DefaultFloatView(@NonNull Context context) {
        this(context,R.layout.en_floating_view);
    }
    public DefaultFloatView(@NonNull Context context, @LayoutRes int resource) {
        super(context, null);
        inflate(context, resource, this);
    }
}
