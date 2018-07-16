package com.game.jxj.ui.home.record.fragment;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.game.jxj.R;
import com.game.jxj.base.App;
import com.game.jxj.config.preference.Constants;
import com.game.jxj.model.entity.MusicListReq;
import com.game.jxj.model.entity.MusicListResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.game.jxj.ui.home.record.activity.RecordMusicActivity;
import com.game.jxj.ui.home.record.activity.TCVideoEditerActivity;
import com.game.jxj.ui.home.record.activity.TCVideoEditerWrapper;
import com.game.jxj.ui.home.record.adapter.EditMusicAdapter;
import com.game.jxj.utils.RxUtil;
import com.gm.utils.StringUtils;
import com.gm.utils.ToastUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.tencent.ugc.TXVideoEditer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author jxj
 * @date 2018/7/5
 */
public class MusicFragment extends Fragment implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.sb_voice_1)
    SeekBar sbVoice1;
    @BindView(R.id.sb_voice_2)
    SeekBar sbVoice2;
    @BindView(R.id.rcy_content)
    RecyclerView rcyContent;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    @BindView(R.id.iv_ok)
    ImageView ivOk;
    Unbinder unbinder;
    private EditMusicAdapter mAdapter;
    private TXVideoEditer editer;
    private String BGMPath;
    private String mFileName;
    private MediaPlayer mediaPlayer;
    private boolean isEmptyMusci;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        releaseMediaPlayer();

    }

    @Override
    public void onStop() {
        super.onStop();
        stopMeidaplayer();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_music, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editer = TCVideoEditerWrapper.getInstance().getEditer();
        mediaPlayer = new MediaPlayer();


        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcyContent.setLayoutManager(manager);

        mAdapter = new EditMusicAdapter();
        mAdapter.setOnItemClickListener(this);
        rcyContent.setAdapter(mAdapter);


        getMusicData();
        setListener();

    }

    @SuppressLint("CheckResult")
    private void getMusicData() {

        MusicListReq listReq = new MusicListReq();
        listReq.isHot = 1;
        listReq.page = 1;
        App.getDaggerAppComponent().getHttpHelper()
                .getMusicList(listReq).compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<List<MusicListResp>>>(getActivity()) {
                                   @Override
                                   public void onNext(Optional<List<MusicListResp>> listOptional) {
                                       List<MusicListResp> resps = listOptional.get();
                                       resps.add(0, new MusicListResp());
                                       resps.add(1, new MusicListResp());
                                       mAdapter.setNewData(resps);
                                   }
                               }
                );
    }

    private void setListener() {

        sbVoice1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (editer != null) {
                    editer.setVideoVolume((float) (progress * 0.1));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbVoice2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (editer != null) {
                    editer.setBGMVolume((float) (progress * 0.1));
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }


    @OnClick({R.id.iv_cancel, R.id.iv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_cancel:
                getFragmentManager().beginTransaction().hide(this).commit();
                break;
            case R.id.iv_ok:
                userMusic();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MusicListResp resp = (MusicListResp) adapter.getItem(position);
        if (position == 0) {
            //选择音乐库时候， 不显示mark，所以传一个-1
            mAdapter.setmCurrentPosition(-1);
            RecordMusicActivity.launch(getActivity());
        } else if (position == 1) {
            mAdapter.setmCurrentPosition(1);
            isEmptyMusci = true;
            mFileName = null;
            BGMPath = null;
            stopMeidaplayer();
        } else {
            mAdapter.setmCurrentPosition(position);
            isEmptyMusci = false;
            mFileName = resp.musicId;
            BGMPath = resp.filePath;
            playSelectedMusic();

        }
    }


    /**
     * 点击使用音乐时候触发该方法，这个方法主要做两件事情，
     * 1， 拿选中的文件名 与本地匹配，  是否存在音乐文件
     * 2， 下载远程的音乐文件
     */
    private void userMusic() {

        //选中背景音乐为空的时候
        if (isEmptyMusci) {
            if (editer != null) {
                editer.setBGM(null);
            }
            getFragmentManager().beginTransaction().hide(MusicFragment.this).commit();
            return;
        }

        if (StringUtils.isEmpty(mFileName) || StringUtils.isEmpty(BGMPath)) {
            return;
        }
        String filePath = fileIsExistes(mFileName);

        if (filePath != null) {
            editer.setBGM(filePath);
            float volume = sbVoice2.getProgress() * 0.1f;
            editer.setBGMVolume(volume);
            getFragmentManager().beginTransaction().hide(MusicFragment.this).commit();
        } else {
            String musicPath = getCustomMusicOutputPath(mFileName);
            if (musicPath != null && BGMPath != null) {
                downloadMusicFile(musicPath);
            }

        }
    }

    private void stopMeidaplayer() {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }


    /**
     * 真正开始下载音乐文件
     */
    private void downloadMusicFile(String musicPath) {


        FileDownloader.setup(getActivity());
        FileDownloader.getImpl()
                .create(BGMPath)
                .setPath(musicPath)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {

                        if (!getActivity().isDestroyed()) {
                            editer.setBGM(musicPath);
                            float volume = sbVoice2.getProgress() * 0.1f;
                            editer.setBGMVolume(volume);
                            getFragmentManager().beginTransaction().hide(MusicFragment.this).commit();
                            ((TCVideoEditerActivity) getActivity()).resumePlay();
                        }


                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {


                        ToastUtils.showShort("下载错误");

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                }).start();

    }


    /**
     * @param mFileName 选中的音乐id,
     * @return 返回 要下载的文件路径
     */
    private String getCustomMusicOutputPath(String mFileName) {
        File outputFolder = new File(Constants.PATH_SDCARD);
        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }
        String tempOutputPath = Constants.PATH_MUSIC + File.separator + mFileName + ".mp3";
        File file = new File(tempOutputPath);
        if (file.exists()) {
            file.delete();
        }
        return tempOutputPath;
    }


    /**
     * @param mFileName 选中的音乐id,
     * @return 加入本地有该音乐文件返回 路径， 本地没有就返回null
     */
    private String fileIsExistes(String mFileName) {
        File file = new File(Constants.PATH_MUSIC + File.separator + mFileName + ".mp3");
        if (file.exists()) {
            return file.getAbsoluteFile().getAbsolutePath();
        } else {
            return null;
        }

    }

    /**
     * 播放选中的音乐
     */
    private void playSelectedMusic() {

        //停止播放录制的视频， 不然会与播放的音乐冲突
        ((TCVideoEditerActivity) getActivity()).stopPlay();
        if (!StringUtils.isEmpty(BGMPath)) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                mediaPlayer.reset();
                String localMusicPath = fileIsExistes(mFileName);
                if (localMusicPath != null) {
                    mediaPlayer.setDataSource(localMusicPath);
                } else {
                    mediaPlayer.setDataSource(BGMPath);
                }
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(mp -> {
                    mediaPlayer.start();
                });


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showShort("播放失败，没有该音乐文件");
        }

    }

    /**
     * 释放mediaplayer
     */
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    /**
     * @param filePath 音乐库选中的音乐，本地路径
     */
    @Subscribe
    public void OnSelectedBGM(String filePath) {
        if (filePath != null && editer != null) {
            editer.setBGM(filePath);
            getFragmentManager().beginTransaction().hide(this).commit();
            ((TCVideoEditerActivity) getActivity()).resumePlay();
        }

    }
}
