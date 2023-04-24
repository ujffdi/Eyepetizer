package com.tongsr.base.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.AppUtils
import timber.log.Timber


/**
 * @author Tongsr
 * @version 1.0
 * @date 2022/11/13
 * @email ujffdtfivkg@gmail.com
 * @description 基类 Activity
 */
abstract class BaseActivity : AppCompatActivity(), IBaseView {

    protected lateinit var parentActivity: Activity
    protected lateinit var appContext: Context
    protected lateinit var mContentView: View

    private var isDebug = true

    override fun onCreate(savedInstanceState: Bundle?) {
        log("onCreate")
        parentActivity = this
        super.onCreate(savedInstanceState)
        appContext = this
        initData(intent.extras)
        setContentView()
        initView(savedInstanceState, mContentView)
        doBusiness()
    }

    /**
     * 如果当前的 Activity（singleTop 启动模式） 被复用时会回调
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // 设置为当前的 Intent，避免 Activity 被杀死后重启 Intent 还是最原先的那个
        setIntent(intent)
    }

    override fun onStart() {
        log("onStart")
        super.onStart()
    }

    override fun onResume() {
        log("onResume")
        super.onResume()
    }

    override fun onRestart() {
        log("onRestart")
        super.onRestart()
    }

    override fun onPause() {
        log("onPause")
        super.onPause()
    }

    override fun onStop() {
        log("onStop")
        super.onStop()
    }

    override fun onDestroy() {
        log("onDestroy")
        super.onDestroy()
    }

    override fun setContentView() {
        if (onBindLayout() <= 0) {
            return
        }
        mContentView = LayoutInflater.from(this).inflate(onBindLayout(), null)
        setContentView(mContentView)
    }

    protected open fun log(msg: String) {
        isDebug = AppUtils.isAppDebug()
        if (isDebug) {
            Timber.tag(TAG).d("${javaClass.simpleName}: $msg")
        }
    }

    companion object {
        private const val TAG = "ACTIVITY_LIFE"
    }

}