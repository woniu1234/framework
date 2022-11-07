package com.common.framework

import android.view.LayoutInflater
import android.view.View
import com.common.common.CommonExActivity
import com.common.framework.databinding.ActivityMainBinding

class MainActivity : CommonExActivity<ActivityMainBinding, MainViewModel>() {


    override fun initEventAndData() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, BlankFragment.newInstance("", ""))
            .commit()
    }

    override fun loginChanged() {

    }

    override fun onRetryBtnClick() {

    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    override fun getLoadSirView(): View? {
        return null
    }


}