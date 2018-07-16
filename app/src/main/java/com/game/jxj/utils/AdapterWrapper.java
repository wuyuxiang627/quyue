package com.game.jxj.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.game.jxj.model.entity.HttpResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author jxj
 * @date 2018/7/3
 */
public class AdapterWrapper<T> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private int page = 1;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private BaseQuickAdapter adapter;
    private Context mContext;
    private Flowable<HttpResp<List<T>>> flowable;

    public AdapterWrapper(RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout, BaseQuickAdapter adapter) {
        this.mRecyclerView = recyclerView;
        this.mSwipeRefreshLayout = swipeRefreshLayout;
        this.adapter = adapter;
        mContext = mRecyclerView.getContext();
        mRecyclerView.setHasFixedSize(true);
        if (mRecyclerView.getLayoutManager() == null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        }
        mSwipeRefreshLayout.setOnRefreshListener(this);
        adapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);


    }

    public void setRequest(Flowable<HttpResp<List<T>>> flowable) {
        this.flowable = flowable;
        getData();
    }


    @Override
    public void onRefresh() {
        page = 1;
        getData();
    }

    @SuppressLint("CheckResult")
    private void getData() {

        if (flowable == null) {
            return;
        }
        flowable.compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<List<T>>>(mContext) {
                    @Override
                    public void onNext(Optional<List<T>> listOptional) {
                        List<T> list = listOptional.get();

                        if (page == 1) {
                            adapter.setNewData(list);
                            mSwipeRefreshLayout.setRefreshing(false);
                        } else {
                            adapter.addData(list);
                            adapter.loadMoreComplete();
                            if (list.size() == 0) {
                                adapter.loadMoreEnd();
                            }
                        }
                    }
                });

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getData();
    }
}
