package com.tongsr.base.base

import android.os.Bundle
import android.os.Handler
import android.view.View
import java.lang.Runnable
import com.blankj.utilcode.util.ThreadUtils

/**
 * @author Tongsr
 * @version 1.0
 * @date 7/30/21
 * @email ujffdtfivkg@gmail.com
 * @description 基础的方法
 */
interface IBaseView {
    /**
     * 初始化数据，可获取bundle数据或者其他数据
     *
     * @param bundle bundle
     */
    fun initData(bundle: Bundle?)

    /**
     * 绑定view layout
     *
     * @return layout id
     */
    fun onBindLayout(): Int

    /**
     * 设置View布局
     */
    fun setContentView()

    /**
     * 初始化view
     *
     * @param savedInstanceState savedInstanceState
     * @param contentView        contentView
     */
    fun initView(savedInstanceState: Bundle?, contentView: View)

    /**
     * 业务方法
     */
    fun doBusiness()

    /**
     * 在主线程执行任务
     * @param runnable runnable
     */
    fun runOnMainThread(runnable: Runnable) {
        handler.post(runnable)
    }

    /**
     * 延迟在主线程执行任务，默认1秒
     *
     * @param runnable [Runnable]
     */
    fun runOnUiThreadDelayed(runnable: Runnable) {
        runOnUiThreadDelayed(DELAY_MILLIS.toLong(), runnable)
    }

    /**
     * 延迟在主线程执行任务
     *
     * @param runnable    [Runnable]
     * @param delayMillis delay millis
     */
    fun runOnUiThreadDelayed(delayMillis: Long, runnable: Runnable) {
        handler.postDelayed(runnable, delayMillis)
    }

    private val handler: Handler
        get() = ThreadUtils.getMainHandler()

}


private const val DELAY_MILLIS = 1000
