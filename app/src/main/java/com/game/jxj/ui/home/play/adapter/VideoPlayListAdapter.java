package com.game.jxj.ui.home.play.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.game.jxj.R;
import com.game.jxj.model.entity.PlayerInfo;
import com.game.jxj.model.entity.TCVideoInfo;
import com.game.jxj.utils.SharePop;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jxj
 * @date 2018/6/11
 */
public class VideoPlayListAdapter extends PagerAdapter {

    private ArrayList<PlayerInfo> playerInfoList = new ArrayList<>();
    private Context mContext;
    private List<TCVideoInfo> mTCLiveInfoList;
    private ITXVodPlayListener playListener;

    public VideoPlayListAdapter(Context mContext, List<TCVideoInfo> mTCLiveInfoList, ITXVodPlayListener playListener) {
        this.playListener = playListener;
        this.mTCLiveInfoList = mTCLiveInfoList;
        this.mContext = mContext;


    }

    protected PlayerInfo instantiatePlayerInfo(int position) {


        PlayerInfo playerInfo = new PlayerInfo();
        TXVodPlayer vodPlayer = new TXVodPlayer(mContext);
        vodPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        vodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        vodPlayer.setVodListener(playListener);
        TXVodPlayConfig config = new TXVodPlayConfig();
        config.setCacheFolderPath(mContext.getCacheDir()+ "/txcache");
        config.setMaxCacheItems(5);
        vodPlayer.setConfig(config);
        vodPlayer.setAutoPlay(false);

        TCVideoInfo tcLiveInfo = mTCLiveInfoList.get(position);
        playerInfo.playURL = TextUtils.isEmpty(tcLiveInfo.hlsPlayUrl) ? tcLiveInfo.playurl : tcLiveInfo.hlsPlayUrl;
        playerInfo.txVodPlayer = vodPlayer;
        playerInfo.pos = position;
        playerInfoList.add(playerInfo);

        return playerInfo;
    }

    protected void destroyPlayerInfo(int position) {
        while (true) {
            PlayerInfo playerInfo = findPlayerInfo(position);
            if (playerInfo == null)
                break;
            playerInfo.txVodPlayer.stopPlay(true);
            playerInfoList.remove(playerInfo);


        }
    }

    public PlayerInfo findPlayerInfo(int position) {
        for (int i = 0; i < playerInfoList.size(); i++) {
            PlayerInfo playerInfo = playerInfoList.get(i);
            if (playerInfo.pos == position) {
                return playerInfo;
            }
        }
        return null;
    }

    public PlayerInfo findPlayerInfo(TXVodPlayer player) {
        for (int i = 0; i < playerInfoList.size(); i++) {
            PlayerInfo playerInfo = playerInfoList.get(i);
            if (playerInfo.txVodPlayer == player) {
                return playerInfo;
            }
        }
        return null;
    }

    public void onDestroy() {
        for (PlayerInfo playerInfo : playerInfoList) {
            playerInfo.txVodPlayer.stopPlay(true);
        }
        playerInfoList.clear();
    }

    @Override
    public int getCount() {
        return mTCLiveInfoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        TCVideoInfo tcLiveInfo = mTCLiveInfoList.get(position);

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_video_play, null);
        view.setId(position);
        // 封面
        ImageView coverImageView = view.findViewById(R.id.iv_cover);
        ImageView iv_share = view.findViewById(R.id.iv_share);
        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(dialog == null){
//                    dialog = new ShareDialog(mContext);
//                }
//                dialog.show();

                new SharePop(mContext);


            }
        });

        // 获取此player
        TXCloudVideoView playView = view.findViewById(R.id.video_view);
        PlayerInfo playerInfo = instantiatePlayerInfo(position);
        playerInfo.playerView = playView;
        playerInfo.txVodPlayer.setPlayerView(playView);
        playerInfo.txVodPlayer.setAutoPlay(false);
        playerInfo.txVodPlayer.startPlay(playerInfo.playURL);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        destroyPlayerInfo(position);
        container.removeView((View) object);
    }
}
