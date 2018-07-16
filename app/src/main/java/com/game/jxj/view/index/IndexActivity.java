package com.game.jxj.view.index;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.game.jxj.ui.home.me.fragment.MeFragment;
import com.game.jxj.ui.home.play.fragment.VideoFragment;
import com.gm.utils.ActivityUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018\5\22 0022.
 */

public class IndexActivity extends SimpleActivity {

    public static void launch(Context context) {
        ActivityUtil.startActivity(context, IndexActivity.class);
    }

    @BindView(R.id.layout_fragment_index)
    FrameLayout layoutFragmentIndex;
    @BindView(R.id.ib_index_video)
    ImageButton ibIndexVideo;
    @BindView(R.id.ib_index_add)
    ImageButton ibIndexAdd;
    @BindView(R.id.ib_index_me)
    ImageButton ibIndexMe;
    @BindView(R.id.ll_index_bottom)
    LinearLayout llIndexBottom;
    private FragmentTransaction transaction;
    VideoFragment videoFragment;
    MeFragment meFragment;
    private Fragment[] fragments;
    int index = 0;
    private int currentTabIndex = 0;

    @Override
    protected int getLayout() {
        return R.layout.activity_index;
    }

    @Override
    protected void initEventAndData() {
        setAdapter();
        setListener();

    }

    private void setListener() {


    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    private void setAdapter() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        videoFragment = new VideoFragment();
        meFragment = new MeFragment();
        fragments = new Fragment[]{videoFragment, meFragment};

        transaction.add(R.id.layout_fragment_index, videoFragment).
                add(R.id.layout_fragment_index, meFragment)
                .hide(meFragment)
                .show(videoFragment)
                .commit();

    }

    @SuppressLint("ResourceAsColor")
    @OnClick({R.id.ib_index_video, R.id.ib_index_me})
    public void onViewClicked(View view) {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.ib_index_video:
                llIndexBottom.setBackgroundColor(Color.parseColor("#40000000"));
                index = 0;
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                break;
            case R.id.ib_index_me:
                llIndexBottom.setBackgroundColor(Color.BLACK);
                index = 1;
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                break;
        }
        if (currentTabIndex != index) {//判断是否是当前页
            trx.hide(fragments[currentTabIndex]);//隐藏掉原先的fragment
            if (!fragments[index].isAdded()) {//判断fragment是否已被添加过
                trx.add(R.id.layout_fragment_index, fragments[index]);//添加到trx管理中
            }
            trx.show(fragments[index]).commit();//如果已被添加过就直接显示出来
        } else {
            trx.show(fragments[index]).commit();//如果已被添加过就直接显示出来
        }
        currentTabIndex = index;//修改当前页


    }
}
