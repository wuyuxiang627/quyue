package com.game.jxj.ui.home.record.fragment;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.game.jxj.R;
import com.game.jxj.ui.home.record.activity.TCVideoEditerActivity;
import com.game.jxj.ui.home.record.activity.TCVideoEditerWrapper;
import com.game.jxj.utils.TCUtils;
import com.game.jxj.widget.videotimeline.RangeSliderViewContainer;
import com.game.jxj.widget.videotimeline.VideoProgressController;
import com.game.jxj.widget.videotimeline.VideoProgressView;
import com.tencent.ugc.TXVideoEditer;

import java.util.List;

/**
 * @author jxj
 * @date 2018/7/2
 */
public class CutterFragment extends Fragment {


    private TextView mTvTip;
    private VideoProgressController mVideoProgressController;

    private long mVideoDuration;
    private long mCutterStartTime;
    private long mCutterEndTime;
    private TXVideoEditer mTXVideoEditer;
    private RangeSliderViewContainer mCutterRangeSliderView;
    private VideoProgressView mVideoProgressView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cutter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TCVideoEditerWrapper wrapper = TCVideoEditerWrapper.getInstance();
        mTXVideoEditer = wrapper.getEditer();
        mVideoDuration = wrapper.getTXVideoInfo().duration;
        mCutterStartTime = 0;
        mCutterEndTime = mVideoDuration;
        initVideoProgressLayout(view);
        initView(view);
        initListener(view);


    }

    private void initView(View view) {
        mTvTip = view.findViewById(R.id.cutter_tv_tip);
        initRangeSlider();
    }


    private void initRangeSlider() {
        mCutterRangeSliderView = new RangeSliderViewContainer(getActivity());
        mCutterRangeSliderView.init(mVideoProgressController, 0, mVideoDuration, mVideoDuration);
        mCutterRangeSliderView.setDurationChangeListener(mOnDurationChangeListener);
        mVideoProgressController.addRangeSliderView(mCutterRangeSliderView);
    }

    private void initVideoProgressLayout(View view) {
        mVideoProgressView = view.findViewById(R.id.editer_video_progress_view);
        Point point = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(point);
        int screenWidth = point.x;
        mVideoProgressView.setViewWidth(screenWidth);

        List<Bitmap> thumbnailList = TCVideoEditerWrapper.getInstance().getAllThumbnails();
        mVideoProgressView.setThumbnailData(thumbnailList);

        mVideoProgressController = new VideoProgressController(mVideoDuration);
        mVideoProgressController.setVideoProgressView(mVideoProgressView);
        mVideoProgressController.setVideoProgressSeekListener(((TCVideoEditerActivity) getActivity()).getmVideoProgressSeekListener());
        mVideoProgressController.setVideoProgressDisplayWidth(screenWidth);

    }

    private void initListener(View view) {

        view.findViewById(R.id.iv_cancel).setOnClickListener(v -> {
            getFragmentManager().beginTransaction().hide(this).commit();
        });
        view.findViewById(R.id.iv_ok).setOnClickListener(v -> {
            getFragmentManager().beginTransaction().hide(this).commit();
        });

    }


    public void setCurrentTimeMs(int timeMs) {
        if (mVideoProgressController != null) {
            mVideoProgressController.setCurrentTimeMs(timeMs);
        }

    }

    private RangeSliderViewContainer.OnDurationChangeListener mOnDurationChangeListener = new RangeSliderViewContainer.OnDurationChangeListener() {
        @Override
        public void onDurationChange(long startTime, long endTime) {
            mCutterStartTime = startTime;
            mCutterEndTime = endTime;
            if (mTXVideoEditer != null) {
                mTXVideoEditer.setCutFromTime(startTime, endTime);
            }

            mTvTip.setText(String.format("左侧 : %s, 右侧 : %s ", TCUtils.duration(startTime), TCUtils.duration(endTime)));

            TCVideoEditerWrapper.getInstance().setCutterStartTime(startTime, endTime);
        }
    };


    public long getCutterStartTime() {
        return mCutterStartTime;
    }

    public long getCutterEndTime() {
        return mCutterEndTime;
    }
}
