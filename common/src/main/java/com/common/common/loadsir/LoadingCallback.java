package com.common.common.loadsir;

import android.content.Context;
import android.view.View;

import com.common.cmnpop.XPopup;
import com.common.cmnpop.impl.LoadingPopupView;
import com.common.common.R;
import com.kingja.loadsir.callback.Callback;

/**
 * @author lst
 * @Description: LoadingCallback(类描述)
 * @date 2021/6/18
 */
public class LoadingCallback extends Callback {

    private LoadingPopupView loadingPopupView;

    @Override
    protected int onCreateView() {
        return R.layout.layout_loading;
    }

    @Override
    public void onAttach(Context context, View view) {
        super.onAttach(context, view);
        XPopup.Builder builder = new XPopup.Builder(context);
        loadingPopupView = builder.dismissOnTouchOutside(false).dismissOnBackPressed(true).asLoading();
        loadingPopupView.show();
    }

    @Override
    public void onDetach() {
        loadingPopupView.dismiss();
        loadingPopupView = null;
        super.onDetach();
    }

}
