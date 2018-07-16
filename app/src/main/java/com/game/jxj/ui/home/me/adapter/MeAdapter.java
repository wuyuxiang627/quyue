package com.game.jxj.ui.home.me.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.game.jxj.R;
import com.game.jxj.model.entity.MeBean;

/**
 * @author jxj
 * @date 2018/6/8
 */
public class MeAdapter extends BaseQuickAdapter<MeBean, BaseViewHolder> {


    public MeAdapter() {
        super(R.layout.item_me_function, MeBean.getData());
    }

    @Override
    protected void convert(BaseViewHolder helper, MeBean item) {

        helper.setImageResource(R.id.iv_icon, item.iconRes)
                .setText(R.id.tv_name, item.functionName);

    }
}
