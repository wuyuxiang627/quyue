package com.game.jxj.utils;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.game.jxj.R;

/**
 * @author jxj
 * @date 2018/6/12
 */
public class RadioButtonWrapper {

    private RadioGroup mRadioGroup;
    private int mCurrentPosition;

    public RadioButtonWrapper(RadioGroup mRadioGroup, int mCurrentPosition) {
        this.mRadioGroup = mRadioGroup;
        this.mCurrentPosition = mCurrentPosition;
    }


    public void setCurrentItemUI(int position) {

        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            RadioButton childView = (RadioButton) mRadioGroup.getChildAt(i);
            setTextStyle(childView, R.color.black_ff333333, false);
        }

        RadioButton currentView = (RadioButton) mRadioGroup.getChildAt(position);
        setTextStyle(currentView, R.color.black, true);

    }

    private void setTextStyle(RadioButton rbtn, int colorResId, boolean isBold) {
        rbtn.setTextColor(mRadioGroup.getContext().getColor(colorResId));
        rbtn.getPaint().setFakeBoldText(isBold);
    }


}
