package com.game.jxj.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.game.jxj.R;
import com.game.jxj.ui.home.record.adapter.RecordFilterAdapter;
import com.gm.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author jxj
 * @date 2018/6/19
 */
public class BeautySettingPannel extends FrameLayout
        implements SeekBar.OnSeekBarChangeListener,
        BaseQuickAdapter.OnItemClickListener {

    private Context mContext;
    private ViewHolder mViewHolder;
    private IOnBeautyParamsChangeListener mBeautyChangeListener;
    public static final int BEAUTY_PARAM_BEAUTY = 1;
    public static final int BEAUTY_PARAM_WHITE = 2;
    public static final int BEAUTY_PARAM_RUDDY = 3;
    public static final int BEAUTY_PARAM_FILTER = 5;

    private RecordFilterAdapter mAdapter;


    public static class BeautyParams {
        //风格 例如光滑 自然 朦胧
        public int mBeautyStyle = 0;
        //美白等级
        public int mWhiteLevel = 5;
        //红润
        public int mRuddyLevel = 2;
        //磨皮
        public int mBeautyLevel = 5;
        //滤镜
        public Bitmap mFilterBmp;

    }


    public BeautySettingPannel(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public BeautySettingPannel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public BeautySettingPannel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {

        LayoutInflater.from(mContext).inflate(R.layout.beauty_pannel, this);
        mViewHolder = new ViewHolder(this);

        mViewHolder.sbBeauty.setProgress(5);
        mViewHolder.sbWhite.setProgress(5);
        mViewHolder.sbRuddy.setProgress(2);
        mViewHolder.sbBeauty.setOnSeekBarChangeListener(this);
        mViewHolder.sbWhite.setOnSeekBarChangeListener(this);
        mViewHolder.sbRuddy.setOnSeekBarChangeListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewHolder.rvFilter.setLayoutManager(linearLayoutManager);
        mAdapter = new RecordFilterAdapter(RecordFilterAdapter.RecordFilterBean.getData());
        mAdapter.setOnItemClickListener(this);
        mViewHolder.rvFilter.setAdapter(mAdapter);


    }

    public void setBeautyParamsChangeListener(IOnBeautyParamsChangeListener listener) {
        mBeautyChangeListener = listener;
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_beauty:
                if (mBeautyChangeListener != null) {
                    BeautyParams params = new BeautyParams();
                    params.mBeautyLevel = progress;
                    mBeautyChangeListener.onBeautyParamsChange(params, BEAUTY_PARAM_BEAUTY);
                }
                break;
            case R.id.sb_white:
                if (mBeautyChangeListener != null) {
                    BeautyParams params = new BeautyParams();
                    params.mWhiteLevel = progress;
                    mBeautyChangeListener.onBeautyParamsChange(params, BEAUTY_PARAM_WHITE);
                }
                break;
            case R.id.sb_ruddy:
                if (mBeautyChangeListener != null) {
                    BeautyParams params = new BeautyParams();
                    params.mRuddyLevel = progress;
                    mBeautyChangeListener.onBeautyParamsChange(params, BEAUTY_PARAM_RUDDY);
                }
                break;
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mAdapter.setSelected(position);
        if (mBeautyChangeListener != null) {
            BeautyParams params = new BeautyParams();
            params.mFilterBmp = getFilterBitmap(position);
            mBeautyChangeListener.onBeautyParamsChange(params, BEAUTY_PARAM_FILTER);
        }
    }


    private Bitmap getFilterBitmap(int index) {
        LogUtils.d(index);
        Bitmap bmp = null;
        switch (index) {
            case 1:
                bmp = decodeResource(getResources(), R.drawable.filter_white);
                break;
            case 2:
                bmp = decodeResource(getResources(), R.drawable.filter_langman);
                break;
            case 3:
                bmp = decodeResource(getResources(), R.drawable.filter_qingxin);
                break;
            case 4:
                bmp = decodeResource(getResources(), R.drawable.filter_weimei);
                break;
            case 5:
                bmp = decodeResource(getResources(), R.drawable.filter_fennen);
                break;
            case 6:
                bmp = decodeResource(getResources(), R.drawable.filter_huaijiu);
                break;
            case 7:
                bmp = decodeResource(getResources(), R.drawable.filter_landiao);
                break;
            case 8:
                bmp = decodeResource(getResources(), R.drawable.filter_qingliang);
                break;
            case 9:
                bmp = decodeResource(getResources(), R.drawable.filter_rixi);
                break;
            default:
                bmp = null;
                break;
        }

        return bmp;
    }


    private Bitmap decodeResource(Resources resources, int id) {
        TypedValue value = new TypedValue();
        resources.openRawResource(id, value);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTargetDensity = value.density;
        return BitmapFactory.decodeResource(resources, id, opts);
    }


    static class ViewHolder {
        @BindView(R.id.sb_white)
        SeekBar sbWhite;
        @BindView(R.id.sb_beauty)
        SeekBar sbBeauty;
        @BindView(R.id.sb_ruddy)
        SeekBar sbRuddy;
        @BindView(R.id.rv_filter)
        RecyclerView rvFilter;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public interface IOnBeautyParamsChangeListener {
        void onBeautyParamsChange(BeautyParams params, int key);
    }

}
