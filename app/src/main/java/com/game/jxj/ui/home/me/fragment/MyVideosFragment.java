package com.game.jxj.ui.home.me.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.game.jxj.R;
import com.game.jxj.base.SimpleFragment;
import com.game.jxj.model.entity.LoginResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.entity.UserVideoInfoReq;
import com.game.jxj.model.entity.UserVideoInfoResp;
import com.game.jxj.model.entity.VideoResp;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.game.jxj.ui.home.me.adapter.MyVideosAdapter;
import com.game.jxj.utils.ItemDividing;
import com.game.jxj.utils.RxUtil;
import com.gm.utils.CacheUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author jxj
 * @date 2018/6/11
 */
public class MyVideosFragment extends SimpleFragment implements SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener {


    Unbinder unbinder;

    public static MyVideosFragment getInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, type);
        MyVideosFragment fragment = new MyVideosFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.srl_my_videos)
    SwipeRefreshLayout srlMyVideos;

    //类型 作品
    public static final int TYPE_PRODUCTION = 0;
    //类型 喜欢
    public static final int TYPE_LIKE = 1;
    //类型 草稿
    public static final int TYPE_DRAFT = 2;
    //传值的键
    private static final String KEY_TYPE = "type";
    //当前 类型
    private int type;

    //页数
    private int page = 1;

    //条数
    private int length = 20;

    List<VideoResp> videoResps = new ArrayList<>();

    MyVideosAdapter myVideosAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_videos;
    }

    @Override
    protected void initEventAndData() {
        type = getArguments().getInt(KEY_TYPE, 0);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvContent.setLayoutManager(layoutManager);
        List<String> data = Arrays.asList("1", "2", "3", "4", "5", "6");
        rvContent.addItemDecoration(new ItemDividing());
        myVideosAdapter = new MyVideosAdapter(R.layout.item_my_videos, videoResps);
        rvContent.setAdapter(myVideosAdapter);
        if(type != TYPE_DRAFT  ){
            getVideos(type);
        }


    }

    @SuppressLint("CheckResult")
    public void getVideos(int type) {
        LoginResp loginResp = (LoginResp) CacheUtils.getInstance().getSerializable("loginResp", null);

        UserVideoInfoReq userVideoInfoReq = new UserVideoInfoReq();
        if (type == TYPE_PRODUCTION) {
            userVideoInfoReq.favorStatus = TYPE_PRODUCTION;

        } else if (type == TYPE_LIKE) {
            userVideoInfoReq.favorStatus = TYPE_LIKE;
        }
        userVideoInfoReq.page = page;
        userVideoInfoReq.length = length;
        userVideoInfoReq.userId = loginResp.userId;

        getHttpHelper().getUserVideoInfo(userVideoInfoReq)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<UserVideoInfoResp>>(mContext) {
                    @Override
                    public void onNext(Optional<UserVideoInfoResp> listOptional) {
                        UserVideoInfoResp  userVideoInfoResp = listOptional.get();
                        videoResps = userVideoInfoResp.list;
                        if (page == 1) {
                            //1刷新
                            myVideosAdapter.setNewData(videoResps);
                            srlMyVideos.setRefreshing(false);
                        } else {
                            //加载更多
                            myVideosAdapter.addData(videoResps);
                            myVideosAdapter.loadMoreComplete();
                            if (videoResps.size() == 0) {
                                myVideosAdapter.loadMoreEnd();
                            } else {
                                myVideosAdapter.loadMoreEnd(false);
                            }
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        page = 1;
        getVideos(type);

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getVideos(type);
    }
}
