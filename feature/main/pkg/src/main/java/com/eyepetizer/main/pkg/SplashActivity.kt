package com.eyepetizer.main.pkg

import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tongsr.base.base.BaseActivity

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/3
 * @email ujffdtfivkg@gmail.com
 * @description 启动页面
 */
class SplashActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { false }
    }
    
    override fun initData(bundle: Bundle?) {
    }

    override fun onBindLayout(): Int = R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?, contentView: View) {

    }

    override fun doBusiness() {
        
    }

}