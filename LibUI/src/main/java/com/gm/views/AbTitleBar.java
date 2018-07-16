/*
 * Copyright (C) 2013 www.418log.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gm.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gm.R;
import com.gm.utils.SizeUtils;
import com.gm.utils.ViewUtils;


/**
 * 描述：标题栏实现.
 *
 * @author zhaoqp
 * @version v1.0
 * @date：2013-4-24 下午3:46:47
 */
public class AbTitleBar extends RelativeLayout {


    /**
     * 标题布局.
     */
    protected LinearLayout ll_title_text;

    /**
     * 标题文字
     */
    protected TextView tv_title;

    /**
     * 默认左侧的返回
     */
    protected ImageView iv_left = null;


    /**
     * 左边的View，可以自定义显示什么.
     */
    protected LinearLayout ll_left = null;

    /**
     * 右边的View，可以自定义显示什么.
     */
    protected LinearLayout ll_right = null;


    /**
     * 全局的LayoutInflater对象，已经完成初始化.
     */
    public LayoutInflater mInflater;


    /**
     * 下拉选择.
     */
    private PopupWindow popupWindow;


    public int BUTTON_SIZE;

    private Context mContext;

    public AbTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTitleBar(context, attrs);
    }

    public AbTitleBar(Context context) {
        super(context);
        initTitleBar(context, null);

    }


    public void initTitleBar(final Context context, AttributeSet attrs) {

        mContext = context;

        BUTTON_SIZE = context.getResources().getDimensionPixelSize(R.dimen.title_bar_height);

        setBackgroundColor(context.getResources().getColor(R.color.abtitlebar_bg));

        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BUTTON_SIZE));

        mInflater = LayoutInflater.from(mContext);

        View view = mInflater.inflate(R.layout.title_bar, null);
        view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BUTTON_SIZE));

        ll_title_text = ViewUtils.find(view, R.id.ll_title_text);
        tv_title = ViewUtils.find(view, R.id.tv_title);
        ll_left = ViewUtils.find(view, R.id.ll_left);
        ll_right = ViewUtils.find(view, R.id.ll_right);

        tv_title.setTextColor(getResources().getColor(R.color.black));
        setTitleTextSize(16);
        iv_left = setLeftIcon(R.drawable.fabu_fanhui);
        iv_left.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }

            }
        });
        addView(view);

    }


    /**
     * 描述：标题栏的背景图.
     *
     * @param res 背景图资源ID
     */
    public void setTitleBarBackground(int res) {
        this.setBackgroundResource(res);
    }

    /**
     * 描述：设置标题背景.
     *
     * @param d 背景图
     */
    public void setTitleBarBackgroundDrawable(Drawable d) {
        this.setBackgroundDrawable(d);
    }

    /**
     * 描述：标题栏的背景图.
     *
     * @param color 背景颜色值
     */
    public void setTitleBarBackgroundColor(int color) {
        this.setBackgroundColor(color);
    }


    /**
     * 描述：标题文字字号.
     *
     * @param titleTextSize 文字字号
     */
    public void setTitleTextSize(int titleTextSize) {
        this.tv_title.setTextSize(titleTextSize);
    }


    /**
     * 描述：获取标题文本的Button.
     *
     * @return the title Button view
     */
    public TextView getTitleTextButton() {
        return tv_title;
    }


    /**
     * 描述：设置标题字体粗体.
     *
     * @param bold the new title text bold
     */
    public void setTitleTextBold(boolean bold) {
        TextPaint paint = tv_title.getPaint();
        if (bold) {
            //粗体
            paint.setFakeBoldText(true);
        } else {
            paint.setFakeBoldText(false);
        }

    }

    /**
     * 描述：设置标题背景.
     *
     * @param resId the new title text background resource
     */
    public void setTitleTextBackgroundResource(int resId) {
        tv_title.setBackgroundResource(resId);
    }



    /**
     * 描述：设置标题背景.
     *
     * @param drawable the new title text background drawable
     */
    public void setTitleTextBackgroundDrawable(Drawable drawable) {
        tv_title.setBackgroundDrawable(drawable);
    }

    /**
     * 描述：设置标题文本.
     *
     * @param text 文本
     */
    public TextView setTitleText(String text) {
        tv_title.setText(text);
        return tv_title;
    }

    /**
     * 描述: 设置标题文本颜色
     */
    public void setTitleTextColor(){
        tv_title.setTextColor(getResources().getColor(R.color.black));
    }

    public ImageView setImageBack(){
        iv_left = setLeftIcon(R.drawable.ic_arrow_back_black_24dp);
        return iv_left;
    }


    /**
     * 描述：设置标题文本.
     *
     * @param resId 文本的资源ID
     */
    public TextView setTitleText(int resId) {
        tv_title.setText(resId);
        return tv_title;
    }


    /**
     * 描述：设置左button
     *
     * @param resId Logo资源ID
     */
    public ImageView setLeftIcon(String text, int resId) {
//        TextView leftBtn = new TextView(mContext);
//        leftBtn.setText(text);
//        leftBtn.setVisibility(View.VISIBLE);
//        leftBtn.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
//        leftBtn.setBackgroundColor(Color.TRANSPARENT);
//        addLeftView(leftBtn);
        setLeftVisible();
        return iv_left;
    }

    public void setLeftVisible() {
        iv_left.setVisibility(View.VISIBLE);
    }


    public void setLeftGone() {
        iv_left.setVisibility(View.GONE);
    }


    private void setMinSize(View view) {
        view.setMinimumHeight(BUTTON_SIZE);
        view.setMinimumWidth(BUTTON_SIZE);
        view.setBackgroundResource(R.drawable.selector_titlebar_button);
    }


    public Button setLeftButton(String text) {
        Button button = getCommonButton();
        button.setText(text);
        addLeftView(button);
        return button;
    }

    public ImageView setLeftIcon(int drawableRes) {

        ImageView imageView = getCommonImageView();
        imageView.setImageResource(drawableRes);
        addLeftView(imageView);
        return imageView;
    }

    public ImageView getleftIconView() {
        return iv_left;

    }

    public Button setRightButton(String text,int color) {
        Button button = getCommonButton();
        button.setText(text);
        button.setTextColor(getResources().getColor(color));
        addRightView(button);
        return button;
    }

    public ImageView setRightIcon(int drawableRes) {
        ImageView imageView = getCommonImageView();
        imageView.setImageResource(drawableRes);
        addRightView(imageView);
        return imageView;
    }


    /**
     * @return 取得通用的button
     */
    private Button getCommonButton() {
        Button button = new Button(getContext());
        setMinSize(button);
        button.setTextSize(15);
        button.setGravity(Gravity.CENTER);
        button.setTextColor(getResources().getColor(R.color.white));
        return button;
    }


    private ImageView getCommonImageView() {
        ImageView imageView = new ImageView(getContext());
        setMinSize(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        return imageView;
    }


    /**
     * 描述：把指定的View填加到标题栏右边.
     *
     * @param leftView 指定的View
     */
    public void addLeftView(View leftView) {
        setMinSize(leftView);
        ll_left.setVisibility(View.VISIBLE);
        ll_left.addView(leftView);
    }

    /**
     * 描述：把指定的View填加到标题栏右边.
     *
     * @param rightView 指定的View
     */
    public void addRightView(View rightView) {
        setMinSize(rightView);
        ll_right.setVisibility(View.VISIBLE);
        ll_right.addView(rightView);
    }

    /**
     * 描述：把指定资源ID表示的View填加到标题栏右边.
     *
     * @param resId 指定的View的资源ID
     */
    public void addRightView(int resId) {
        ll_right.setVisibility(View.VISIBLE);
        ll_right.addView(mInflater.inflate(resId, null));
    }

    /**
     * 描述：清除标题栏右边的View.
     */
    public void clearRightView() {
        ll_right.removeAllViews();
    }

    /**
     * 获取这个右边的布局,可用来设置位置
     *
     * @return
     */
    public LinearLayout getRightLayout() {
        return ll_right;
    }


    /**
     * 描述：设置标题的点击事件.
     *
     * @param mOnClickListener 指定的返回事件
     */
    public void setTitleTextOnClickListener(OnClickListener mOnClickListener) {
        tv_title.setOnClickListener(mOnClickListener);
    }

    /**
     * 描述：下拉菜单的的实现方法
     *
     * @param parent
     * @param view       要显示的View
     * @param offsetMode 不填满的模式
     */
    public void showWindow(View parent, View view, boolean offsetMode) {
//        GMViewUtil.measureView(view);
        int popWidth = parent.getMeasuredWidth();
        int popMargin = (this.getMeasuredHeight() - parent.getMeasuredHeight()) / 2;
        if (view.getMeasuredWidth() > parent.getMeasuredWidth()) {
            popWidth = view.getMeasuredWidth();
        }
        if (offsetMode) {
            popupWindow = new PopupWindow(view, popWidth + 10, LayoutParams.WRAP_CONTENT, true);
        } else {
            popupWindow = new PopupWindow(view, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);
        }

        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
        popupWindow.showAsDropDown(parent, 0, popMargin + 2);
    }

    /**
     * 描述：隐藏Window
     */
    public void hideWindow() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }

    }

    /**
     * 描述：设置标题下拉的View
     *
     * @param view
     * @throws
     */
    public void setTitleTextDropDown(final View view) {
        if (view == null) {
            return;
        }
        setTitleTextOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showWindow(tv_title, view, true);
            }
        });
    }

    /**
     * 获取标题的全体布局
     *
     * @return
     */
    public LinearLayout getTitleTextLayout() {
        return ll_title_text;
    }


    @Override
    public boolean isInEditMode() {
        return true;
    }
}
