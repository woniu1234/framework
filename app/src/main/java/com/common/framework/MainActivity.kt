package com.common.framework

import android.view.View
import androidx.lifecycle.ViewModel
import com.common.common.CommonActivity
import com.common.frame.ac.MvvmActivity
import com.common.framework.databinding.ActivityMainBinding

class MainActivity : CommonActivity<ActivityMainBinding, ViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initEventAndData() {

    }

    override fun loginChanged() {

    }

    override fun getViewModel(): ViewModel? {
        return null
    }

    override fun onRetryBtnClick() {

    }

    override fun getBindingVariable(): Int {
        return 0
    }

    override fun getLoadSirView(): View {
        return binding.root
    }
}