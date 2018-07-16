package com.game.jxj.ui.home.me.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.game.jxj.R;
import com.game.jxj.base.SimpleFragment;
import com.game.jxj.model.entity.ArtUserReq;
import com.game.jxj.model.entity.LoginResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.http.subscriber.SimpleCommonSubscriber;
import com.game.jxj.ui.home.me.activity.AuthenticationActivity;
import com.game.jxj.ui.home.me.activity.ChallengeActivity;
import com.game.jxj.ui.home.me.activity.ChallengeCreatActivity;
import com.game.jxj.ui.home.me.activity.CopperationActivity;
import com.game.jxj.ui.home.me.activity.FeedbackActivity;
import com.game.jxj.ui.home.me.activity.MyVideosActivity;
import com.game.jxj.ui.home.me.adapter.MeAdapter;
import com.game.jxj.utils.MessageEvent;
import com.game.jxj.utils.RxUtil;
import com.game.jxj.view.HeaderViewMe;
import com.gm.utils.CacheUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * Created by Administrator on 2018\5\23 0023.
 */

public class MeFragment extends SimpleFragment implements BaseQuickAdapter.OnItemClickListener {


    public static MeFragment getInstance() {
        return new MeFragment();
    }


    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    private MeAdapter adapter;

    HeaderViewMe headerViewMe;
    ArtUserReq ArtUserReq;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initEventAndData() {

        //注册成为订阅者
        EventBus.getDefault().register(this);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvContent.setLayoutManager(manager);
        rvContent.setHasFixedSize(true);
        OverScrollDecoratorHelper.setUpOverScroll(rvContent, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        getUserInfo();

        adapter = new MeAdapter();
        adapter.setOnItemClickListener(this);

        headerViewMe = new HeaderViewMe(mContext);
        View footerView = LayoutInflater.from(mContext).inflate(R.layout.footer_me_empty, null);

        adapter.addHeaderView(headerViewMe);
        adapter.addFooterView(footerView);
        rvContent.setAdapter(adapter);


    }


    //订阅方法，当接收到事件的时候，会调用该方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent){

        switch (messageEvent.type)
        {
            case 1:
                getUserInfo();
//                ToastUtils.showLong("回来了");
                break;
        }
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }






    @SuppressLint("CheckResult")
    public void getUserInfo(){
        LoginResp loginResp = (LoginResp) CacheUtils.getInstance().getSerializable("loginResp",null);
        getHttpHelper().getArtUser(loginResp.userId)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult2())
                .subscribeWith(new SimpleCommonSubscriber<Optional<ArtUserReq>>(mContext) {
                    @Override
                    public void onNext(Optional<ArtUserReq> stringOptional) {
                        ArtUserReq = stringOptional.get();
                        headerViewMe.setData(ArtUserReq);
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0:
                //视频
                if(ArtUserReq != null){
                    MyVideosActivity.launch(mContext,ArtUserReq.myVideoNum,ArtUserReq.favorVideoNum );
                }
//                //钱包
//                WalletActivity.launch(mContext);
                break;
            case 1:
                //挑战
                ChallengeActivity.launch(mContext);
                break;
            case 2:
                CopperationActivity.launch(mContext);
                break;
            case 3: //意见反馈
                FeedbackActivity.launch(mContext);
                break;
            case 4:
                //实名认证
                AuthenticationActivity.launch(mContext);
                break;
        }

    }
}
