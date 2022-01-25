package com.common.framework

import com.common.frame.viewmodel.MvvmBaseViewModel
import com.common.framework.MainModel
import com.common.frame.model.SuperBaseModel.IBaseModelListener
import com.common.framework.MainDataT

class MainViewModel : MvvmBaseViewModel<MainModel?>(), IBaseModelListener<MainDataT?> {
    override fun getModel(): MainModel {
        return MainModel()
    }

    override fun register() {
        model?.register(this)
    }

    override fun onDestroy() {
        model?.unRegister(this)
    }
}