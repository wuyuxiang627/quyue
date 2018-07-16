package com.game.jxj.model.entity;

import com.game.jxj.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jxj
 * @date 2018/6/8
 */
public class MeBean {


    public int iconRes;
    public String functionName;

    public MeBean(int iconRes, String functionName) {
        this.iconRes = iconRes;
        this.functionName = functionName;
    }

    public static List<MeBean> getData() {
        List<MeBean> datas = new ArrayList<>();
        datas.add(new MeBean(R.drawable.icon_me_video, "我的视频"));
        datas.add(new MeBean(R.drawable.icon_me_challeng, "我的挑战赛"));
        datas.add(new MeBean(R.drawable.icon_me_hezuo, "合作申请"));
        datas.add(new MeBean(R.drawable.icon_me_contact, "意见反馈"));
        datas.add(new MeBean(R.drawable.iocn_me_shiming, "实名认证"));
        return datas;
    }
}
