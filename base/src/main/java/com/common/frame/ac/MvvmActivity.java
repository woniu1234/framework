package com.common.frame.ac;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.common.frame.common.MessageEvent;
import com.common.frame.utils.StatusBarUtil;
import com.common.frame.viewmodel.MvvmBaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class MvvmActivity<V extends ViewDataBinding, VM extends MvvmBaseViewModel> extends AppCompatActivity {

    protected VM viewModel;
    protected V binding;
    protected boolean isDark = true;
    protected Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        viewModel = getViewModel();
        initStatusBar();
        performDataBinding();
        initEventAndData();
    }

    public abstract @LayoutRes
    int getLayoutId();

    protected abstract void initEventAndData();

    protected abstract void loginChanged();

    protected abstract VM getViewModel();

    protected abstract void onRetryBtnClick();

    public abstract int getBindingVariable();

    protected <T extends ViewModel> T setViewModel(Class<T> tClass) {
        return new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(tClass);
    }

    public void initStatusBar() {
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        //沉浸顶部状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        updateStateBar(!isDark);
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

    private void performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        this.viewModel = viewModel == null ? getViewModel() : viewModel;
        if (getBindingVariable() > 0) {
            binding.setVariable(getBindingVariable(), viewModel);
        }
        binding.executePendingBindings();
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
