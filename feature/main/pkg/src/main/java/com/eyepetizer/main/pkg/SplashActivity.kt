package com.eyepetizer.main.pkg

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.eyepetizer.main.export.PATH_MAIN
import com.tongsr.base.base.BaseActivity
import com.tongsr.core.util.BarUtils
import com.tongsr.core.util.ThreadUtils
import com.tongsr.router.routerNavigation

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/3
 * @email ujffdtfivkg@gmail.com
 * @description 启动页面
 */
class SplashActivity: BaseActivity() {
    
    override fun initData(bundle: Bundle?) {

    }

    override fun onBindLayout(): Int = R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?, contentView: View) {
        BarUtils.setNavBarColor(this, Color.WHITE)
        BarUtils.setStatusBarColor(this, Color.WHITE)
    }

    override fun doBusiness() {
        ThreadUtils.runOnUiThreadDelayed(run, DELAY_MILLIS)
    }

    override fun onDestroy() {
        super.onDestroy()
        ThreadUtils.getMainHandler().removeCallbacks(run)
    }

    private val run = Runnable {
        routerNavigation(PATH_MAIN)
        finish()
    }

    companion object {

        private const val DELAY_MILLIS = 2000L

    }

}