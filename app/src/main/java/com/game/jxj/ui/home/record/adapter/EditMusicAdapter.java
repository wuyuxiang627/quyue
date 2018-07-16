package com.game.jxj.ui.home.record.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.game.jxj.GlideApp;
import com.game.jxj.R;
import com.game.jxj.model.entity.MusicListResp;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author jxj
 * @date 2018/6/21
 */
public class EditMusicAdapter extends BaseQuickAdapter<MusicListResp, BaseViewHolder> {
    public EditMusicAdapter() {
        super(R.layout.item_editer_music);
    }

    //默认都不选中
    private int mCurrentPosition = -1;


    @Override
    protected void convert(BaseViewHolder helper, MusicListResp item) {

        try {
            CircleImageView civCover = helper.getView(R.id.civ_cover);

            int position = helper.getAdapterPosition();
            if (position == 0) {
                helper.setImageResource(R.id.civ_cover, R.drawable.yinyueku);
                helper.setText(R.id.tv_name, "音乐库");
            } else if (position == 1) {
                helper.setImageResource(R.id.civ_cover, R.drawable.wuyinyue);
                helper.setText(R.id.tv_name, "无音乐");
            } else {
                GlideApp.with(mContext).load(item.coverPath).into(civCover);
                helper.setText(R.id.tv_name, item.description);
            }

            if (position == mCurrentPosition) {
                helper.getView(R.id.iv_mark).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.iv_mark).setVisibility(View.GONE);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setmCurrentPosition(int position) {
        mCurrentPosition = position;
        notifyDataSetChanged();
    }

}
