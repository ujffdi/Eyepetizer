package com.eyepetizer.main.pkg

import android.app.Application
import android.content.Context
import com.tencent.bugly.crashreport.CrashReport
import com.therouter.app.flowtask.lifecycle.FlowTask
import com.therouter.flow.TheRouterFlowTask
import com.tongsr.common.CoilUtils
import com.tongsr.common.webview.TemplateWebViewPool
import com.tongsr.common.webview.WebViewPool
import com.tongsr.core.CrashReportingTree
import com.tongsr.core.util.AppUtils
import com.tongsr.core.util.CrashUtils
import com.tongsr.core.util.LogUtils
import com.tongsr.core.util.Utils
import com.tongsr.data.local.datastore.LocalStorageManager
import timber.log.Timber
import java.lang.Integer.min


/**
 * @author Tongsr
 * @version 1.0
 * @date 2023/2/3
 * @email ujffdtfivkg@gmail.com
 * @description web init
 */

// TODO 初始化任务类

@FlowTask(
    taskName = "initMain",
    dependsOn = TheRouterFlowTask.BEFORE_THEROUTER_INITIALIZATION,//当应用启动后，在TheRouter初始化之前，执行任务
)
fun initMain(context: Context) {
    initUtils(context)

    initLog(context)

    initCoilUtils(context)

    initCrashUtils()

    LocalStorageManager.init(context as Application)

    initBugly(context)
}

private fun initBugly(context: Context) {
    // https://bugly.qq.com/docs/user-guide/advance-features-android/?v=1.0.0
    CrashReport.initCrashReport(context, "687c9274fe", false)
    CrashReport.setIsDevelopmentDevice(context, BuildConfig.DEBUG)
}

private fun initCoilUtils(context: Context) {
    CoilUtils.init(context)
}

private fun initCrashUtils() {
    CrashUtils.init {
        //TODO 异常信息上报
        CrashReport.postCatchedException(it.throwable)
        AppUtils.relaunchApp(true)
    }
}

private fun initUtils(context: Context) {
    Utils.init(context as Application?)
}

private fun initLog(context: Context) {
    // 线上环境不开启 log。开发时使用
    val config = LogUtils.getConfig()
    config.isLogSwitch = BuildConfig.DEBUG

    // Timber 用于 log 埋点
    if (BuildConfig.DEBUG) {
        Timber.plant(Timber.DebugTree())
    } else {
        Timber.plant(CrashReportingTree())
    }
}

@FlowTask(
    taskName = "initWeb",
    dependsOn = TheRouterFlowTask.APP_ONSPLASH,//当应用的首个Activity.onCreate()执行后初始化
)
fun initWeb(context: Context) {
    // 根据手机 CPU 核心数（或者手机内存等条件）设置缓存池容量
    WebViewPool.getInstance().setMaxPoolSize(min(Runtime.getRuntime().availableProcessors(), 3))
    WebViewPool.getInstance().init(context)

    // 加载本地模板用的 WebView 复用池
    TemplateWebViewPool.getInstance().setMaxPoolSize(min(Runtime.getRuntime().availableProcessors(), 3))
    TemplateWebViewPool.getInstance().init(context)
}


