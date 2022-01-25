package com.common.common.loadsir;

import android.content.Context;
import android.view.View;

import com.common.common.R;
import com.kingja.loadsir.callback.Callback;

/**
 * @author lst
 * @Description: PlaceholderCallback.java(类描述)
 * @date 2021/6/18
 */
public class PlaceholderCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_placeholder;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
