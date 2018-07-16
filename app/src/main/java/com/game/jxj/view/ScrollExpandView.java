package com.game.jxj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.ExpandableListView;

/**
 * @author jxj
 * @date 2018/4/27
 */

public class ScrollExpandView extends ExpandableListView {
    public ScrollExpandView(Context context) {
        super(context);
    }

    public ScrollExpandView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollExpandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
