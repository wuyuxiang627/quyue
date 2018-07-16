package com.game.jxj.ui.home.record.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.game.jxj.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jxj
 * @date 2018/6/19
 */
public class RecordFilterAdapter extends BaseQuickAdapter<RecordFilterAdapter.RecordFilterBean, BaseViewHolder> {


    private int mCurrentPosition;

    public RecordFilterAdapter(@Nullable List<RecordFilterBean> data) {
        super(R.layout.item_filter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordFilterBean item) {
        helper.setImageResource(R.id.civ_avatar, item.img);
        if (mCurrentPosition == helper.getAdapterPosition()) {
            helper.getView(R.id.filter_image_tint).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.filter_image_tint).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_name, item.name);

    }

    public void setSelected(int position) {
        this.mCurrentPosition = position;
        notifyDataSetChanged();
    }


    public static class RecordFilterBean {
        public int img;
        public int bitmapIndex;
        public String name;

        public RecordFilterBean(int img, int bitmapIndex, String name) {
            this.img = img;
            this.bitmapIndex = bitmapIndex;
            this.name = name;
        }

        public static List<RecordFilterBean> getData() {

            List<RecordFilterBean> data = new ArrayList<>();
            data.add(new RecordFilterBean(R.drawable.orginal, 0, "正常"));
            data.add(new RecordFilterBean(R.drawable.langman, 1, "浪漫"));
            data.add(new RecordFilterBean(R.drawable.qingxin, 2, "清新"));
            data.add(new RecordFilterBean(R.drawable.qingxin, 3, "唯美"));
            data.add(new RecordFilterBean(R.drawable.fennen, 4, "粉嫩"));
            data.add(new RecordFilterBean(R.drawable.huaijiu, 5, "怀旧"));
            data.add(new RecordFilterBean(R.drawable.landiao, 6, "蓝调"));
            data.add(new RecordFilterBean(R.drawable.qingliang, 7, "清凉"));
            data.add(new RecordFilterBean(R.drawable.rixi, 8, "日系"));
            return data;
        }
    }


}
