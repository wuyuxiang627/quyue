package com.game.jxj.ui.home.record.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.game.jxj.GlideApp;
import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.game.jxj.config.enums.MusicType;
import com.game.jxj.model.entity.MusicListReq;
import com.game.jxj.model.entity.MusicListResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.game.jxj.ui.home.record.adapter.MusicAdapter;
import com.game.jxj.ui.home.record.fragment.MusicListFragment;
import com.game.jxj.utils.RxUtil;
import com.gm.utils.ActivityUtil;
import com.gm.utils.ActivityUtils;
import com.gm.utils.StringUtils;
import com.gm.utils.TimeUtils;
import com.gm.utils.ToastUtils;
import com.gm.views.AbTitleBar;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class RecordMusicTypeActivity extends SimpleActivity implements SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnItemClickListener
        , MusicListFragment.OnPlayMusicListener {


    public static void launch(Context context, int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        ActivityUtil.startActivity(context, RecordMusicTypeActivity.class, bundle);
    }


    @BindView(R.id.title_bar)
    AbTitleBar titleBar;
    @BindView(R.id.rcy_content)
    RecyclerView rcyContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.progress)
    MaterialProgressBar progress;
    @BindView(R.id.tv_time_progress)
    TextView tvTimeProgress;
    @BindView(R.id.tv_time_length)
    TextView tvTimeLength;
    @BindView(R.id.iv_sound_wave)
    ImageView ivSoundWave;
    @BindView(R.id.btn_user)
    Button btnUser;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;


    private MediaPlayer mediaPlayer;
    private Disposable mDisposable;
    private int type;
    private int page = 1;
    private MusicAdapter adapter;
    private String currentMusic;


    @Override
    protected int getLayout() {
        return R.layout.activity_record_music_type;
    }

    @Override
    protected void initEventAndData() {
        type = getIntent().getIntExtra("type", 0);
        titleBar.setTitleText(MusicType.getNameByIndex(type).getName());

        rcyContent.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MusicAdapter();
        adapter.setOnLoadMoreListener(this, rcyContent);
        adapter.setOnItemClickListener(this);
        swipeRefresh.setOnRefreshListener(this);
        rcyContent.setAdapter(adapter);

        View emptyView = LayoutInflater.from(mContext).inflate(R.layout.view_empty_music, null);
        adapter.setEmptyView(emptyView);

        getMusicList();


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
    public void onRefresh() {
        page = 1;
        getMusicList();


    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getMusicList();


    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MusicListResp resp = (MusicListResp) adapter.getData().get(position);
        if (resp != null) {
            playMusic(resp);
        }
    }


    @OnClick(R.id.btn_user)
    public void onViewClicked() {
        if (!StringUtils.isEmpty(currentMusic)) {
            EventBus.getDefault().post(currentMusic);
            ActivityUtils.finishActivity(RecordMusicActivity.class);
            finish();
        }

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


    @SuppressLint("CheckResult")
    private void getMusicList() {
        MusicListReq listReq = new MusicListReq();
        listReq.musicType = type;
        listReq.isHot = 1;
        listReq.page = page;
        getHttpHelper().getMusicList(listReq)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<List<MusicListResp>>>(mContext) {
                                   @Override
                                   public void onNext(Optional<List<MusicListResp>> listOptional) {
                                       List<MusicListResp> resps = listOptional.get();
                                       if (page == 1) {
                                           //1刷新
                                           adapter.setNewData(resps);
                                           swipeRefresh.setRefreshing(false);
                                       } else {
                                           //加载更多
                                           adapter.addData(resps);
                                           adapter.loadMoreComplete();
                                           if (resps.size() == 0) {
                                               adapter.loadMoreEnd();
                                           } else {
                                               adapter.loadMoreEnd(false);
                                           }

                                       }
                                   }
                               }
                );
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


}
