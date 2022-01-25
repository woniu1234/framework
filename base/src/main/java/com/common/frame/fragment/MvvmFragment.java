package com.common.frame.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.common.frame.viewmodel.MvvmBaseViewModel;

/**
 * @author lst
 * @Description: MvvmFragment.java(类描述)
 * @date 2021/6/18
 */
public abstract class MvvmFragment<V extends ViewDataBinding, VM extends MvvmBaseViewModel> extends Fragment {
    protected VM viewModel;
    protected V binding;
    private boolean isFirstLoad = true; // 是否第一次加载

    public abstract int getBindingVariable();

    public abstract @LayoutRes
    int getLayoutId();

    public abstract VM getViewModel();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = getViewModel();

        if (getBindingVariable() > 0) {
            binding.setVariable(getBindingVariable(), viewModel);
            binding.executePendingBindings();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            initData();
            isFirstLoad = false;
        }
    }

    protected void initData() {

    }

}
