package com.game.jxj.ui.home.me.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.gm.utils.ActivityUtil;
import com.gm.views.AbTitleBar;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletActivity extends SimpleActivity {
    public static void launch(Context context) {
        ActivityUtil.startActivity(context, WalletActivity.class);
    }
    @BindView(R.id.title_bar)
    AbTitleBar titleBar;
    @BindView(R.id.tv_money_iocn)
    TextView tvMoneyIocn;
    @BindView(R.id.tv_wallte_of_cash)
    TextView tvWallteOfCash;
    @BindView(R.id.tv_wallte_money_icon)
    TextView tvWallteMoneyIcon;
    @BindView(R.id.et_wallte_money)
    EditText etWallteMoney;
    @BindView(R.id.btn_wallte_all_wallte)
    Button btnWallteAllWallte;
    @BindView(R.id.et_wallte_alipay_number)
    EditText etWallteAlipayNumber;
    @BindView(R.id.et_wallte_real_name)
    EditText etWallteRealName;
    @BindView(R.id.et_wallte_login_pwd)
    EditText etWallteLoginPwd;
    @BindView(R.id.ib_wallte)
    ImageButton ibWallte;
    @BindView(R.id.tv_wallet_money)
    TextView tvWalletMoney;




    @Override
    protected int getLayout() {
        return R.layout.activity_wallte;
    }

    @Override
    protected void initEventAndData() {
        titleBar.setTitleText("提现");
        titleBar.setBackgroundColor(Color.parseColor("#ffffff"));
        titleBar.setTitleTextColor();
        titleBar.getleftIconView().setImageResource(R.drawable.ic_arrow_back_black_24dp);

        String str2 = "<small>￥</small>"+money(Double.parseDouble("0"));
        tvWalletMoney.setText(Html.fromHtml(str2));



    }

    @OnClick({R.id.btn_wallte_all_wallte, R.id.ib_wallte})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_wallte_all_wallte:
                break;
            case R.id.ib_wallte:
                break;
        }
    }

    public static String money(double d){

        NumberFormat nf = new DecimalFormat("####.##");

        String str = nf.format(d);

        return  str;

    }

}
