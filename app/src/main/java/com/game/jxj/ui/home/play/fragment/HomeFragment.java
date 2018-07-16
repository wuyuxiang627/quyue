package com.game.jxj.ui.home.play.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.game.jxj.R;
import com.game.jxj.base.SimpleFragment;
import com.game.jxj.model.entity.UserInfo;
import com.game.jxj.ui.home.me.adapter.CommonViewPagerAdapter;
import com.game.jxj.ui.user.activity.LoginActivity;
import com.gm.utils.ToastUtils;
import com.gm.views.ForbidenScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author jxj
 * @date 2018/6/14
 */
public class HomeFragment extends SimpleFragment {

    @BindView(R.id.viewpager_content)
    ForbidenScrollViewPager viewpagerContent;
    @BindView(R.id.tlTabLayout)
    SlidingTabLayout tlTabLayout;


    private String[] titles = {"关注", "推荐"};
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    @Override
    protected int getLayoutId() {
        return R.layout.framgent_home;
    }

    @Override
    protected void initEventAndData() {

        viewpagerContent.setForbidenScroll(true);
        viewpagerContent.setExpenseOnTouch(true);


        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FollowerListFragment());
        fragments.add(VideoFragment.getInstance());
        CommonViewPagerAdapter adapter = new CommonViewPagerAdapter(getChildFragmentManager(), fragments);
        viewpagerContent.setAdapter(adapter);

        tlTabLayout.setViewPager(viewpagerContent, titles);
        tlTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                startAnimation(tlTabLayout.getTitleView(position));
                if (position == 0) {

                    if (!UserInfo.getInstance().isLogined()) {
                        ToastUtils.showShort("尚未登录");
                        LoginActivity.launch(mContext);
                    }
                }
            }

            @Override
            public void onTabReselect(int position) {
                startAnimation(tlTabLayout.getTitleView(position));
            }
        });
        viewpagerContent.setCurrentItem(1);


    }

    private void startAnimation(View target) {
        ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(target, "scaleX", 0.2f, 1.0f);
        bounceAnimX.setDuration(300);
        bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(target, "scaleY", 0.2f, 1.0f);
        bounceAnimY.setDuration(300);
        bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(bounceAnimX, bounceAnimY);
        animatorSet.start();
    }


}
