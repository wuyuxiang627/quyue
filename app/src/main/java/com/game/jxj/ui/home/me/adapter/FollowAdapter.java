package com.game.jxj.ui.home.me.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.game.jxj.R;
import com.game.jxj.model.entity.FollowListResp;
import com.game.jxj.model.entity.MeBean;

import java.util.List;

/**
 * @author jxj
 * @date 2018/6/8
 */
public class FollowAdapter extends BaseQuickAdapter<FollowListResp, BaseViewHolder> {


//    public FollowAdapter() {
//        super(R.layout.item_follow, FollowListResp.getData());
//    }

    public FollowAdapter(int layoutResId, @Nullable List<FollowListResp> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, FollowListResp item) {
        helper.setText(R.id.tv_item_follow_name,"滴  打");
//        helper.setImageResource(R.id.iv_icon, item.iconRes)
//                .setText(R.id.tv_name, item.functionName);

    }
}
