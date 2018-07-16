package com.game.jxj.ui.home.business.fragment;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.game.jxj.R;
import com.game.jxj.base.SimpleFragment;
import com.game.jxj.model.entity.ActivityInfoResp;
import com.game.jxj.model.entity.BannerListResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.game.jxj.ui.home.business.activity.BusinessActivityDetailsActivity;
import com.game.jxj.ui.home.business.adapter.BusinessActivitysAdapter;
import com.game.jxj.ui.home.business.view.BusinessActivitysHeader;
import com.game.jxj.utils.RxUtil;
import com.gm.utils.StringUtils;

import java.util.List;

import butterknife.BindView;

public class BusinessActivitysFragment extends SimpleFragment implements
        BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;


    public static BusinessActivitysFragment getInstance() {

        return new BusinessActivitysFragment();
    }


    @BindView(R.id.rcy_content)
    RecyclerView rcyContent;
    BusinessActivitysHeader header;

    private BusinessActivitysAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_business_activitys;
    }

    @Override
    protected void initEventAndData() {
        header = new BusinessActivitysHeader(mContext);
        adapter = new BusinessActivitysAdapter();
        adapter.setOnItemClickListener(this);
        adapter.addHeaderView(header);
        rcyContent.setLayoutManager(new LinearLayoutManager(mContext));
        rcyContent.setHasFixedSize(true);
        rcyContent.setAdapter(adapter);
        swipeRefresh.setOnRefreshListener(this);

        swipeRefresh.post(() -> {
            swipeRefresh.setRefreshing(true);
            onRefresh();
        });


    }


    @SuppressLint("CheckResult")
    private void getData() {

        getHttpHelper().getBannerList()
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<List<BannerListResp>>>(mContext) {
                    @Override
                    public void onNext(Optional<List<BannerListResp>> listOptional) {
                        List<BannerListResp> listResps = listOptional.get();
                        header.setData(listResps);
                        swipeRefresh.setRefreshing(false);
                    }
                });


        getHttpHelper().getActivityList()
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<List<ActivityInfoResp>>>(mContext) {
                    @Override
                    public void onNext(Optional<List<ActivityInfoResp>> listOptional) {
                        List<ActivityInfoResp> list = listOptional.get();
                        adapter.setNewData(list);
                        swipeRefresh.setRefreshing(false);
                    }
                });

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String activityId = ((ActivityInfoResp) adapter.getItem(position)).activityId;
        if (StringUtils.isEmpty(activityId)) {
            return;
        }
        BusinessActivityDetailsActivity.launch(mContext, activityId);
    }


    @Override
    public void onRefresh() {
        getData();
    }
}
