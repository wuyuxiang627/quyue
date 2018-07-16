package com.game.jxj.ui.home.me.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.game.jxj.R;
import com.game.jxj.model.entity.FollowListResp;
import com.game.jxj.model.entity.VideoResp;

import java.util.List;

/**
 * @author jxj
 * @date 2018/6/8
 */
public class MyVideosAdapter extends BaseQuickAdapter<VideoResp, BaseViewHolder> {


    public MyVideosAdapter(int layoutResId, @Nullable List<VideoResp> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, VideoResp item) {
        helper.setText(R.id.tv_item_follow_name,"滴  打");

    }
}
