package com.game.jxj.ui.home.record.adapter;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.game.jxj.GlideApp;
import com.game.jxj.R;
import com.game.jxj.base.App;
import com.game.jxj.model.entity.EmptyDateResp;
import com.game.jxj.model.entity.MusicListResp;
import com.game.jxj.model.entity.Optional;
import com.game.jxj.model.http.HttpHelper;
import com.game.jxj.model.http.subscriber.CommonSubscriber;
import com.game.jxj.utils.RxUtil;
import com.gm.utils.LogUtils;
import com.gm.utils.StringUtils;
import com.gm.utils.TimeUtils;
import com.gm.utils.ToastUtils;

/**
 * @author jxj
 * @date 2018/6/21
 */
public class MusicAdapter extends BaseQuickAdapter<MusicListResp, BaseViewHolder> {
    public MusicAdapter() {
        super(R.layout.item_music_selected);
    }

    //未收藏
    private static final int COLLECTION_NO = 0;
    //已收藏
    private static final int COLLECTION_YES = 1;


    @SuppressLint("CheckResult")
    @Override
    protected void convert(BaseViewHolder helper, MusicListResp item) {

        try {
            ImageView ivCover = helper.getView(R.id.iv_cover);
            GlideApp.with(mContext).load(item.coverPath).into(ivCover);
            helper.setText(R.id.tv_title1, item.description)
                    .setText(R.id.tv_title2, item.author);

            helper.setText(R.id.tv_time, TimeUtils.second2MinuteSecond(item.length));
            setCollect(helper, item);
            helper.getView(R.id.iv_collection)
                    .setOnClickListener(v -> {
                        HttpHelper httpHelper = App.getDaggerAppComponent().getHttpHelper();
                        if (item.collectionStatus == COLLECTION_NO) {
                            if (StringUtils.isEmpty(item.musicId)) {
                                ToastUtils.showShort("收藏失败");
                                LogUtils.e("musicId is Null");
                                return;
                            }
                            httpHelper.saveMusicCollection(item.musicId)
                                    .compose(RxUtil.rxSchedulerHelper())
                                    .compose(RxUtil.handleResult2())
                                    .subscribeWith(new CommonSubscriber<Optional<EmptyDateResp>>() {
                                        @Override
                                        public void onNext(Optional<EmptyDateResp> emptyDateRespOptional) {
                                            item.collectionStatus = COLLECTION_YES;
                                            setCollect(helper, item);
                                            ToastUtils.showShort("收藏成功");

                                        }
                                    });


                        } else if (item.collectionStatus == COLLECTION_YES) {
                            if (StringUtils.isEmpty(item.musicId)) {
                                ToastUtils.showShort("取消收藏失败");
                                LogUtils.e("musicId is Null");
                                return;
                            }
                            httpHelper.deleteMusicCollection(item.musicId)
                                    .compose(RxUtil.rxSchedulerHelper())
                                    .compose(RxUtil.handleResult2())
                                    .subscribeWith(new CommonSubscriber<Optional<EmptyDateResp>>() {
                                        @Override
                                        public void onNext(Optional<EmptyDateResp> emptyDateRespOptional) {
                                            item.collectionStatus = COLLECTION_NO;
                                            setCollect(helper, item);
                                            ToastUtils.showShort("收藏取消");

                                        }
                                    });

                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param helper
     * @param resp
     */
    private void setCollect(BaseViewHolder helper, MusicListResp resp) {

        // collectionStatus  0未收藏、1 已收藏
        helper.setImageResource(R.id.iv_collection,
                resp.collectionStatus == 0 ? R.drawable.weishoucang : R.drawable.yishoucang);
    }
}
