package com.gm.widget;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gm.R;
import com.wang.avi.AVLoadingIndicatorView;



/**
 * @author jxj
 * @date 2017/12/13
 */

public class LoadingDialogFragment extends AppCompatDialogFragment {


    private AVLoadingIndicatorView loadingIndicatorView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_loading, null);
        loadingIndicatorView = view.findViewById(R.id.avi);
        loadingIndicatorView.show();
        loadingIndicatorView.setBackgroundResource(R.color.black);
        return view;
    }




    @Override
    public void dismiss() {
        super.dismiss();
        loadingIndicatorView.hide();
    }
}
