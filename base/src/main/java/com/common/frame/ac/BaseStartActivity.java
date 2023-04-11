package com.common.frame.ac;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.common.frame.preference.SpUtil;
import com.common.frame.utils.StatusBarUtil;

public abstract class BaseStartActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
        if (SpUtil.getInstance().getBoolean("PERSONALPRIVACY", true)) {
            showPrivacyAgreement(this, this);
        } else {
            initSDK();
            enterIndex();
        }
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

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        if (which == 0) {//cancel
            onClickCancel();
            finishAffinity();
        } else if (which == 1) {//ok
            SpUtil.getInstance().setBoolean("PERSONALPRIVACY", false);
            initSDK();
            enterIndex();
        }
    }

    protected abstract void onClickCancel();

    protected abstract void initSDK();

    protected abstract void enterIndex();

    public abstract void showPrivacyAgreement(DialogInterface.OnClickListener okClick, DialogInterface.OnClickListener cancelClick);
}
