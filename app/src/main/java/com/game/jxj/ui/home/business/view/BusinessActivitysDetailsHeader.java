package com.game.jxj.ui.home.business.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.game.jxj.GlideApp;
import com.game.jxj.R;
import com.game.jxj.model.entity.ActivityInfoResp;
import com.game.jxj.ui.home.record.activity.VideoRecordActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author jxj
 * @date 2018/7/3
 */
public class BusinessActivitysDetailsHeader extends FrameLayout {


    private ViewHolder holder;
    private Context mContext;

    public BusinessActivitysDetailsHeader(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public BusinessActivitysDetailsHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BusinessActivitysDetailsHeader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.header_business_activitys_details, this);
        holder = new ViewHolder(this);


    }

    public void setData(ActivityInfoResp resp) {
        GlideApp.with(mContext).load(resp.LOGO).into(holder.ivLogo);
        holder.tvTitle1.setText(resp.enterpriseName);
        holder.tvTitle2.setText(resp.content);
        holder.tvMoney1.setText("¥ " + resp.money / 100 + "");
        holder.tvMoney2.setText("¥ " + resp.partitionMoney / 100 + "");
        holder.tvCouponName.setText(resp.couponsName);
        holder.tvCoupon1.setText(resp.couponsNum + "");
        holder.tvCoupon2.setText(resp.couponUseNum + "");
        holder.tvContent.setText(resp.slogan);
        holder.ll2.setVisibility(resp.couponsNum == 0 ? GONE : VISIBLE);

        holder.btnRecord.setOnClickListener(v -> VideoRecordActivity.launch(mContext));


    }

    static class ViewHolder {
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_title1)
        TextView tvTitle1;
        @BindView(R.id.tv_title2)
        TextView tvTitle2;
        @BindView(R.id.view_line)
        View viewLine;
        @BindView(R.id.tv_money1)
        TextView tvMoney1;
        @BindView(R.id.tv_money2)
        TextView tvMoney2;
        @BindView(R.id.view_line2)
        View viewLine2;
        @BindView(R.id.ll_1)
        LinearLayout ll1;
        @BindView(R.id.tv_coupon_name)
        TextView tvCouponName;
        @BindView(R.id.tv_coupon1)
        TextView tvCoupon1;
        @BindView(R.id.tv_coupon2)
        TextView tvCoupon2;
        @BindView(R.id.ll_2)
        LinearLayout ll2;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.btn_record)
        Button btnRecord;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
