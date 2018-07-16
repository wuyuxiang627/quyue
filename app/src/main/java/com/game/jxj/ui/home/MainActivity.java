package com.game.jxj.ui.home;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.game.jxj.ui.home.business.fragment.BusinessActivitysFragment;
import com.game.jxj.ui.home.me.fragment.MeFragment;
import com.game.jxj.ui.home.play.fragment.HomeFragment;
import com.game.jxj.ui.home.play.fragment.VideoFragment;
import com.gm.utils.ActivityUtil;
import com.gm.views.ForbidenScrollViewPager;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends SimpleActivity implements ViewPager.OnPageChangeListener {


    public static void launch(Context context) {
        ActivityUtil.startActivity(context, MainActivity.class);
    }


    @BindView(R.id.viewpager_content)
    ForbidenScrollViewPager viewpagerContent;
    @BindView(R.id.ib_index_video)
    ImageButton ibIndexVideo;
    @BindView(R.id.ib_index_add)
    ImageButton ibIndexAdd;
    @BindView(R.id.ib_index_me)
    ImageButton ibIndexMe;
    @BindView(R.id.ll_index_bottom)
    LinearLayout llIndexBottom;
    @BindView(R.id.fl_parent)
    FrameLayout flParent;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {

        viewpagerContent.setForbidenScroll(true);
        viewpagerContent.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager()));
        viewpagerContent.setCurrentItem(0);
        viewpagerContent.setOffscreenPageLimit(2);
        viewpagerContent.addOnPageChangeListener(this);

    }


    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    @OnClick(R.id.ib_index_video)
    public void onIbIndexVideoClicked() {
        viewpagerContent.setCurrentItem(0);
    }

    @OnClick(R.id.ib_index_add)
    public void onIbIndexAddClicked() {
        viewpagerContent.setCurrentItem(1);
    }

    @OnClick(R.id.ib_index_me)
    public void onIbIndexMeClicked() {
        viewpagerContent.setCurrentItem(2);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        llIndexBottom.setBackgroundResource(position == 0 ? R.color.transparent : R.color.colorPrimary);

    }

    @Override
    public void onPageScrollStateChanged(int state) {


    }


    class MainViewPagerAdapter extends FragmentPagerAdapter {


        MainViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        Fragment fragment = null;

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = BusinessActivitysFragment.getInstance();
                    break;
                case 2:
                    fragment = MeFragment.getInstance();
                    break;
                default:
                    break;
            }

            return fragment;
        }


        @Override
        public int getCount() {
            return 3;
        }
    }


}



