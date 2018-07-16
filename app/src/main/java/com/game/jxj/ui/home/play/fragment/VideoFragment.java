package com.game.jxj.ui.home.play.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Service;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.game.jxj.R;
import com.game.jxj.base.SimpleFragment;
import com.game.jxj.model.entity.PlayerInfo;
import com.game.jxj.model.entity.TCVideoInfo;
import com.game.jxj.ui.home.play.adapter.VideoPlayListAdapter;
import com.gm.utils.ToastUtils;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * Created by Administrator on 2018\5\23 0023.
 */

public class VideoFragment extends SimpleFragment implements ITXVodPlayListener {


    public static VideoFragment getInstance() {
        return new VideoFragment();
    }

    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.vertical_view_pager)
    VerticalViewPager mVerticalViewPager;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;


    private VideoPlayListAdapter mPagerAdapter;
    private TXCloudVideoView mTXCloudVideoView;
    // 发布者id 、视频地址、 发布者名称、 头像URL、 封面URL
    private List<TCVideoInfo> mTCLiveInfoList = new ArrayList<>();
    private int mInitTCLiveInfoPosition;
    private int mCurrentPosition;
    private TXVodPlayer mTXVodPlayer;
    private ImageView mIvCover;
    private ObjectAnimator objectAnimator;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initEventAndData() {


        objectAnimator = ObjectAnimator.ofFloat(view1, "scaleX", 0.2f, 0.9f);
        objectAnimator.setDuration(400);
        objectAnimator.setRepeatCount(Integer.MAX_VALUE);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                view1.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                view1.setVisibility(View.VISIBLE);
            }
        });

        initViews();
        initPhoneListener();
        setListener();

    }


    private void initPhoneListener() {
        if (mPhoneListener == null)
            mPhoneListener = new TXPhoneStateListener(mTXVodPlayer);
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }


    private void initViews() {

        swipeRefresh.setProgressViewOffset(false, 150, 360);
        mVerticalViewPager.setOffscreenPageLimit(2);
        mVerticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mCurrentPosition = position;
                // 滑动界面，首先让之前的播放器暂停，并seek到0

                if (mTXVodPlayer != null) {
                    mTXVodPlayer.seek(0);
                    mTXVodPlayer.pause();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (mCurrentPosition == mTCLiveInfoList.size() - 1 &&
                        state == ViewPager.SCROLL_STATE_IDLE) {
//                    mTCLiveInfoList.add(new TCVideoInfo());
//                    mPagerAdapter.notifyDataSetChanged();
                }

                if (mCurrentPosition == 0 && state == ViewPager.SCROLL_STATE_IDLE) {
                    swipeRefresh.setEnabled(true);
                } else {
                    swipeRefresh.setEnabled(false);
                }

            }
        });

        mVerticalViewPager.setPageTransformer(false, (page, position) -> {

            if (position != 0) {
                return;
            }
            ViewGroup viewGroup = (ViewGroup) page;
            mIvCover = viewGroup.findViewById(R.id.iv_cover);
            mTXCloudVideoView = viewGroup.findViewById(R.id.video_view);


            PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(mCurrentPosition);
            if (playerInfo != null && !playerInfo.txVodPlayer.isPlaying()) {
                playerInfo.txVodPlayer.resume();
                mTXVodPlayer = playerInfo.txVodPlayer;
            }
        });

        mTCLiveInfoList.add(new TCVideoInfo());
        mTCLiveInfoList.add(new TCVideoInfo());

        mPagerAdapter = new VideoPlayListAdapter(mContext, mTCLiveInfoList, VideoFragment.this);
        mVerticalViewPager.setAdapter(mPagerAdapter);
    }


    private void setListener() {
        swipeRefresh.setOnRefreshListener(() -> {
            swipeRefresh.setRefreshing(false);
        });
    }


    @Override
    public void onPlayEvent(TXVodPlayer player, int event, Bundle param) {
        if (event == TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION) {
            int width = param.getInt(TXLiveConstants.EVT_PARAM1);
            int height = param.getInt(TXLiveConstants.EVT_PARAM2);
            if (width > height) {
                player.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);
            } else {
                player.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
            restartPlay();
        } else if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {// 视频I帧到达，开始播放

            PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(player);
            if (playerInfo != null) {
                playerInfo.isBegin = true;
            }
            if (mTXVodPlayer == player) {

                mIvCover.setVisibility(View.GONE);
            }

        } else if (event == TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED) {
            if (mTXVodPlayer == player) {

                mTXVodPlayer.resume();
            }

        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(player);
            if (playerInfo != null && playerInfo.isBegin) {
                mIvCover.setVisibility(View.GONE);
                objectAnimator.cancel();
            }


        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING) {

            objectAnimator.start();

        } else if (event == TXLiveConstants.PLAY_WARNING_RECONNECT) {
            ToastUtils.showShort("请检查网络~");
        }
    }

    @Override
    public void onNetStatus(TXVodPlayer player, Bundle status) {

    }


    private void restartPlay() {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.resume();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser) {
            if (mTXCloudVideoView != null) {
                mTXCloudVideoView.onResume();
            }
            if (mTXVodPlayer != null) {
                mTXVodPlayer.resume();
            }
        } else {
            if (mTXCloudVideoView != null) {
                mTXCloudVideoView.onPause();
            }
            if (mTXVodPlayer != null) {
                mTXVodPlayer.pause();
            }
        }
        super.setUserVisibleHint(isVisibleToUser);

    }


    @Override
    public void onStop() {
        super.onStop();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onPause();
        }
        if (mTXVodPlayer != null) {
            mTXVodPlayer.pause();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onDestroy();
            mTXCloudVideoView = null;
        }
        stopPlay(true);
        mTXVodPlayer = null;

        if (mPhoneListener != null) {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Service.TELEPHONY_SERVICE);
            tm.listen(mPhoneListener, PhoneStateListener.LISTEN_NONE);
            mPhoneListener = null;
        }
    }

    protected void stopPlay(boolean clearLastFrame) {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.stopPlay(clearLastFrame);
        }
    }


    /**
     * ==========================================来电监听==========================================
     */
    private PhoneStateListener mPhoneListener = null;


    static class TXPhoneStateListener extends PhoneStateListener {
        WeakReference<TXVodPlayer> mPlayer;

        public TXPhoneStateListener(TXVodPlayer player) {
            mPlayer = new WeakReference<TXVodPlayer>(player);
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            TXVodPlayer player = mPlayer.get();
            switch (state) {
                //电话等待接听
                case TelephonyManager.CALL_STATE_RINGING:
                    if (player != null) player.setMute(true);
                    break;
                //电话接听
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (player != null) player.setMute(true);
                    break;
                //电话挂机
                case TelephonyManager.CALL_STATE_IDLE:
                    if (player != null) player.setMute(false);
                    break;
            }
        }
    }
}
