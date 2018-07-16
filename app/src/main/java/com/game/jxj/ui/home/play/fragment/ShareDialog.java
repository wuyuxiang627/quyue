package com.game.jxj.ui.home.play.fragment;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;

import com.game.jxj.R;

public class ShareDialog extends Dialog {
    private Context mContext;

    public ShareDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        setContentView(R.layout.layout_dialog_share);
        getWindow().setGravity(Gravity.BOTTOM);
    }
}
