package com.game.jxj.ui.home.business.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.game.jxj.R;
import com.game.jxj.base.SimpleActivity;
import com.game.jxj.config.preference.Constants;
import com.game.jxj.model.entity.ActivityInfoResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.entity.VideoListReq;
import com.game.jxj.model.entity.VideoListResp;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.game.jxj.ui.home.business.adapter.BusinessActivitysDetailsAdapter;
import com.game.jxj.ui.home.business.view.BusinessActivitysDetailsHeader;
import com.game.jxj.utils.ItemDividing;
import com.game.jxj.utils.RxUtil;
import com.gm.utils.ActivityUtil;
import com.gm.views.AbTitleBar;

import butterknife.BindView;

public class BusinessActivityDetailsActivity extends SimpleActivity implements
        BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    public static void launch(Context context, String activityId) {
        Bundle bundle = new Bundle();
        bundle.putString("activityId", activityId);
        ActivityUtil.startActivity(context, BusinessActivityDetailsActivity.class, bundle);
    }

    @BindView(R.id.title_bar)
    AbTitleBar titleBar;
    @BindView(R.id.rcy_content)
    RecyclerView rcyContent;


    private BusinessActivitysDetailsAdapter adapter;
    private BusinessActivitysDetailsHeader header;

    private int page = 1;
    private String activityId;


    @Override
    protected int getLayout() {
        return R.layout.activity_business_details;
    }

    @Override
    protected void initEventAndData() {
        titleBar.setTitleText("活动详情");
        activityId = getIntent().getStringExtra("activityId");
        activityId = "2c98c02264277560016435b31b89002f";

        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        rcyContent.setLayoutManager(manager);
        rcyContent.addItemDecoration(new ItemDividing());

        adapter = new BusinessActivitysDetailsAdapter();
        adapter.setOnLoadMoreListener(this, rcyContent);
        adapter.setOnItemClickListener(this);

        header = new BusinessActivitysDetailsHeader(mContext);
        adapter.addHeaderView(header);
        rcyContent.setAdapter(adapter);

        getVideoListData();
        getActivitysInfo();

    }


    private void getActivitysInfo() {
        getHttpHelper().getActivityInfo(activityId)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<ActivityInfoResp>>(mContext) {
                    @Override
                    public void onNext(Optional<ActivityInfoResp> info) {
                        if (!info.isEmpty()) {
                            header.setData(info.get());
                        }
                    }
                });
    }

    private void getVideoListData() {
        VideoListReq listReq = new VideoListReq();
        listReq.activityId = activityId;
        listReq.page = page;
        listReq.length = Constants.DATA_LIST_SIZE;
        getHttpHelper().getVideoList(listReq)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<VideoListResp>>(mContext) {

                    @Override
                    public void onNext(Optional<VideoListResp> respOptional) {
                        VideoListResp videoListResp = respOptional.get();
                        if (page == 1) {
                            adapter.setNewData(videoListResp.list);
                        } else {
                            adapter.addData(videoListResp.list);
                            adapter.loadMoreComplete();
                            if (videoListResp.list.size() == 0) {
                                adapter.loadMoreEnd();
                            }
                        }
                    }
                });
    }


    @Override
    public void onLoadMoreRequested() {
        page++;
        getVideoListData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


    }
}
