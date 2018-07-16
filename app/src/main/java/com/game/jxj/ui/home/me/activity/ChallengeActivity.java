package com.game.jxj.ui.home.me.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.game.jxj.ui.home.me.adapter.CommonViewPagerAdapter;
import com.game.jxj.ui.home.me.fragment.ChallengeFragment;
import com.game.jxj.ui.home.me.fragment.MyVideosFragment;
import com.game.jxj.view.ColorFlipPagerTitleView;
import com.gm.utils.ActivityUtil;
import com.gm.views.AbTitleBar;
import com.gm.views.ForbidenScrollViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class ChallengeActivity extends SimpleActivity {

    public static void launch(Context context) {
        ActivityUtil.startActivity(context, ChallengeActivity.class);
    }
    @BindView(R.id.title_bar)
    AbTitleBar titleBar;
    @BindView(R.id.mi_challenge_start_title)
    MagicIndicator miChallengeStartTitle;
    @BindView(R.id.vp_challenge)
    ForbidenScrollViewPager vpChallenge;

    private static String[] CHANNELS ;
    private List<String> mDataList;

    @Override
    protected int getLayout() {
        return R.layout.activity_challenge;
    }

    @Override
    protected void initEventAndData() {
        titleBar.setTitleText("挑战赛");
        titleBar.setRightButton("新建",R.color.black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChallengeCreatActivity.launch(mContext);
            }
        });

        CHANNELS = new String[]{"进行中 ", "未开启 ", "已结束 "};
        mDataList = Arrays.asList(CHANNELS);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ChallengeFragment.getInstance(ChallengeFragment.TYPE_UNDERWAY));
        fragments.add(ChallengeFragment.getInstance(ChallengeFragment.TYPE_FINISHED));
        fragments.add(ChallengeFragment.getInstance(ChallengeFragment.TYPE_NO_pay));
        CommonViewPagerAdapter pagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(), fragments);
        vpChallenge.setOffscreenPageLimit(3);
        vpChallenge.setCurrentItem(0);
        vpChallenge.setAdapter(pagerAdapter);

        initMagicIndicator7();

    }



    private void initMagicIndicator7() {
        miChallengeStartTitle.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator7 = new CommonNavigator(this);
        commonNavigator7.setScrollPivotX(0.8f);
        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#9e9e9e"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#000000"));

                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vpChallenge.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                indicator.setLineWidth(UIUtil.dip2px(context, 30));
                indicator.setRoundRadius(UIUtil.dip2px(context, 1));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#000000"));
                return indicator;
            }
        });
        miChallengeStartTitle.setNavigator(commonNavigator7);
        ViewPagerHelper.bind(miChallengeStartTitle, vpChallenge);
    }


}
