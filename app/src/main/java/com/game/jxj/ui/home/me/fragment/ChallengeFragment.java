package com.game.jxj.ui.home.me.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.game.jxj.R;
import com.game.jxj.base.SimpleFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author jxj
 * @date 2018/6/12
 */
public class ChallengeFragment extends SimpleFragment {


    Unbinder unbinder;

    public static ChallengeFragment getInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, type);
        ChallengeFragment fragment = new ChallengeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    //类型 进行中
    public static final int TYPE_UNDERWAY = 0;
    //类型 已完成
    public static final int TYPE_FINISHED = 1;

    public static final int TYPE_NO_pay = 2;

    //传值的键
    private static final String KEY_TYPE = "type";
    //当前 类型
    private int type;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_challenge;
    }

    @Override
    protected void initEventAndData() {
        type = getArguments().getInt(KEY_TYPE, 0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvContent.setLayoutManager(layoutManager);
        List<String> data = Arrays.asList("5","5","5","5","5","5","5","5","5","5","5","5","5");
        rvContent.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_challenge, data) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {

            }
        });

    }


}
