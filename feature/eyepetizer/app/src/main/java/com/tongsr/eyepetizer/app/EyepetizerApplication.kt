package com.tongsr.eyepetizer.app

import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.navigation.DefaultNavigationViewModelDelegateFactory
import com.tongsr.base.BaseApplication
import com.tongsr.common.coil.CoilUtils

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/3
 * @email ujffdtfivkg@gmail.com
 * @description Application
 */
class EyepetizerApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(
            this,
            viewModelDelegateFactory = DefaultNavigationViewModelDelegateFactory()
        )

        CoilUtils.init(this)
    }

}