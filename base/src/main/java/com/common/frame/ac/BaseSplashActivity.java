package com.common.frame.ac;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.common.frame.preference.SpUtil;

public abstract class BaseSplashActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        if (SpUtil.getInstance().getBoolean("isFirstInApp", true)) {
            showPrivacyAgreement(this, this);
        } else {
            initSDK();
            enterIndex();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        if (which == 0) {//cancel
            onClickCancel();
            finishAffinity();
        } else if (which == 1) {//ok
            SpUtil.getInstance().setBoolean("isFirstInApp", false);
            initSDK();
            enterIndex();
        }
    }

    protected abstract void onClickCancel();

    protected abstract void initSDK();

    protected abstract void enterIndex();

    public abstract int getLayoutId();

    public abstract void showPrivacyAgreement(DialogInterface.OnClickListener okClick, DialogInterface.OnClickListener cancelClick);
}
