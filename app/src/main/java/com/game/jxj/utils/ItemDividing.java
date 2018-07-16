package com.game.jxj.utils;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.game.jxj.R;
import com.gm.utils.LogUtils;
import com.gm.utils.SizeUtils;

/**
 * @author jxj
 * @date 2018/4/20
 */

public class ItemDividing extends RecyclerView.ItemDecoration {


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildLayoutPosition(view) + 1;
        int offset = SizeUtils.dp2px(1);
        if (position % 3 == 2) {
            outRect.set(offset, 0, offset, offset);
        } else {
            outRect.set(0, 0, 0, offset);
        }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        c.drawColor(parent.getContext().getResources().getColor(R.color.color_gray_E8E8E8));
    }

}
