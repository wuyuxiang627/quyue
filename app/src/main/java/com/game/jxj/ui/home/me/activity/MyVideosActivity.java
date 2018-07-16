package com.game.jxj.ui.home.me.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.game.jxj.ui.home.me.adapter.CommonViewPagerAdapter;
import com.game.jxj.ui.home.me.fragment.MyVideosFragment;
import com.game.jxj.utils.RadioButtonWrapper;
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
import butterknife.ButterKnife;

public class MyVideosActivity extends SimpleActivity {

    @BindView(R.id.vp_content)
    ForbidenScrollViewPager vpContent;
    @BindView(R.id.magic_indicator7)
    MagicIndicator magicIndicator7;
    @BindView(R.id.title_bar)
    AbTitleBar titleBar;

    private int myVideoNum, favorVideoNum;

    private RadioButtonWrapper radioButtonWrapper;

    private static String[] CHANNELS ;
    private List<String> mDataList;

    public static void launch(Context context,int myVideoNum,int favorVideoNum  ) {
        Intent intent = new Intent(context, MyVideosActivity.class);
        intent.putExtra("myVideoNum",myVideoNum);
        intent.putExtra("favorVideoNum  ",favorVideoNum );
        ActivityUtil.startActivity(context, MyVideosActivity.class);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_my_videos;
    }


    @Override
    protected void initEventAndData() {
        myVideoNum = getIntent().getIntExtra("myVideoNum",0);
        favorVideoNum = getIntent().getIntExtra("favorVideoNum",0);
        CHANNELS = new String[]{"我的视频 "+myVideoNum, "我喜欢的 "+favorVideoNum, "草稿箱 0"};
        mDataList = Arrays.asList(CHANNELS);

        titleBar.setTitleText("发起挑战赛");
        titleBar.setBackgroundColor(Color.parseColor("#ffffff"));
        titleBar.setTitleTextColor();
        titleBar.getleftIconView().setImageResource(R.drawable.ic_arrow_back_black_24dp);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MyVideosFragment.getInstance(MyVideosFragment.TYPE_PRODUCTION));
        fragments.add(MyVideosFragment.getInstance(MyVideosFragment.TYPE_LIKE));
        fragments.add(MyVideosFragment.getInstance(MyVideosFragment.TYPE_DRAFT));
        CommonViewPagerAdapter pagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager(), fragments);
        vpContent.setOffscreenPageLimit(3);
        vpContent.setCurrentItem(0);
        vpContent.setForbidenScroll(true);
        vpContent.setAdapter(pagerAdapter);


//        rgTypeName.setOnCheckedChangeListener((group, checkedId) -> {
//            switch (checkedId) {
//                case R.id.rbtn_production:
//                    vpContent.setCurrentItem(0);
//                    radioButtonWrapper.setCurrentItemUI(0);
//                    break;
//                case R.id.rbtn_like:
//                    vpContent.setCurrentItem(1);
//                    radioButtonWrapper.setCurrentItemUI(1);
//                    break;
//                case R.id.rbtn_draft:
//                    vpContent.setCurrentItem(2);
//                    radioButtonWrapper.setCurrentItemUI(2);
//                    break;
//                default:
//                    break;
//
//
//            }
//        });
//        rgTypeName.check(R.id.rbtn_production);

        initMagicIndicator7();


    }


    private void initMagicIndicator7() {
        magicIndicator7.setBackgroundColor(Color.parseColor("#ffffff"));
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
                        vpContent.setCurrentItem(index);
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
        magicIndicator7.setNavigator(commonNavigator7);
        ViewPagerHelper.bind(magicIndicator7, vpContent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
