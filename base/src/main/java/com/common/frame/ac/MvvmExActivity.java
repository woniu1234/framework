package com.common.frame.ac;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.common.frame.utils.StatusBarUtil;
import com.common.frame.viewmodel.MvvmBaseViewModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

public abstract class MvvmExActivity<V extends ViewBinding, VM extends MvvmBaseViewModel> extends AppCompatActivity {

    protected VM viewModel;
    protected V binding;
    protected Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        binding = getViewBinding();
        setContentView(binding.getRoot());
        viewModel = getViewModel();
        updateStateBar(false);
    }

    protected V getViewBinding() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<V> actualTypeArguments = (Class<V>) type.getActualTypeArguments()[0];
        try {
            return (V) actualTypeArguments.getMethod("inflate", LayoutInflater.class).invoke(null, LayoutInflater.from(this));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract void initEventAndData();

    protected abstract void loginChanged();

    protected VM getViewModel() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<VM> actualTypeArguments = (Class<VM>) type.getActualTypeArguments()[1];
        return new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(actualTypeArguments);
    }

    protected abstract void onRetryBtnClick();

    /**
     * 修改状态颜色
     *
     * @param isBlack 字体颜色
     */
    public void updateStateBar(boolean isBlack) {
        if (isBlack) {
            StatusBarUtil.statusBarLightMode(this);
        } else {
            StatusBarUtil.statusBarDarkMode(this);
        }
    }

}
