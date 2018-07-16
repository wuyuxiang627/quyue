package com.game.jxj.ui.home.me.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.game.jxj.model.entity.FollowListReq;
import com.game.jxj.model.entity.FollowListResp;
import com.game.jxj.model.entity.LoginResp;
import com.game.jxj.model.entity.MusicListReq;
import com.game.jxj.model.entity.MusicListResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.game.jxj.ui.home.me.adapter.FollowAdapter;
import com.game.jxj.utils.RxUtil;
import com.gm.utils.ActivityUtil;
import com.gm.utils.CacheUtils;
import com.gm.views.AbTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FollowActivity extends SimpleActivity implements SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnItemClickListener  {


    @BindView(R.id.rlv_follow_list)
    RecyclerView rlvFollowList;
    @BindView(R.id.title_bar)
    AbTitleBar titleBar;
    @BindView(R.id.srl_follow)
    SwipeRefreshLayout srlFollow;

    //当前页索引
    private int page = 1;
    private int type;
    FollowAdapter followAdapter;
    List<FollowListResp> resps = new ArrayList<>();

    public static void launch(Context context) {
        ActivityUtil.startActivity(context, FollowActivity.class);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_follow;
    }

    @Override
    protected void initEventAndData() {
        titleBar.setTitleText(R.string.my_follow);
        titleBar.setBackgroundColor(Color.parseColor("#010B1E"));
        rlvFollowList.setLayoutManager(new LinearLayoutManager(mContext));
        followAdapter = new FollowAdapter(R.layout.item_follow,resps);
        rlvFollowList.setAdapter(followAdapter);
        getFollowList();

    }


    @SuppressLint("CheckResult")
    private void getFollowList() {
        FollowListReq listReq = new FollowListReq();
        listReq.length = 20;
        listReq.page = page;
        listReq.type =0;
        LoginResp loginResp = (LoginResp) CacheUtils.getInstance().getSerializable("loginResp");
        listReq.userId = loginResp.userId;
        getHttpHelper().getFollowList(listReq)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<List<FollowListResp>>>(mContext) {
                                   @Override
                                   public void onNext(Optional<List<FollowListResp>> listOptional) {
                                       resps = listOptional.get();
                                       if (page == 1) {
                                           //1刷新
                                           followAdapter.setNewData(resps);
                                           srlFollow.setRefreshing(false);
                                       } else {
                                           //加载更多
                                           followAdapter.addData(resps);
                                           followAdapter.loadMoreComplete();
                                           if (resps.size() == 0) {
                                               followAdapter.loadMoreEnd();
                                           } else {
                                               followAdapter.loadMoreEnd(false);
                                           }
                                       }
                                   }
                               }
                );
    }

    @Override
    public void onRefresh() {
        page = 1;
        getFollowList();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getFollowList();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
