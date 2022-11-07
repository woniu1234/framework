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
        initStatusBar();
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

    protected <T extends ViewModel> T setViewModel(Class<T> tClass) {
        return new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(tClass);
    }

    public void initStatusBar() {
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        //沉浸顶部状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        updateStateBar(false);
    }

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

    //隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    if (v != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }


}
