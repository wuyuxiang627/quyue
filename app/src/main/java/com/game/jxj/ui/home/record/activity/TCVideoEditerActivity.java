package com.game.jxj.ui.home.record.activity;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.jxj.R;
import com.game.jxj.config.preference.TCConstants;
import com.game.jxj.ui.home.record.fragment.BeautyFragment;
import com.game.jxj.ui.home.record.fragment.CutterFragment;
import com.game.jxj.ui.home.record.fragment.MusicFragment;
import com.game.jxj.utils.PlayState;
import com.game.jxj.widget.videotimeline.VideoProgressController;
import com.gm.utils.ScreenUtils;
import com.gm.utils.ToastUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.ugc.TXVideoEditConstants;
import com.tencent.ugc.TXVideoEditer;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TCVideoEditerActivity extends AppCompatActivity implements
        TCVideoEditerWrapper.TXVideoPreviewListenerWrapper {

    private static final String TAG = "TCVideoEditerActivity";

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.editer_fl_video)
    FrameLayout editerFlVideo;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.iv_texiao)
    ImageView ivTexiao;
    @BindView(R.id.jianji)
    ImageView jianji;
    @BindView(R.id.yinyue)
    ImageView yinyue;
    @BindView(R.id.meiyan)
    ImageView meiyan;
    @BindView(R.id.editer_ib_play)
    ImageButton mIbPlay;
    @BindView(R.id.fl_video)
    FrameLayout flVideo;

    // 短视频SDK获取到的视频信息
    private TXVideoEditer mTXVideoEditer;                   // SDK接口类
    private long mVideoDuration;                            // 视频的总时长
    private int mVideoResolution = -1;                      // 分辨率类型（如果是从录制过来的话才会有，这参数）
    private int mVideoFrom;
    private String mRecordProcessedPath;

    private TXPhoneStateListener mPhoneListener;            // 电话监听
    private int mCurrentState = PlayState.STATE_NONE;       // 播放器当前状态\
    private KeyguardManager mKeyguardManager;
    public boolean isPreviewFinish;
    private long mPreviewAtTime;                            // 当前单帧预览的时间
    private VideoProgressController.VideoProgressSeekListener mVideoProgressSeekListener = new VideoProgressController.VideoProgressSeekListener() {
        @Override
        public void onVideoProgressSeek(long currentTimeMs) {


            previewAtTime(currentTimeMs);
        }

        @Override
        public void onVideoProgressSeekFinish(long currentTimeMs) {


            previewAtTime(currentTimeMs);
        }
    };


    private Fragment mCurrentFragment,                      // 标记当前的Fragment
            mCutterFragment,                                // 裁剪的Fragment
            mBeautyFragment,                                // 美颜滤镜Fragment
            mMusicFragment,                                 // 音乐背景Fragment
            mMotionFragment,                                // 动态滤镜的Fragment
            mBGMSettingFragment;                            // BGM设置的Fragment


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ScreenUtils.setFullScreen(this);
        setContentView(R.layout.activity_tcvideo_editer);
        ButterKnife.bind(this);
        TCVideoEditerWrapper wrapper = TCVideoEditerWrapper.getInstance();
        wrapper.addTXVideoPreviewListenerWrapper(this);

        mTXVideoEditer = wrapper.getEditer();
        if (mTXVideoEditer == null || wrapper.getTXVideoInfo() == null) {
            ToastUtils.showShort("状态异常，结束编辑");
            finish();
            return;
        }
        mVideoDuration = wrapper.getTXVideoInfo().duration;
        TCVideoEditerWrapper.getInstance().setCutterStartTime(0, mVideoDuration);

        mVideoResolution = getIntent().getIntExtra(TCConstants.VIDEO_RECORD_RESOLUTION, -1);
        mVideoFrom = getIntent().getIntExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_EDIT);
        // 录制经过预处理的视频路径，在编辑后需要删掉录制源文件
        mRecordProcessedPath = getIntent().getStringExtra(TCConstants.VIDEO_EDITER_PATH);

        initPhoneListener();
        previewVideo();// 开始预览视频
        mKeyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);

    }


    private void previewVideo() {
        initPlayerLayout();         // 初始化预览视频布局
        startPlay(TCVideoEditerWrapper.getInstance().getCutterStartTime(),
                TCVideoEditerWrapper.getInstance().getCutterEndTime());  // 开始播放
    }


    private void initPlayerLayout() {
        TXVideoEditConstants.TXPreviewParam param = new TXVideoEditConstants.TXPreviewParam();
        param.videoView = editerFlVideo;
        param.renderMode = TXVideoEditConstants.PREVIEW_RENDER_MODE_FILL_EDGE;
        mTXVideoEditer.initWithPreview(param);
    }


    /**
     * 调用mTXVideoEditer.previewAtTime后，需要记录当前时间，下次播放时从当前时间开始
     * x
     *
     * @param timeMs
     */
    public void previewAtTime(long timeMs) {
        pausePlay();
        isPreviewFinish = false;
        mTXVideoEditer.previewAtTime(timeMs);
        mPreviewAtTime = timeMs;
        mCurrentState = PlayState.STATE_PREVIEW_AT_TIME;
    }


    /**
     * 给子Fragment调用 （子Fragment不在意Activity中对于播放器的生命周期）
     */
    public void restartPlay() {
        stopPlay();
        startPlay(getCutterStartTime(), getCutterEndTime());
    }

    public void startPlay(long startTime, long endTime) {

        mTXVideoEditer.startPlayFromTime(startTime, endTime);
        mCurrentState = PlayState.STATE_PLAY;
        isPreviewFinish = false;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mIbPlay.setImageResource(R.drawable.ic_pause);
            }
        });
    }


    public void resumePlay() {
        if (mCurrentState == PlayState.STATE_PAUSE) {
            mTXVideoEditer.resumePlay();
            mCurrentState = PlayState.STATE_RESUME;
            mIbPlay.setImageResource(R.drawable.ic_pause);

        }
    }

    public void pausePlay() {
        if (mCurrentState == PlayState.STATE_RESUME || mCurrentState == PlayState.STATE_PLAY) {
            mTXVideoEditer.pausePlay();
            mCurrentState = PlayState.STATE_PAUSE;
            mIbPlay.setImageResource(R.drawable.ic_play);
        }
    }

    public void stopPlay() {
        if (mCurrentState == PlayState.STATE_RESUME || mCurrentState == PlayState.STATE_PLAY ||
                mCurrentState == PlayState.STATE_STOP || mCurrentState == PlayState.STATE_PAUSE) {
            mTXVideoEditer.stopPlay();
            mCurrentState = PlayState.STATE_STOP;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mIbPlay.setImageResource(R.drawable.ic_play);
                }
            });
        }
    }


    @Override
    public void onPreviewProgressWrapper(int time) {
        // 视频的进度回调是异步的，如果不是处于播放状态，那么无需修改进度
        if (mCurrentState == PlayState.STATE_RESUME || mCurrentState == PlayState.STATE_PLAY) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mCurrentFragment instanceof CutterFragment && !mCurrentFragment.isHidden()) {
                        ((CutterFragment) mCurrentFragment).setCurrentTimeMs(time);
                    }
                }
            });
        }
    }

    @Override
    public void onPreviewFinishedWrapper() {
        TXCLog.d(TAG, "---------------onPreviewFinished-----------------");
        isPreviewFinish = true;
        stopPlay();
        // 如果当前不是动态滤镜界面或者时间特效界面，那么会自动开始重复播放
        startPlay(getCutterStartTime(), getCutterEndTime());

    }


    private void initPhoneListener() {
        //设置电话监听
        if (mPhoneListener == null) {
            mPhoneListener = new TXPhoneStateListener(this);
            TelephonyManager tm = (TelephonyManager) this.getApplicationContext().getSystemService(Service.TELEPHONY_SERVICE);
            tm.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }


    private long getCutterStartTime() {
        return mCutterFragment != null ? ((CutterFragment) mCutterFragment).getCutterStartTime() : TCVideoEditerWrapper.getInstance().getCutterStartTime();
    }

    private long getCutterEndTime() {
        return mCutterFragment != null ? ((CutterFragment) mCutterFragment).getCutterEndTime() : TCVideoEditerWrapper.getInstance().getCutterEndTime();
    }

    public VideoProgressController.VideoProgressSeekListener getmVideoProgressSeekListener() {
        return mVideoProgressSeekListener;
    }


    private void showCutterFragment() {
        if (mCutterFragment == null) {
            mCutterFragment = new CutterFragment();
        }
        showFragment(mCutterFragment, "cutter_fragment");
    }

    private void showBeautyFragment() {
        if (mBeautyFragment == null) {
            mBeautyFragment = new BeautyFragment();
        }
        showFragment(mBeautyFragment, "beauty_fragment");
    }

    private void showMusicFragment() {
        if (mMusicFragment == null) {
            mMusicFragment = new MusicFragment();
        }
        showFragment(mMusicFragment, "music_fragment");
    }


    private void showFragment(Fragment fragment, String tag) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentFragment != null) {
            transaction.hide(mCurrentFragment);
        }
        if (!fragment.isAdded()) {
            transaction.add(R.id.fl_container, fragment, tag);
        } else {
            transaction.show(fragment);
        }
        mCurrentFragment = fragment;
        transaction.commit();
    }

    @OnClick({R.id.iv_back, R.id.tv_save,
            R.id.tv_next, R.id.iv_texiao,
            R.id.jianji, R.id.yinyue,
            R.id.meiyan, R.id.editer_ib_play,
            R.id.fl_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.tv_save:
                break;
            case R.id.tv_next:
                break;
            case R.id.iv_texiao:
                break;
            case R.id.jianji:
                showCutterFragment();
                break;
            case R.id.yinyue:
                showMusicFragment();
                break;
            case R.id.meiyan:
                showBeautyFragment();
                break;
            case R.id.editer_ib_play:
                playVideo(false);
                break;
            case R.id.fl_video:
                if (mCurrentFragment instanceof BeautyFragment && mCurrentFragment.isVisible()) {
                    getSupportFragmentManager().beginTransaction().hide(mCurrentFragment).commit();
                }
                break;
        }
    }


    /**
     * 如果是滤镜特效的界面调用：
     * 1、在播放状态下，按住滤镜不会停止播放
     * 2、播放到末尾了，按住时，不会重新播放
     *
     * @param isMotionFilter
     */
    public void playVideo(boolean isMotionFilter) {
        TXCLog.i(TAG, "editer_ib_play clicked, mCurrentState = " + mCurrentState);
        if (mCurrentState == PlayState.STATE_NONE || mCurrentState == PlayState.STATE_STOP) {
            startPlay(getCutterStartTime(), getCutterEndTime());
        } else if ((mCurrentState == PlayState.STATE_RESUME || mCurrentState == PlayState.STATE_PLAY) && !isMotionFilter) {
            pausePlay();
        } else if (mCurrentState == PlayState.STATE_PAUSE) {
            resumePlay();
        } else if (mCurrentState == PlayState.STATE_PREVIEW_AT_TIME) {
            if ((mPreviewAtTime >= getCutterEndTime() || mPreviewAtTime <= getCutterStartTime()) && !isMotionFilter) {
                startPlay(getCutterStartTime(), getCutterEndTime());
            } else if (!TCVideoEditerWrapper.getInstance().isReverse()) {
                startPlay(mPreviewAtTime, getCutterEndTime());
            } else {
                startPlay(getCutterStartTime(), mPreviewAtTime);
            }
        }
    }


    private void stopGenerate() {
//        if (mCurrentState == PlayState.STATE_GENERATE) {
//            mTvDone.setEnabled(true);
//            mTvDone.setClickable(true);
//            mWorkLoadingProgress.dismiss();
//            Toast.makeText(TCVideoEditerActivity.this, "取消视频生成", Toast.LENGTH_SHORT).show();
//            mWorkLoadingProgress.setProgress(0);
//            mCurrentState = PlayState.STATE_NONE;
//            if (mTXVideoEditer != null) {
//                mTXVideoEditer.cancel();
//            }
//        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPhoneListener != null) {
            TelephonyManager tm = (TelephonyManager) this.getApplicationContext().getSystemService(Service.TELEPHONY_SERVICE);
            tm.listen(mPhoneListener, PhoneStateListener.LISTEN_NONE);
        }
        if (mTXVideoEditer != null) {
            stopPlay();
            mTXVideoEditer.setVideoGenerateListener(null);
            //编辑完成后，销毁资源，避免影响处理下一个视频
            mTXVideoEditer.release();
            mTXVideoEditer = null;
        }
        // 清除对TXVideoEditer的引用以及相关配置
        TCVideoEditerWrapper.getInstance().removeTXVideoPreviewListenerWrapper(this);
        TCVideoEditerWrapper.getInstance().clear();

    }

    /*********************************************监听电话状态**************************************************/
    static class TXPhoneStateListener extends PhoneStateListener {
        WeakReference<TCVideoEditerActivity> mEditer;

        public TXPhoneStateListener(TCVideoEditerActivity editer) {
            mEditer = new WeakReference<>(editer);
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            TCVideoEditerActivity activity = mEditer.get();
            if (activity == null) return;
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:  //电话等待接听
                case TelephonyManager.CALL_STATE_OFFHOOK:  //电话接听
                    // 生成状态 取消生成
                    if (activity.mCurrentState == PlayState.STATE_GENERATE) {
                        activity.stopGenerate();
                    }
                    // 直接停止播放
                    activity.stopPlay();
                    break;
                //电话挂机
                case TelephonyManager.CALL_STATE_IDLE:
                    // 重新开始播放
                    activity.restartPlay();
                    break;
            }
        }
    }
}
