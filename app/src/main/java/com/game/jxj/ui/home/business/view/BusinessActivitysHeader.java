package com.game.jxj.ui.home.business.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.game.jxj.R;
import com.game.jxj.model.entity.BannerListResp;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

/**
 * @author jxj
 * @date 2018/7/3
 */
public class BusinessActivitysHeader extends FrameLayout {
    public BusinessActivitysHeader(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public BusinessActivitysHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BusinessActivitysHeader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private Banner banner;

    private void initView(Context context) {

        LayoutInflater.from(context).inflate(R.layout.header_business, this);
        banner = findViewById(R.id.banner_ad);

    }

    public void setData(List<BannerListResp> data) {

        banner.setImages(data);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setImageLoader(new BannerImageLoader());
        banner.start();
    }
}
