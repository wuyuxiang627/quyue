package com.game.jxj.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.game.jxj.R;
import com.game.jxj.model.entity.ArtUserReq;
import com.game.jxj.model.entity.LoginResp;
import com.game.jxj.ui.home.me.activity.EditInfoActivity;
import com.game.jxj.ui.home.me.activity.FansActivity;
import com.game.jxj.ui.home.me.activity.FollowActivity;
import com.game.jxj.ui.home.me.activity.WalletActivity;
import com.gm.utils.CacheUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author jxj
 * @date 2018/6/7
 */
public class HeaderViewMe extends LinearLayout {


    private Context mContext;

    private ViewHolder holder;

    public ArtUserReq artUserReq;

    public HeaderViewMe(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public HeaderViewMe(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public HeaderViewMe(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();

    }


    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.header_me_view, this);
        holder = new ViewHolder(view,mContext);




    }

    public void setData(ArtUserReq artUserReq) {
        this.artUserReq = artUserReq;
        if(artUserReq != null){
            holder.tvMeNickname.setText(artUserReq.nickName);
            holder.tvMeAddress.setText(!artUserReq.hometown.equals("")?artUserReq.hometown : "未知");
            if(artUserReq.sex ==0){
                holder.tvMeGender.setText("未知");
                holder.llMeGender.setBackgroundResource(R.drawable.back_no_follow);
//                holder.ivMeGender.setVisibility(View.GONE);
            }else if(artUserReq.sex ==1){
                holder.tvMeGender.setText("男");
                holder.llMeGender.setBackgroundResource(R.drawable.back_gender_man);
                holder.ivMeGender.setBackgroundResource(R.drawable.icon_gender_man);
            }else{
                holder.tvMeGender.setText("女");
                holder.llMeGender.setBackgroundResource(R.drawable.back_gender_wuman);
                holder.ivMeGender.setBackgroundResource(R.drawable.icon_gender_wuman);
            }
            holder.tvMeMoney.setText("￥ "+artUserReq.money+"");
            holder.tvMeText.setText(!artUserReq.signature.equals("")?artUserReq.signature:"暂无个性签名...");
            Glide.with(this).load(artUserReq.filePath).into(holder.circleIvAvatar);
            holder.tvMeFollow.setText(artUserReq.followHeNum);
            holder.tvMeZan.setText(artUserReq.favorNum);
            holder.tvMeMoney.setText(artUserReq.money);

        }

    }




    class ViewHolder {
        Context context;
        @BindView(R.id.tv_me_title)
        TextView tvMeTitle;
        @BindView(R.id.view_title)
        View viewTitle;
        @BindView(R.id.circle_iv_avatar)
        CircleImageView circleIvAvatar;
        @BindView(R.id.tv_me_nickname)
        TextView tvMeNickname;
        @BindView(R.id.tv_me_address)
        TextView tvMeAddress;
        @BindView(R.id.ll_me_address)
        LinearLayout llMeAddress;
        @BindView(R.id.iv_me_gender)
        ImageView ivMeGender;
        @BindView(R.id.tv_me_gender)
        TextView tvMeGender;
        @BindView(R.id.ll_me_gender)
        LinearLayout llMeGender;
        @BindView(R.id.tv_me_text)
        TextView tvMeText;
        @BindView(R.id.tv_me_follow)
        TextView tvMeFollow;
        @BindView(R.id.ll_me_follow)
        LinearLayout llMeFollow;
        @BindView(R.id.tv_me_fans)
        TextView tvMeFans;
        @BindView(R.id.ll_me_fans)
        LinearLayout llMeFans;
        @BindView(R.id.tv_me_zan)
        TextView tvMeZan;
        @BindView(R.id.ll_me_zan)
        LinearLayout llMeZan;
        @BindView(R.id.ll_me_num)
        LinearLayout llMeNum;
        @BindView(R.id.btn_tixian)
        Button btnTixian;
        @BindView(R.id.tv_me_money)
        TextView tvMeMoney;
        @BindView(R.id.linearLayout)
        RelativeLayout linearLayout;
        @BindView(R.id.view)
        View view;
        ViewHolder(View view, Context mContext) {
            ButterKnife.bind(this, view);
            this.context = mContext;
        }
        @OnClick({R.id.tv_me_nickname, R.id.ll_me_follow, R.id.ll_me_fans, R.id.ll_me_zan, R.id.btn_tixian,R.id.circle_iv_avatar})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.circle_iv_avatar:
                case R.id.tv_me_nickname:
                    EditInfoActivity.launch(context,artUserReq);
                    break;
                case R.id.ll_me_follow:
                    FollowActivity.launch(context);
                    break;
                case R.id.ll_me_fans:
                    FansActivity.launch(context);
                    break;
                case R.id.ll_me_zan:
                    break;
                case R.id.btn_tixian:
                    WalletActivity.launch(context);
                    break;
            }
        }
    }
}
