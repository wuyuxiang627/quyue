package com.game.jxj.ui.home.play.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.game.jxj.R;
import com.game.jxj.base.SimpleFragment;
import com.game.jxj.config.preference.Constants;
import com.game.jxj.model.entity.FollowListReq;
import com.game.jxj.model.entity.FollowListResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.entity.UserInfo;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.game.jxj.ui.home.play.adapter.FollowerListAdapter;
import com.game.jxj.utils.RxUtil;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author jxj
 * @date 2018/7/4
 */
public class FollowerListFragment extends SimpleFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.OnItemClickListener,
        BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.rcy_content)
    RecyclerView rcyContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private FollowerListAdapter adapter;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_follwer;
    }

    @Override
    protected void initEventAndData() {
        swipeRefresh.setOnRefreshListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        rcyContent.setLayoutManager(gridLayoutManager);
        rcyContent.setHasFixedSize(true);

        adapter = new FollowerListAdapter();
        adapter.setOnItemClickListener(this);
        adapter.setOnLoadMoreListener(this, rcyContent);
        rcyContent.setAdapter(adapter);
        adapter.setNewData(Arrays.asList("1", "2"));

        swipeRefresh.post(() -> {
            swipeRefresh.setRefreshing(true);
            onRefresh();
        });


    }


    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onLoadMoreRequested() {

    }


    private void getData() {


        FollowListReq req = new FollowListReq();
        req.page = page;
        req.userId = UserInfo.getInstance().userId;
        req.length = Constants.DATA_LIST_SIZE;
        req.type = 0;
        getHttpHelper().getFollowList(req)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<List<FollowListResp>>>(mContext) {
                    @Override
                    public void onNext(Optional<List<FollowListResp>> listOptional) {

                    }
                });


    }
}


