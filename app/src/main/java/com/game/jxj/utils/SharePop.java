package com.game.jxj.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.game.jxj.R;

import butterknife.BindView;

public class SharePop extends PopupWindow {

    LinearLayout llPop;
    Button btnFinish;
    private Context mContext;

    private View view;



    /**
     * 构造参数
     *
     * @param context
     */
    public SharePop(Context context) {
        this.mContext = context;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_share, null);
        btnFinish = view.findViewById(R.id.btn_pop_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // 设置外部可点击
        this.setOutsideTouchable(true);
        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.GiftPopupAnimation);
        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        showAtLocation(view, Gravity.BOTTOM, 0, 0); // 设置layout在PopupWindow中显示的位置

    }


}
