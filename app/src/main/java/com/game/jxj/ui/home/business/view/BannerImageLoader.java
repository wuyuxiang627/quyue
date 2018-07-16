package com.game.jxj.ui.home.business.view;

import android.content.Context;
import android.widget.ImageView;

import com.game.jxj.GlideApp;
import com.game.jxj.config.preference.Constants;
import com.game.jxj.model.entity.BannerListResp;
import com.youth.banner.loader.ImageLoader;

/**
 * @author jxj
 * @date 2018/4/27
 */

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        BannerListResp resp = (BannerListResp) path;
        GlideApp.with(context).load(resp.filePath).into(imageView);
    }
}
