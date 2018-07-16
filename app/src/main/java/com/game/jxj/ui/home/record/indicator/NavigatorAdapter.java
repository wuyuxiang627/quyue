package com.game.jxj.ui.home.record.indicator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.animation.OvershootInterpolator;

import com.game.jxj.view.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jxj
 * @date 2018/6/21
 */
public class NavigatorAdapter extends CommonNavigatorAdapter {

    private List<String> data = new ArrayList<>();
    private ViewPager viewPager;
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    public NavigatorAdapter(List<String> data, ViewPager viewPager) {
        this.data = data;
        this.viewPager = viewPager;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, int i) {
        ScaleTransitionPagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
        simplePagerTitleView.setMinScale(0.9f);
        simplePagerTitleView.setText(data.get(i));
        simplePagerTitleView.getPaint().setFakeBoldText(true);
        simplePagerTitleView.setTextSize(16);
        simplePagerTitleView.setOnClickListener(v -> {
            ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(v, "scaleX", 0.2f, 1.0f);
            bounceAnimX.setDuration(300);
            bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(v, "scaleY", 0.2f, 1.0f);
            bounceAnimY.setDuration(300);
            bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(bounceAnimX, bounceAnimY);
            animatorSet.start();
        });
        simplePagerTitleView.setNormalColor(Color.WHITE);
        simplePagerTitleView.setSelectedColor(Color.WHITE);

        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        return null;
    }
}
