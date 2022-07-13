package com.common.framework

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.common.frame.ac.BaseStartActivity

class StartActivity : BaseStartActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }

    override fun onClickCancel() {

    }

    override fun initSDK() {

    }

    override fun enterIndex() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun showPrivacyAgreement(
        okClick: DialogInterface.OnClickListener?,
        cancelClick: DialogInterface.OnClickListener?
    ) {
        val view = LayoutInflater.from(baseContext).inflate(R.layout.dialog_layout_privacy, null)
        val dialog = AlertDialog.Builder(this, R.style.Dialog)
            .setView(view)
            .setCancelable(false)
            .create()
        dialog.show()
        view.findViewById<View>(R.id.tv_reject)
            .setOnClickListener(DialogClick(dialog, cancelClick, 0))
        view.findViewById<View>(R.id.tv_agree).setOnClickListener(DialogClick(dialog, okClick, 1))
    }
}