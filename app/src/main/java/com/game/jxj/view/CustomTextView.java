package com.game.jxj.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.game.jxj.utils.TypefaceManager;

import org.w3c.dom.Text;

/**
 * @author jxj
 * @date 2018/6/11
 */
public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        this(context,null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        try {
            this.setTypeface(TypefaceManager.getTypeface3(context));
        }catch (Exception e){
        }
    }


}
