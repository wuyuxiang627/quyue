package com.game.jxj.ui.home.record.activity;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.game.jxj.GlideApp;
import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.game.jxj.config.enums.MusicType;
import com.game.jxj.model.entity.MusicListResp;
import com.game.jxj.ui.home.me.adapter.CommonViewPagerAdapter;
import com.game.jxj.ui.home.record.fragment.MusicListFragment;
import com.game.jxj.ui.home.record.indicator.NavigatorAdapter;
import com.gm.utils.ActivityUtil;
import com.gm.utils.StringUtils;
import com.gm.utils.TimeUtils;
import com.gm.utils.ToastUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class RecordMusicActivity extends SimpleActivity implements MusicListFragment.OnPlayMusicListener {

    @BindView(R.id.id_stickynavlayout_topview)
    LinearLayout idStickynavlayoutTopview;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.progress)
    MaterialProgressBar progress;
    @BindView(R.id.tv_time_progress)
    TextView tvTimeProgress;
    @BindView(R.id.tv_time_length)
    TextView tvTimeLength;
    @BindView(R.id.btn_user)
    Button btnUser;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.iv_sound_wave)
    ImageView ivSoundWave;
    @BindView(R.id.tv_xinge)
    TextView tvXinge;
    @BindView(R.id.tv_jingdian)
    TextView tvJingdian;
    @BindView(R.id.tv_chunyinyue)
    TextView tvChunyinyue;
    @BindView(R.id.tv_oumei)
    TextView tvOumei;
    @BindView(R.id.tv_dianyin)
    TextView tvDianyin;
    @BindView(R.id.tv_liuxing)
    TextView tvLiuxing;
    @BindView(R.id.tv_gaoxiao)
    TextView tvGaoxiao;
    @BindView(R.id.wu_dao)
    TextView wuDao;

    public static void launch(Context context) {
        ActivityUtil.startActivity(context, RecordMusicActivity.class);
    }


    @BindView(R.id.ibtn_back)
    ImageView ibtnBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.indicator)
    MagicIndicator indicator;
    @BindView(R.id.vp_content)
    ViewPager vpContent;

    private MediaPlayer mediaPlayer;
    private Disposable mDisposable;
    private String currentMusic;


    private String[] titles = {"热门歌曲", "我的收藏"};

    @Override
    protected int getLayout() {
        return R.layout.activity_record_musice;
    }

    @Override
    protected void initEventAndData() {

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        NavigatorAdapter navigatorAdapter = new NavigatorAdapter(Arrays.asList(titles), vpContent);
        commonNavigator.setAdapter(navigatorAdapter);
        indicator.setNavigator(commonNavigator);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MusicListFragment.getInstance(MusicListFragment.TYPE_HOT));
        fragments.add(MusicListFragment.getInstance(MusicListFragment.TYPE_COLLECT));

        CommonViewPagerAdapter adapter = new CommonViewPagerAdapter(getSupportFragmentManager(), fragments);
        vpContent.setAdapter(adapter);

        ViewPagerHelper.bind(indicator, vpContent);


        mediaPlayer = new MediaPlayer();

        //更新音乐的播放进度 ，显示格式“03:10”
        mDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (mediaPlayer.getCurrentPosition() < 0) {
                        return;
                    }
                    String progress = TimeUtils.second2MinuteSecond(mediaPlayer.getCurrentPosition() / 1000);
                    tvTimeProgress.setText(progress);
                });

    }


    @Override
    public void playMusic(MusicListResp resp) {

        llBottom.setVisibility(View.VISIBLE);
        //设置选择该音乐的信息
        GlideApp.with(mContext).load(resp.coverPath).into(ivCover);
        tvTimeLength.setText(TimeUtils.second2MinuteSecond(resp.length));

        try {
            if (StringUtils.isEmpty(resp.filePath)) {
                ToastUtils.showShort("该音乐没有资源");
                return;
            }
            currentMusic = resp.filePath;
            AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.music_play);
            ivSoundWave.setBackground(animationDrawable);
            animationDrawable.start();
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
            mediaPlayer.reset();
            mediaPlayer.setDataSource(resp.filePath);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepareAsync();
            progress.setVisibility(View.VISIBLE);
            mediaPlayer.setOnPreparedListener(mp -> {
                mediaPlayer.start();
                progress.setVisibility(View.GONE);
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {

        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        super.onDestroy();
    }


    @OnClick({R.id.tv_xinge, R.id.tv_jingdian, R.id.tv_chunyinyue, R.id.tv_oumei, R.id.tv_dianyin, R.id.tv_liuxing, R.id.tv_gaoxiao, R.id.wu_dao, R.id.btn_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_xinge:
                RecordMusicTypeActivity.launch(mContext, MusicType.TYPE_XINGE.getIndex());
                break;
            case R.id.tv_jingdian:
                RecordMusicTypeActivity.launch(mContext, MusicType.TYPE_JINGDIAN.getIndex());
                break;
            case R.id.tv_chunyinyue:
                RecordMusicTypeActivity.launch(mContext, MusicType.TYPE_CHUN_YINYUE.getIndex());
                break;
            case R.id.tv_oumei:
                RecordMusicTypeActivity.launch(mContext, MusicType.TYPE_OUMEI.getIndex());
                break;
            case R.id.tv_dianyin:
                RecordMusicTypeActivity.launch(mContext, MusicType.TYPE_DIANYIN.getIndex());
                break;
            case R.id.tv_liuxing:
                RecordMusicTypeActivity.launch(mContext, MusicType.TYPE_LIUXING.getIndex());
                break;
            case R.id.tv_gaoxiao:
                RecordMusicTypeActivity.launch(mContext, MusicType.TYPE_GAOXIAO.getIndex());
                break;
            case R.id.wu_dao:
                RecordMusicTypeActivity.launch(mContext, MusicType.TYPE_WUDAO.getIndex());
                break;
            case R.id.btn_user:
                if (!StringUtils.isEmpty(currentMusic)) {
                    EventBus.getDefault().post(currentMusic);
                    finish();
                }

                break;
        }
    }
}
