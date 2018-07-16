package com.game.jxj.ui.home.business.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.game.jxj.GlideApp;
import com.game.jxj.R;
import com.game.jxj.model.entity.VideoResp;

/**
 * @author jxj
 * @date 2018/7/4
 */
public class BusinessActivitysDetailsAdapter extends BaseQuickAdapter<VideoResp, BaseViewHolder> {
    public BusinessActivitysDetailsAdapter() {
        super(R.layout.item_business_activitys_details);
    }


    @Override
    protected void convert(BaseViewHolder helper, VideoResp item) {
        ImageView ivCover = helper.getView(R.id.iv_cover);
        GlideApp.with(mContext).load(item.coverPath).into(ivCover);
        helper.getView(R.id.tv_shili).setVisibility(helper.getAdapterPosition() == 1 ? View.VISIBLE : View.GONE);

    }
}
