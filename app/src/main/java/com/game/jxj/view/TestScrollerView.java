package com.game.jxj.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * @author jxj
 * @date 2018/6/22
 */
public class TestScrollerView extends FrameLayout {

    private Scroller scroller;

    public TestScrollerView(@NonNull Context context) {
        super(context);
    }

    public TestScrollerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestScrollerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void init(Context context) {
        scroller = new Scroller(context);
        scroller.startScroll(0, 0, -190, -190,5000);
        invalidate();
    }


    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }
}
