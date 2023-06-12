package com.tongsr.monitor

import android.content.Context
import android.webkit.WebView

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/6/12
 * @email ujffdtfivkg@gmail.com
 * @description 异常上报监听
 */
interface ICrashReportListener {

    /**
     * 初始化
     *
     * @param context Context
     * @param isDebug Boolean
     */
    fun initCrashReport(context: Context, isDebug: Boolean)

    /**
     * 设置设备ID
     *
     * @param context Context
     * @param deviceId 唯一ID
     */
    fun setDeviceId(context: Context, deviceId: String)
    /**
     * 异常上报
     *
     * @param t Throwable
     */
    fun postCatchedException(t: Throwable)

    /**
     * 设置js异常上报
     *
     * @param view WebView
     * @param autoInject 是否自动注入js
     */
    fun setJavascriptMonitor(view: WebView, autoInject: Boolean)


}