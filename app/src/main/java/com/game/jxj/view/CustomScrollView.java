package com.game.jxj.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.game.jxj.R;

/**
 * @author jxj
 * @date 2018/6/21
 */
public class CustomScrollView extends LinearLayout implements NestedScrollingParent {

    public static final String TAG = "NESTEDSCROLLing";

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {

        return true;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

        boolean hiddenTop = dy > 0 && getScrollY() < topHeight;
        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);
        Log.e(TAG, dy + "dy" + " scrollY" + getScrollY());
        if (hiddenTop || showTop) {
            consumed[1] = dy;
            scrollBy(0, dy);
        }

        super.onNestedPreScroll(target, dx, dy, consumed);
    }

    private View mTop;
    private ViewPager mViewPager;
    private int topHeight;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTop = getChildAt(0);
        mViewPager = findViewById(R.id.vp_content);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        topHeight = mTop.getMeasuredHeight();
//        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
//        params.height = getMeasuredHeight() - topHeight;
//        LogUtils.e(getMeasuredHeight());
//        setMeasuredDimension(widthMeasureSpec, topHeight + mViewPager.getMeasuredHeight());

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        topHeight = getMeasuredHeight();
    }
}
