package com.game.jxj.ui.home.record.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.game.jxj.R;
import com.game.jxj.config.preference.Constants;
import com.game.jxj.config.preference.TCConstants;
import com.game.jxj.utils.FileUtils;
import com.game.jxj.view.RecordProgressView;
import com.game.jxj.view.RippleBackground;
import com.game.jxj.widget.BeautySettingPannel;
import com.gm.utils.ActivityUtil;
import com.gm.utils.ScreenUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import com.tencent.ugc.TXUGCRecord;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VideoRecordActivity extends Activity implements
        TXRecordCommon.ITXVideoRecordListener,
        BeautySettingPannel.IOnBeautyParamsChangeListener,
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        RadioGroup.OnCheckedChangeListener,
        ScaleGestureDetector.OnScaleGestureListener,
        TXRecordCommon.ITXBGMNotify {


    public static void launch(Context context) {
        ActivityUtil.startActivity(context, VideoRecordActivity.class);
    }

    private static final String TAG = "VideoRecordActivity";
    private static final String OUTPUT_DIR_NAME = "wodian";
    private boolean mStartPreview = false;
    private TXUGCRecord mTXCameraRecord;
    public static final String RECORD_CONFIG_BITE_RATE = "record_config_bite_rate";
    // 录制方向
    private int mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
    // 渲染方向
    private int mRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;
    //录制的清晰度
    private int mRecommendQuality = TXRecordCommon.VIDEO_QUALITY_MEDIUM;
    //录制最小时长
    private int mMinDuration = 5 * 1000;
    //录制最大时长
    private int mMaxDuration = 30 * 1000;
    //摄像头方向
    private boolean mFront = true;
    // 码率
    private int mBiteRate;
    //拍摄速度
    private int mRecordSpeed = TXRecordCommon.RECORD_SPEED_NORMAL;
    //拍摄视频吧比例
    private int mCurrentAspectRatio = TXRecordCommon.VIDEO_ASPECT_RATIO_9_16;
    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mLastScaleFactor;
    private float mScaleFactor;
    private RelativeLayout mRecordRelativeLayout;
    private BeautySettingPannel.BeautyParams mBeautyParams = new BeautySettingPannel.BeautyParams();
    // 闪光灯的状态
    private boolean mIsTorchOpen = false;
    private long mLastClickTime;
    private boolean mRecording = false;
    private boolean mPause = false;
    // 回删状态
    private boolean isSelected = false;
    private String mBGMPath;
    private int mBGMDuration;
    private String mBGMPlayingPath;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusListener;
    private TXRecordCommon.TXRecordResult mTXRecordResult;
    private long mDuration; // 视频总时长
    private TXCloudVideoView mVideoView;
    private BeautySettingPannel mSettingPannel;
    private FrameLayout mFlMask;
    private RadioGroup mRadioGroup;
    private ImageView mFlashlight;
    private RecordProgressView mRecordProgressView;
    private TextView mTvRecordTime;
    private LinearLayout mBottomFunction;
    private ImageView mDelayRecord;
    private ImageView mDeleteRecord;
    private ImageView mPreview;
    private LinearLayout mMusic;
    private LinearLayout mLocal;
    private RelativeLayout rlTopFunction;
    private RippleBackground mRippleBackground;
    private ImageView mIvRecordSpeed;
    private boolean mShowRecordSpeed;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.setFullScreen(this);
        setContentView(R.layout.activity_tcvideo_record);
        findView();
        initView();
        startCameraPreview();
        EventBus.getDefault().register(this);

    }

    private void initView() {
        mVideoView.enableHardwareDecode(true);
        mSettingPannel.setBeautyParamsChangeListener(this);
        mFlMask.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);
        mScaleGestureDetector = new ScaleGestureDetector(this, this);
        mRadioGroup.setOnCheckedChangeListener(this);
        mRecordProgressView.setMaxDuration(mMaxDuration);
        mRecordProgressView.setMinDuration(mMinDuration);
        mFlashlight.setEnabled(false);

    }

    private void findView() {
        mVideoView = findViewById(R.id.video_view);
        mSettingPannel = findViewById(R.id.setting_pannel);
        mFlMask = findViewById(R.id.mask);
        mRecordRelativeLayout = findViewById(R.id.rl_record_layout);
        mRadioGroup = findViewById(R.id.rg_record_speed);
        mFlashlight = findViewById(R.id.iv_flashlight);
        mRecordProgressView = findViewById(R.id.record_progress_view);
        mTvRecordTime = findViewById(R.id.tv_record_time);
        mBottomFunction = findViewById(R.id.ll_bottom_function);
        mDelayRecord = findViewById(R.id.iv_delay_record);
        mDeleteRecord = findViewById(R.id.iv_delete);
        mPreview = findViewById(R.id.iv_preview);
        mMusic = findViewById(R.id.layout_music);
        mLocal = findViewById(R.id.ll_local);
        rlTopFunction = findViewById(R.id.rl_top_function);
        mRippleBackground = findViewById(R.id.content);
        mIvRecordSpeed = findViewById(R.id.iv_record_speed);
    }


    private void startCameraPreview() {

        if (mStartPreview) return;
        mStartPreview = true;


        mTXCameraRecord = TXUGCRecord.getInstance(getApplicationContext());
        mTXCameraRecord.setVideoRecordListener(this);

        mTXCameraRecord.setHomeOrientation(mHomeOrientation);
        mTXCameraRecord.setRenderRotation(mRenderRotation);
        mTXCameraRecord.setBGMNofify(this);

        TXRecordCommon.TXUGCSimpleConfig simpleConfig = new TXRecordCommon.TXUGCSimpleConfig();
        simpleConfig.videoQuality = mRecommendQuality;
        simpleConfig.minDuration = mMinDuration;
        simpleConfig.maxDuration = mMaxDuration;
        simpleConfig.isFront = mFront;
        simpleConfig.needEdit = true;

        mTXCameraRecord.startCameraSimplePreview(simpleConfig, mVideoView);
        //录制速度
        mTXCameraRecord.setRecordSpeed(mRecordSpeed);
        //视频比例
        mTXCameraRecord.setAspectRatio(mCurrentAspectRatio);
        //0风格、1磨皮、2美白、3红润
        mTXCameraRecord.setBeautyDepth(mBeautyParams.mBeautyStyle, mBeautyParams.mBeautyLevel, mBeautyParams.mWhiteLevel, mBeautyParams.mRuddyLevel);
        //滤镜
        mTXCameraRecord.setFilter(mBeautyParams.mFilterBmp);


    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_beauty:
                mSettingPannel.setVisibility(View.VISIBLE);
                mRecordRelativeLayout.setVisibility(View.GONE);
                break;
            case R.id.iv_switch:
                mFront = !mFront;
                mIsTorchOpen = false;
                setTorchView();
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.switchCamera(mFront);
                }
                break;
            case R.id.iv_flashlight:
                toggleFlashLight();
                break;
            case R.id.ibtn_back:
                finish();
                break;
            case R.id.centerImage:
                switchRecord();
                break;
            case R.id.btn_music:
                ActivityUtil.startActivity(VideoRecordActivity.this, RecordMusicActivity.class);
                break;
            case R.id.iv_delete:
                deleteLastPart();
                break;
            case R.id.iv_preview:
                stopRecord();
                break;
            case R.id.iv_record_speed:
                mShowRecordSpeed = !mShowRecordSpeed;
                mRadioGroup.setVisibility(mShowRecordSpeed ? View.VISIBLE : View.INVISIBLE);
                break;

        }
    }

    @Override
    public void onRecordEvent(int event, Bundle param) {
        TXCLog.d(TAG, "onRecordEvent event id = " + event);
        if (event == TXRecordCommon.EVT_ID_PAUSE) {
            mRecordProgressView.clipComplete();
        } else if (event == TXRecordCommon.EVT_CAMERA_CANNOT_USE) {
            Toast.makeText(this, "摄像头打开失败，请检查权限", Toast.LENGTH_SHORT).show();
        } else if (event == TXRecordCommon.EVT_MIC_CANNOT_USE) {
            Toast.makeText(this, "麦克风打开失败，请检查权限", Toast.LENGTH_SHORT).show();
        } else if (event == TXRecordCommon.EVT_ID_RESUME) {

        }
    }

    @Override
    public void onRecordProgress(long milliSecond) {
        TXCLog.i(TAG, "onRecordProgress, mRecordProgressView = " + mRecordProgressView);
        if (mRecordProgressView == null) {
            return;
        }
        mRecordProgressView.setProgress((int) milliSecond);
        float timeSecondFloat = milliSecond / 1000f;
        int timeSecond = Math.round(timeSecondFloat);
        mTvRecordTime.setText(String.format(Locale.CHINA, "00:%02d", timeSecond));
        if (timeSecondFloat < mMinDuration / 1000) {
            mPreview.setEnabled(false);
        } else {
            mPreview.setEnabled(true);
        }
    }

    @Override
    public void onRecordComplete(TXRecordCommon.TXRecordResult result) {


        mTXRecordResult = result;

        TXCLog.i(TAG, "onRecordComplete, result retCode = " + result.retCode + ", descMsg = " + result.descMsg + ", videoPath + " + result.videoPath + ", coverPath = " + result.coverPath);
        if (mTXRecordResult.retCode < 0) {

            mRecording = false;

            int timeSecond = mTXCameraRecord.getPartsManager().getDuration() / 1000;
            mTvRecordTime.setText(String.format(Locale.CHINA, "00:%02d", timeSecond));
            Toast.makeText(VideoRecordActivity.this.getApplicationContext(), "录制失败，原因：" + mTXRecordResult.descMsg, Toast.LENGTH_SHORT).show();
        } else {
            mDuration = mTXCameraRecord.getPartsManager().getDuration();
            if (mTXCameraRecord != null) {
                mTXCameraRecord.getPartsManager().deleteAllParts();
            }

            startEditVideo();
        }

    }

    @Override
    public void onBeautyParamsChange(BeautySettingPannel.BeautyParams params, int key) {

        if (mTXCameraRecord == null) return;
        switch (key) {
            case BeautySettingPannel.BEAUTY_PARAM_BEAUTY:
                mBeautyParams.mBeautyLevel = params.mBeautyLevel;
                mTXCameraRecord.setBeautyDepth(mBeautyParams.mBeautyStyle, mBeautyParams.mBeautyLevel, mBeautyParams.mWhiteLevel, mBeautyParams.mRuddyLevel);
                break;
            case BeautySettingPannel.BEAUTY_PARAM_WHITE:
                mBeautyParams.mWhiteLevel = params.mWhiteLevel;
                mTXCameraRecord.setBeautyDepth(mBeautyParams.mBeautyStyle, mBeautyParams.mBeautyLevel, mBeautyParams.mWhiteLevel, mBeautyParams.mRuddyLevel);
                break;
            case BeautySettingPannel.BEAUTY_PARAM_RUDDY:
                mBeautyParams.mRuddyLevel = params.mRuddyLevel;
                mTXCameraRecord.setBeautyDepth(mBeautyParams.mBeautyStyle, mBeautyParams.mBeautyLevel, mBeautyParams.mWhiteLevel, mBeautyParams.mRuddyLevel);
                break;
            case BeautySettingPannel.BEAUTY_PARAM_FILTER:
                mBeautyParams.mFilterBmp = params.mFilterBmp;
                mTXCameraRecord.setFilter(mBeautyParams.mFilterBmp);
                break;

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == mFlMask) {
            if (event.getPointerCount() == 1) {
                mGestureDetector.onTouchEvent(event);
            } else if (event.getPointerCount() >= 2) {
                mScaleGestureDetector.onTouchEvent(event);
            }

        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        if (mSettingPannel.isShown()) {
            mSettingPannel.setVisibility(View.GONE);
            mRecordRelativeLayout.setVisibility(View.VISIBLE);
        }

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_slowest:
                mRecordSpeed = TXRecordCommon.RECORD_SPEED_SLOWEST;
                break;
            case R.id.rb_slow:
                mRecordSpeed = TXRecordCommon.RECORD_SPEED_SLOW;
                break;
            case R.id.rb_normal:
                mRecordSpeed = TXRecordCommon.RECORD_SPEED_NORMAL;
                break;
            case R.id.rb_fast:
                mRecordSpeed = TXRecordCommon.RECORD_SPEED_FAST;
                break;
            case R.id.rb_fastest:
                mRecordSpeed = TXRecordCommon.RECORD_SPEED_FASTEST;
                break;
        }
        mTXCameraRecord.setRecordSpeed(mRecordSpeed);

    }


    private void setTorchView() {
        if (mFront) {
            mFlashlight.setAlpha(0.5f);
            mFlashlight.setEnabled(false);
        } else {
            mFlashlight.setAlpha(1.0f);
            mFlashlight.setEnabled(true);
        }
    }

    private void toggleFlashLight() {
        if (mTXCameraRecord == null) {
            return;
        }
        if (mIsTorchOpen) {
            mTXCameraRecord.toggleTorch(false);
            mFlashlight.setImageResource(R.drawable.icon_flashlight);
        } else {
            mTXCameraRecord.toggleTorch(true);
            mFlashlight.setImageResource(R.drawable.yikaishanguangdeng);
        }

        mIsTorchOpen = !mIsTorchOpen;
    }

    private void switchRecord() {
        long currentClickTime = System.currentTimeMillis();
        if (currentClickTime - mLastClickTime < 200) {
            return;
        }
        if (mRecording) {
            if (mPause) {
                if (mTXCameraRecord.getPartsManager().getPartsPathList().size() == 0) {
                    startRecord();
                } else {
                    resumeRecord();
                }
            } else {
                pauseRecord();
            }
        } else {
            startRecord();
        }
        mLastClickTime = currentClickTime;
    }

    private void startRecord() {
        // 在开始录制的时候，就不能再让activity旋转了，否则生成视频出错
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if (mTXCameraRecord == null) {
            mTXCameraRecord = TXUGCRecord.getInstance(this.getApplicationContext());
        }

        String customVideoPath = getCustomVideoOutputPath();
        String customCoverPath = customVideoPath.replace(".mp4", ".jpg");
        String customPartFolder = getCustomVideoPartFolder();

        int result = mTXCameraRecord.startRecord(customVideoPath, customPartFolder, customCoverPath);
        if (result != TXRecordCommon.START_RECORD_OK) {
            if (result == TXRecordCommon.START_RECORD_ERR_NOT_INIT) {
                Toast.makeText(VideoRecordActivity.this.getApplicationContext(), "别着急，画面还没出来", Toast.LENGTH_SHORT).show();
            } else if (result == TXRecordCommon.START_RECORD_ERR_IS_IN_RECORDING) {
                Toast.makeText(VideoRecordActivity.this.getApplicationContext(), "还有录制的任务没有结束", Toast.LENGTH_SHORT).show();
            } else if (result == TXRecordCommon.START_RECORD_ERR_VIDEO_PATH_IS_EMPTY) {
                Toast.makeText(VideoRecordActivity.this.getApplicationContext(), "传入的视频路径为空", Toast.LENGTH_SHORT).show();
            } else if (result == TXRecordCommon.START_RECORD_ERR_API_IS_LOWER_THAN_18) {
                Toast.makeText(VideoRecordActivity.this.getApplicationContext(), "版本太低", Toast.LENGTH_SHORT).show();
            }
            // 增加了TXUgcSDK.licence校验的返回错误码
            else if (result == TXRecordCommon.START_RECORD_ERR_LICENCE_VERIFICATION_FAILED) {
                Toast.makeText(VideoRecordActivity.this.getApplicationContext(), "licence校验失败", Toast.LENGTH_SHORT).show();
            }
//            mTXCameraRecord.setVideoRecordListener(null);
//            mTXCameraRecord.stopRecord();
            return;
        }

        mRadioGroup.setVisibility(View.INVISIBLE);
        mRippleBackground.startRippleAnimation();
        rlTopFunction.setVisibility(View.INVISIBLE);
        mTvRecordTime.setVisibility(View.VISIBLE);
        mBottomFunction.setVisibility(View.INVISIBLE);
        mDelayRecord.setEnabled(false);
        mMusic.setVisibility(View.INVISIBLE);
        mLocal.setVisibility(View.INVISIBLE);
        mPreview.setVisibility(View.INVISIBLE);
        mDeleteRecord.setVisibility(View.INVISIBLE);
        mDeleteRecord.setEnabled(false);

        if (!TextUtils.isEmpty(mBGMPath)) {
            mBGMDuration = mTXCameraRecord.setBGM(mBGMPath);
            mTXCameraRecord.playBGMFromTime(0, mBGMDuration);
            mBGMPlayingPath = mBGMPath;
            TXCLog.i(TAG, "music duration = " + mTXCameraRecord.getMusicDuration(mBGMPath));
        }
        mRecording = true;
        mPause = false;
        requestAudioFocus();
    }


    private void resumeRecord() {
        if (mTXCameraRecord == null) {
            return;
        }
        int startResult = mTXCameraRecord.resumeRecord();
        if (startResult != TXRecordCommon.START_RECORD_OK) {
            TXCLog.i(TAG, "resumeRecord, startResult = " + startResult);
            if (startResult == TXRecordCommon.START_RECORD_ERR_NOT_INIT) {
                Toast.makeText(VideoRecordActivity.this.getApplicationContext(), "别着急，画面还没出来", Toast.LENGTH_SHORT).show();
            } else if (startResult == TXRecordCommon.START_RECORD_ERR_IS_IN_RECORDING) {
                Toast.makeText(VideoRecordActivity.this.getApplicationContext(), "还有录制的任务没有结束", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        if (!TextUtils.isEmpty(mBGMPath)) {
            if (mBGMPlayingPath == null || !mBGMPath.equals(mBGMPlayingPath)) {
                mTXCameraRecord.setBGM(mBGMPath);
                mTXCameraRecord.playBGMFromTime(0, mBGMDuration);
                mBGMPlayingPath = mBGMPath;
            } else {
                mTXCameraRecord.resumeBGM();
            }
        }

        mRadioGroup.setVisibility(View.INVISIBLE);
        mRippleBackground.startRippleAnimation();
        rlTopFunction.setVisibility(View.INVISIBLE);
        mTvRecordTime.setVisibility(View.VISIBLE);
        mBottomFunction.setVisibility(View.INVISIBLE);
        mDelayRecord.setEnabled(false);
        mMusic.setVisibility(View.INVISIBLE);
        mLocal.setVisibility(View.INVISIBLE);
        mPreview.setVisibility(View.INVISIBLE);
        mDeleteRecord.setVisibility(View.INVISIBLE);
        mDeleteRecord.setEnabled(false);

        mPause = false;
        isSelected = false;
        requestAudioFocus();

    }


    private void pauseRecord() {
        mPause = true;
        if (mTXCameraRecord != null) {
            if (!TextUtils.isEmpty(mBGMPlayingPath)) {
                mTXCameraRecord.pauseBGM();
            }
            mTXCameraRecord.pauseRecord();
        }

        mRadioGroup.setVisibility(mShowRecordSpeed ? View.VISIBLE : View.INVISIBLE);
        mRippleBackground.stopRippleAnimation();
        rlTopFunction.setVisibility(View.VISIBLE);
        mBottomFunction.setVisibility(View.VISIBLE);
        mPreview.setVisibility(View.VISIBLE);
        mDeleteRecord.setVisibility(View.VISIBLE);
        mDeleteRecord.setEnabled(true);
        abandonAudioFocus();

    }


    private void stopRecord() {
        if (mTXCameraRecord != null) {
            mTXCameraRecord.stopBGM();
            mTXCameraRecord.stopRecord();
        }

        mRecording = false;
        mPause = false;
        abandonAudioFocus();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                for (int ret : grantResults) {
                    if (ret != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                startCameraPreview();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        TXCLog.i(TAG, "onResume");

        onActivityRotation();
        if (hasPermission()) {
            startCameraPreview();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        TXCLog.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TXCLog.i(TAG, "onDestroy");
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        onActivityRotation();
        if (mTXCameraRecord != null) {
            mTXCameraRecord.stopCameraPreview();
        }

        if (mRecording && !mPause) {
            pauseRecord();
        }
        if (mTXCameraRecord != null) {
            mTXCameraRecord.pauseBGM();
        }
        mStartPreview = false;

        startCameraPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        TXCLog.i(TAG, "onPause");
        if (mTXCameraRecord != null) {
            mTXCameraRecord.setVideoProcessListener(null); // 这里要取消监听，否则在上面的回调中又会重新开启预览
            mTXCameraRecord.stopCameraPreview();
            mStartPreview = false;
            // 设置闪光灯的状态为关闭
            if (mIsTorchOpen) {
                mIsTorchOpen = false;
                setTorchView();
            }
        }
        if (mRecording && !mPause) {
            pauseRecord();
        }
        if (mTXCameraRecord != null) {
            mTXCameraRecord.pauseBGM();
        }
    }

    private String getCustomVideoOutputPath() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
        String time = sdf.format(new Date(currentTime));
        String outputDir = Constants.PATH_SDCARD;
        File outputFolder = new File(outputDir);
        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }
        String tempOutputPath = outputDir + File.separator + "Wodian_" + time + ".mp4";
        return tempOutputPath;
    }

    //自定义分段视频存储目录
    private String getCustomVideoPartFolder() {
        String outputDir = Constants.PATH_SDCARD+ File.separator + "UGCParts";
        return outputDir;
    }


    private void requestAudioFocus() {
        if (null == mAudioManager) {
            mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        }

        if (null == mOnAudioFocusListener) {
            mOnAudioFocusListener = focusChange -> {
                try {
                    TXCLog.i(TAG, "requestAudioFocus, onAudioFocusChange focusChange = " + focusChange);

                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        pauseRecord();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        pauseRecord();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

                    } else {
                        pauseRecord();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }
        try {
            mAudioManager.requestAudioFocus(mOnAudioFocusListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void abandonAudioFocus() {
        try {
            if (null != mAudioManager && null != mOnAudioFocusListener) {
                mAudioManager.abandonAudioFocus(mOnAudioFocusListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        int maxZoom = mTXCameraRecord.getMaxZoom();
        if (maxZoom == 0) {
            TXCLog.i(TAG, "camera not support zoom");
            return false;
        }

        float factorOffset = scaleGestureDetector.getScaleFactor() - mLastScaleFactor;

        mScaleFactor += factorOffset;
        mLastScaleFactor = scaleGestureDetector.getScaleFactor();
        if (mScaleFactor < 0) {
            mScaleFactor = 0;
        }
        if (mScaleFactor > 1) {
            mScaleFactor = 1;
        }

        int zoomValue = Math.round(mScaleFactor * maxZoom);
        mTXCameraRecord.setZoom(zoomValue);
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        mLastScaleFactor = scaleGestureDetector.getScaleFactor();
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    /**
     * 用来在activity随着重力感应切换方向时，切换横竖屏录制
     * 注意：使用时，录制过程中或暂停后不允许切换横竖屏，如果开始录制时使用的是横屏录制，那么整段录制都要用横屏，否则录制失败。
     */
    protected void onActivityRotation() {
        // 自动旋转打开，Activity随手机方向旋转之后，需要改变录制方向
        int mobileRotation = this.getWindowManager().getDefaultDisplay().getRotation();
        mRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT; // 渲染方向，因为activity也旋转了，本地渲染相对正方向的角度为0。
        mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
        switch (mobileRotation) {
            case Surface.ROTATION_0:
                mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
                break;
            case Surface.ROTATION_90:
                mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT;
                break;
            case Surface.ROTATION_270:
                mHomeOrientation = TXLiveConstants.VIDEO_ANGLE_HOME_LEFT;
                break;
            default:
                break;
        }
    }


    private void deleteLastPart() {
        if (mRecording && !mPause) {
            return;
        }
        if (!isSelected) {
            isSelected = true;
            mRecordProgressView.selectLast();
        } else {
            isSelected = false;
            mRecordProgressView.deleteLast();
            mTXCameraRecord.getPartsManager().deleteLastPart();
            int timeSecond = mTXCameraRecord.getPartsManager().getDuration() / 1000;
            mTvRecordTime.setText(String.format(Locale.CHINA, "00:%02d", timeSecond));
            if (timeSecond < mMinDuration / 1000) {
                mPreview.setEnabled(false);
            } else {
                mPreview.setEnabled(true);
            }

            if (mTXCameraRecord.getPartsManager().getPartsPathList().size() == 0) {

                mMusic.setVisibility(View.VISIBLE);
                mLocal.setVisibility(View.VISIBLE);
                mDeleteRecord.setVisibility(View.GONE);
                mPreview.setVisibility(View.GONE);
            }
        }
    }


    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this,
                        permissions.toArray(new String[0]),
                        100);
                return false;
            }
        }

        return true;
    }


    private void back() {
        if (!mRecording) {
            releaseRecord();
            finish();
        }
        if (mPause) {
            if (mTXCameraRecord != null) {
                mTXCameraRecord.getPartsManager().deleteAllParts();
            }
            releaseRecord();
            finish();
        } else {
            pauseRecord();
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }

    // OnScaleGestureListener回调end

    private void releaseRecord() {
        if (mRecordProgressView != null) {
            mRecordProgressView.release();
        }

        if (mTXCameraRecord != null) {
            mTXCameraRecord.stopBGM();
            mTXCameraRecord.stopCameraPreview();
            mTXCameraRecord.setVideoRecordListener(null);
            mTXCameraRecord.getPartsManager().deleteAllParts();
            mTXCameraRecord.release();
            mTXCameraRecord = null;
            mStartPreview = false;
        }
        abandonAudioFocus();
    }


    @Subscribe
    public void OnSelectedBGM(String filePath) {
        if (filePath != null) {
            mBGMPath = filePath;
        }

    }

    @Override
    public void onBGMStart() {

    }

    @Override
    public void onBGMProgress(long l, long l1) {

    }

    @Override
    public void onBGMComplete(int i) {

    }


    private void startEditVideo() {
        Intent intent = new Intent(this, TCVideoPreprocessActivity.class);
        intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, TXRecordCommon.VIDEO_RESOLUTION_720_1280);
        FileUtils.deleteFile(mTXRecordResult.coverPath);
        intent.putExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_UGC_RECORD);
        intent.putExtra(TCConstants.VIDEO_EDITER_PATH, mTXRecordResult.videoPath);
        intent.putExtra(TCConstants.VIDEO_RECORD_COVERPATH, mTXRecordResult.coverPath);
        startActivity(intent);
        finish();
    }
}
