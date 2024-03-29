package com.common.common;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.viewbinding.ViewBinding;

import com.common.common.loadsir.EmptyCallback;
import com.common.common.loadsir.ErrorCallback;
import com.common.common.loadsir.LoadingCallback;
import com.common.frame.ac.IBaseView;
import com.common.frame.ac.MvvmActivity;
import com.common.frame.ac.MvvmExActivity;
import com.common.frame.common.MessageEvent;
import com.common.frame.viewmodel.MvvmBaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class CommonExActivity<V extends ViewBinding, VM extends MvvmBaseViewModel> extends MvvmExActivity<V, VM> implements IBaseView {

    protected LoadService mLoadService;

    protected abstract View getLoadSirView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateStateBar(true);
        EventBus.getDefault().register(this);
        setLoadSir(getLoadSirView());
        initEventAndData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void setLoadSir(View view) {
        if (view == null) {
            return;
        }
        // You can change the callback on sub thread directly.
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryBtnClick();
            }
        });
        showContent();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(MessageEvent messageEvent) {

    }

    @Override
    public void onRefreshEmpty() {
        if (mLoadService != null) {
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        if (mLoadService != null) {
            mLoadService.showCallback(ErrorCallback.class);
        }
    }

    @Override
    public void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void showContent() {
        if (mLoadService != null) {
            mLoadService.showSuccess();
        }
    }


}
