package com.game.jxj.ui.home.record.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.game.jxj.R;
import com.game.jxj.ui.home.record.activity.TCVideoEditerWrapper;
import com.game.jxj.widget.BeautySettingPannel;
import com.tencent.ugc.TXVideoEditer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author jxj
 * @date 2018/7/2
 */
public class BeautyFragment extends Fragment {


    @BindView(R.id.beauty)
    BeautySettingPannel beauty;
    Unbinder unbinder;
    private TCVideoEditerWrapper editerWrapper;
    private BeautySettingPannel.BeautyParams mBeautyParams = new BeautySettingPannel.BeautyParams();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beauty, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editerWrapper = TCVideoEditerWrapper.getInstance();
        TXVideoEditer txVideoEditer = editerWrapper.getEditer();
        txVideoEditer.setBeautyFilter(mBeautyParams.mBeautyLevel, mBeautyParams.mWhiteLevel);


        beauty.setBeautyParamsChangeListener((params, key) -> {
            switch (key) {
                case BeautySettingPannel.BEAUTY_PARAM_BEAUTY:

                    mBeautyParams.mBeautyLevel = params.mBeautyLevel;
                    txVideoEditer.setBeautyFilter(mBeautyParams.mBeautyLevel, mBeautyParams.mWhiteLevel);
                    break;
                case BeautySettingPannel.BEAUTY_PARAM_WHITE:
                    mBeautyParams.mWhiteLevel = params.mWhiteLevel;
                    txVideoEditer.setBeautyFilter(mBeautyParams.mBeautyLevel, mBeautyParams.mWhiteLevel);
                    break;
                case BeautySettingPannel.BEAUTY_PARAM_RUDDY:
//                    mTXCameraRecord.setBeautyDepth(mBeautyParams.mBeautyStyle, mBeautyParams.mBeautyLevel, mBeautyParams.mWhiteLevel, mBeautyParams.mRuddyLevel);
                    break;
                case BeautySettingPannel.BEAUTY_PARAM_FILTER:
                    mBeautyParams.mFilterBmp = params.mFilterBmp;
                    txVideoEditer.setFilter(mBeautyParams.mFilterBmp);
                    break;

            }
        });
    }
}
