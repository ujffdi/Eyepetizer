package com.tongsr.monitor

import android.content.Context
import android.webkit.WebView
import com.tencent.bugly.crashreport.CrashReport

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/6/12
 * @email ujffdtfivkg@gmail.com
 * @description Bugly 异常上报监听
 */
class BuglyHelper: ICrashReportListener {

    override fun initCrashReport(context: Context, isDebug: Boolean) {
        // https://bugly.qq.com/docs/user-guide/advance-features-android/?v=1.0.0
        CrashReport.initCrashReport(context, APP_ID, false)
        if (isDebug) {
            CrashReport.setIsDevelopmentDevice(context, true)
        }
    }

    override fun setDeviceId(context: Context, deviceId: String) {
        CrashReport.setDeviceId(context, deviceId)
    }

    override fun postCatchedException(t: Throwable) {
        CrashReport.postCatchedException(t)
    }

    override fun setJavascriptMonitor(view: WebView, autoInject: Boolean) {
        CrashReport.setJavascriptMonitor(view, true)
    }

    companion object {

        private const val APP_ID = "687c9274fe"

    }

}