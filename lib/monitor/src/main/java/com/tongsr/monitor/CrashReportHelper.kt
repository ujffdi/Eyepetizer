package com.tongsr.monitor

import android.content.Context
import android.webkit.WebView

/**
 * @author Tongsr
 * @date 2023/5/5
 * @description 把bugly sdk的方法集中在此
 */

object CrashReportHelper {

    var crashReport: ICrashReportListener? = null

    fun initCrashReport(context: Context, isDebug: Boolean) {
        crashReport?.initCrashReport(context, isDebug)
    }

    fun setJavascriptMonitor(view: WebView, autoInject: Boolean) {
        crashReport?.setJavascriptMonitor(view, autoInject = true)
    }

    fun postCatchedException(t: Throwable) {
        crashReport?.postCatchedException(t)
    }

    fun setDeviceId(context: Context, deviceId: String) {
        crashReport?.setDeviceId(context, deviceId)
    }

}

