package com.game.jxj.ui.home.business.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.game.jxj.GlideApp;
import com.game.jxj.R;
import com.game.jxj.model.entity.ActivityInfoResp;
import com.gm.utils.StringUtils;
import com.gm.utils.TimeUtils;

import java.util.Calendar;

/**
 * @author jxj
 * @date 2018/7/3
 */
public class BusinessActivitysAdapter extends BaseQuickAdapter<ActivityInfoResp, BaseViewHolder> {

    public BusinessActivitysAdapter() {
        super(R.layout.item_business_activitys);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActivityInfoResp item) {


        helper.setText(R.id.tv_name, item.enterpriseName)
                .setText(R.id.tv_title, item.content)
                .setText(R.id.tv_video_num, item.videoCount + "")
                .setText(R.id.tv_price1, "¥" + item.money / 100 + "")
                .setText(R.id.tv_price2, item.partitionMoney / 100 + "");

        ImageView ivCover = helper.getView(R.id.iv_cover);
        GlideApp.with(mContext).load(item.LOGO).into(ivCover);


        String currentDateString = TimeUtils.millis2String(Calendar.getInstance().getTimeInMillis());
        String spanDay = TimeUtils.getFitTimeSpan(currentDateString, item.endDate, 1);
        if (!StringUtils.isEmpty(spanDay)) {
            String[] str = spanDay.split("天");
            helper.setText(R.id.tv_span_day, str[0]);
        } else {
            helper.setText(R.id.tv_span_day, "0");
        }


    }
}
