package com.common.common;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.common.common.loadsir.EmptyCallback;
import com.common.common.loadsir.ErrorCallback;
import com.common.common.loadsir.LoadingCallback;
import com.common.frame.ac.IBaseView;
import com.common.frame.fragment.MvvmFragment;
import com.common.frame.utils.ToastUtil;
import com.common.frame.viewmodel.MvvmBaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

public abstract class CommonFragment<V extends ViewDataBinding, VM extends MvvmBaseViewModel> extends MvvmFragment<V, VM> implements IBaseView {

    protected LoadService mLoadService;

    protected abstract View getLoadSirView();

    /***
     *   初始化参数
     */
    public abstract void initEvent();

    protected abstract void onRetryBtnClick();

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLoadSir(getLoadSirView());
        initEvent();
    }

    public void setLoadSir(View view) {
        // You can change the callback on sub thread directly.
        if (view == null) {
            return;
        }
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryBtnClick();
            }
        });
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
            if (!isShowedContent) {
                mLoadService.showCallback(ErrorCallback.class);
            } else {
                ToastUtil.show(message);
            }
        }
    }

    @Override
    public void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    private boolean isShowedContent = false;

    @Override
    public void showContent() {
        if (mLoadService != null) {
            isShowedContent = true;
            mLoadService.showSuccess();
        }
    }

}
