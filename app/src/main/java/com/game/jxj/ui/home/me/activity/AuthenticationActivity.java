package com.game.jxj.ui.home.me.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.gm.utils.ActivityUtil;
import com.gm.views.AbTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthenticationActivity extends SimpleActivity {

    public static void launch(Context context) {

        ActivityUtil.startActivity(context, AuthenticationActivity.class);
    }

    @BindView(R.id.title_bar)
    AbTitleBar titleBar;
    @BindView(R.id.et_authentiacion_name)
    EditText etAuthentiacionName;
    @BindView(R.id.et_authentiacion_id)
    EditText etAuthentiacionId;
    @BindView(R.id.cb_authentication_ok)
    CheckBox cbAuthenticationOk;
    @BindView(R.id.tv_authentication_xieyi)
    TextView tvAuthenticationXieyi;
    @BindView(R.id.btn_authentication_ok)
    Button btnAuthenticationOk;

    @Override
    protected int getLayout() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void initEventAndData() {
        titleBar.setTitleText("实名认证");
        titleBar.setBackgroundColor(Color.parseColor("#ffffff"));
        titleBar.setTitleTextColor();
        titleBar.getleftIconView().setImageResource(R.drawable.ic_arrow_back_black_24dp);


    }


    @OnClick(R.id.btn_authentication_ok)
    public void onViewClicked() {

    }
}
