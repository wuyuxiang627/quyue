package com.game.jxj.model.entity;

import android.view.View;

import com.tencent.rtmp.TXVodPlayer;


/**
 * @author jxj
 * @date 2018/6/13
 */
public class PlayerInfo {

    public TXVodPlayer txVodPlayer;
    public String playURL;
    public boolean isBegin;
    public View playerView;
    public int  pos;
}
