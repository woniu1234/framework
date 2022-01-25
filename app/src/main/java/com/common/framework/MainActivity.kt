package com.common.framework

import android.view.View
import androidx.activity.viewModels
import com.common.common.CommonActivity
import com.common.framework.databinding.ActivityMainBinding

class MainActivity : CommonActivity<ActivityMainBinding, MainViewModel>() {

    private val vm: MainViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initEventAndData() {

    }

    override fun loginChanged() {

    }

    override fun getViewModel(): MainViewModel? {
        return vm
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