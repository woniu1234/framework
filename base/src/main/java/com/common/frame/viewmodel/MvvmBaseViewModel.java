package com.common.frame.viewmodel;

import androidx.lifecycle.ViewModel;

import com.common.frame.model.SuperBaseModel;

public abstract class MvvmBaseViewModel<M extends SuperBaseModel> extends ViewModel {

    protected M model;

    public abstract M getModel();

    protected abstract void register();

    public abstract void onDestroy();

    public MvvmBaseViewModel() {
        this.model = getModel();
        register();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (model != null) {
            onDestroy();
            model.cancel();
        }

    }
}
