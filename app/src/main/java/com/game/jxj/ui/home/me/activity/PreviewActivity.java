package com.game.jxj.ui.home.me.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.gm.utils.ActivityUtil;
import com.gm.views.AbTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreviewActivity extends SimpleActivity {

    public static void launch(Context context) {
        ActivityUtil.startActivity(context, PreviewActivity.class);
    }

    @BindView(R.id.title_bar)
    AbTitleBar titleBar;
    @BindView(R.id.ib_preview_video)
    ImageButton ibPreviewVideo;
    @BindView(R.id.ib_preview_text)
    ImageButton ibPreviewText;
    @BindView(R.id.iv_preview_logo)
    ImageView ivPreviewLogo;
    @BindView(R.id.tv_preview_company_name)
    TextView tvPreviewCompanyName;
    @BindView(R.id.tv_preview_title)
    TextView tvPreviewTitle;

    @Override
    protected int getLayout() {
        return R.layout.activity_preview;
    }

    @Override
    protected void initEventAndData() {
        titleBar.setTitleText("发起挑战赛");
        titleBar.setBackgroundColor(Color.parseColor("#ffffff"));
        titleBar.setTitleTextColor();
        titleBar.getleftIconView().setImageResource(R.drawable.ic_arrow_back_black_24dp);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
