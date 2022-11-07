package com.common.frame.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.common.frame.viewmodel.MvvmBaseViewModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

/**
 * @author lst
 * @Description: MvvmFragment.java(类描述)
 * @date 2021/6/18
 */
public abstract class MvvmExFragment<V extends ViewBinding, VM extends MvvmBaseViewModel> extends Fragment {
    protected VM viewModel;
    protected V binding;
    private boolean isFirstLoad = true; // 是否第一次加载

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = getBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = getViewModel();
    }

    public V getBinding(@NonNull LayoutInflater inflater, ViewGroup container) {

        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<V> actualTypeArguments = (Class<V>) type.getActualTypeArguments()[0];
        try {
            return (V) actualTypeArguments.getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class).invoke(null, inflater, container, false);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public VM getViewModel() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<VM> actualTypeArguments = (Class<VM>) type.getActualTypeArguments()[1];
        return new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(actualTypeArguments);
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
