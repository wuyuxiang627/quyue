package com.game.jxj.ui.home.record.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.game.jxj.R;
import com.game.jxj.base.SimpleFragment;
import com.game.jxj.config.preference.Constants;
import com.game.jxj.model.entity.MusicListReq;
import com.game.jxj.model.entity.MusicListResp;
import com.game.jxj.model.entity.MyMusicListReq;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.game.jxj.ui.home.record.adapter.MusicAdapter;
import com.game.jxj.utils.RxUtil;

import java.util.List;

import butterknife.BindView;

/**
 * @author jxj
 * @date 2018/6/26
 */
public class MusicListFragment extends SimpleFragment implements SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnItemClickListener {


    public static MusicListFragment getInstance(int type) {
        MusicListFragment fragment = new MusicListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.rcy_content)
    RecyclerView rcyContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;


    public static int TYPE_HOT = 1;
    public static int TYPE_COLLECT = 0;
    //当前页索引
    private int page = 1;
    private MusicAdapter adapter;
    private int type;

    private OnPlayMusicListener mOnPlayMusicListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnPlayMusicListener = (OnPlayMusicListener) context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music_list;
    }

    @Override
    protected void initEventAndData() {

        type = getArguments().getInt("type", 1);
        rcyContent.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MusicAdapter();
        adapter.setOnLoadMoreListener(this, rcyContent);
        adapter.setOnItemClickListener(this);
        swipeRefresh.setOnRefreshListener(this);
        rcyContent.setAdapter(adapter);

        View emptyView = LayoutInflater.from(mContext).inflate(R.layout.view_empty_music, null);
        adapter.setEmptyView(emptyView);

        if (type == TYPE_COLLECT) {
            getCollectMusic();
        } else {
            getMusicList();
        }

    }


    @Override
    public void onRefresh() {
        page = 1;
        if (type == TYPE_COLLECT) {
            getCollectMusic();
        } else {
            getMusicList();
        }


    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        if (type == TYPE_COLLECT) {
            getCollectMusic();
        } else {
            getMusicList();
        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MusicListResp resp = (MusicListResp) adapter.getData().get(position);
        if (resp != null && mOnPlayMusicListener != null) {
            mOnPlayMusicListener.playMusic(resp);
        }
    }


    @SuppressLint("CheckResult")
    private void getMusicList() {
        MusicListReq listReq = new MusicListReq();
        listReq.isHot = type;
        listReq.page = page;
        getHttpHelper().getMusicList(listReq)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<List<MusicListResp>>>(mContext) {
                                   @Override
                                   public void onNext(Optional<List<MusicListResp>> listOptional) {
                                       List<MusicListResp> resps = listOptional.get();
                                       if (page == 1) {
                                           //1刷新
                                           adapter.setNewData(resps);
                                           swipeRefresh.setRefreshing(false);
                                       } else {
                                           //加载更多
                                           adapter.addData(resps);
                                           adapter.loadMoreComplete();
                                           if (resps.size() == 0) {
                                               adapter.loadMoreEnd();
                                           } else {
                                               adapter.loadMoreEnd(false);
                                           }

                                       }
                                   }
                               }
                );
    }

    /**
     * 我收藏的音乐列表数据
     */
    @SuppressLint("CheckResult")
    private void getCollectMusic() {
        MyMusicListReq listReq = new MyMusicListReq();
        listReq.page = page;
        listReq.length = Constants.DATA_LIST_SIZE;
        getHttpHelper().getMyMusicList(listReq)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<List<MusicListResp>>>(mContext) {
                                   @Override
                                   public void onNext(Optional<List<MusicListResp>> listOptional) {
                                       List<MusicListResp> resps = listOptional.get();
                                       if (page == 1) {
                                           //1刷新
                                           adapter.setNewData(resps);
                                           swipeRefresh.setRefreshing(false);
                                       } else {
                                           //加载更多
                                           adapter.addData(resps);
                                           adapter.loadMoreComplete();
                                           if (resps.size() == 0) {
                                               adapter.loadMoreEnd();
                                           } else {
                                               adapter.loadMoreEnd(false);
                                           }

                                       }
                                   }
                               }
                );
    }


    public interface OnPlayMusicListener {

        void playMusic(MusicListResp resp);
    }
}
