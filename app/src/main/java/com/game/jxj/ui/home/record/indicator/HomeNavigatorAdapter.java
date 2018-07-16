package com.game.jxj.ui.home.record.indicator;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;

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
public class HomeNavigatorAdapter extends CommonNavigatorAdapter {

    private List<String> data = new ArrayList<>();
    private ViewPager viewPager;

    public HomeNavigatorAdapter(List<String> data, ViewPager viewPager) {
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
        simplePagerTitleView.setTextSize(15);
        simplePagerTitleView.setOnClickListener(v -> {
            viewPager.setCurrentItem(i);
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
