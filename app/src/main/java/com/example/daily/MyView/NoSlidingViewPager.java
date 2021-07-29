package com.example.daily.MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class NoSlidingViewPager extends ViewPager {

    private boolean slide = false;// false 禁止ViewPager左右滑动。

    public NoSlidingViewPager(@NonNull Context context) {
        super(context);
    }

    public NoSlidingViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public void setScrollable(boolean slide) {
        this.slide = slide;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return slide;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return slide;
    }

}