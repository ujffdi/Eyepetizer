package com.tongsr.core.app

import android.content.Context
import android.webkit.WebView
import com.tencent.bugly.crashreport.CrashReport

/**
 * @author Tongsr
 * @date 2023/5/5
 * @description 把bugly sdk的方法集中在此
 */


fun initBugly(context: Context, isDebug: Boolean) {
    // https://bugly.qq.com/docs/user-guide/advance-features-android/?v=1.0.0
    CrashReport.initCrashReport(context, "687c9274fe", false)
    CrashReport.setIsDevelopmentDevice(context, isDebug)
}

fun setJavascriptMonitor(view: WebView) {
    CrashReport.setJavascriptMonitor(view, true)
}

fun postCatchedException(t: Throwable) {
    CrashReport.postCatchedException(t)
}

// ....