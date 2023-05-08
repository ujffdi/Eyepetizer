package com.tongsr.common.webview.code

import android.app.AlertDialog
import android.webkit.ConsoleMessage
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.tongsr.core.extend.ifNotNull
import com.tongsr.monitor.CrashReportHelper
import timber.log.Timber

class BaseWebChromeClient : WebChromeClient() {

    companion object {

        private const val TAG = "BaseWebChromeClient"

    }

    /**
     * 网页控制台输入日志
     */
    override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
        Timber.tag(TAG).d("onConsoleMessage() -> ${consoleMessage.message()}")
        return super.onConsoleMessage(consoleMessage)
    }

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        // TODO 增加Javascript异常监控
        ifNotNull(view) {
            CrashReportHelper.setJavascriptMonitor(it)
        }
        super.onProgressChanged(view, newProgress)
    }

    /**
     * 网页警告弹框
     */
    override fun onJsAlert(
        view: WebView,
        url: String,
        message: String,
        result: JsResult
    ): Boolean {
        AlertDialog.Builder(view.context)
            .setTitle("警告")
            .setMessage(message)
            .setPositiveButton("确认") { dialog, _ ->
                dialog?.dismiss()
                result.confirm()
            }
            .setNegativeButton("取消") { dialog, _ ->
                dialog?.dismiss()
                result.cancel()
            }
            .create()
            .show()
        return true
    }

    /**
     * 网页弹出确认弹窗
     */
    override fun onJsConfirm(
        view: WebView,
        url: String,
        message: String,
        result: JsResult
    ): Boolean {
        AlertDialog.Builder(view.context)
            .setTitle("警告")
            .setMessage(message)
            .setPositiveButton("确认") { dialog, _ ->
                dialog?.dismiss()
                result.confirm()
            }
            .setNegativeButton("取消") { dialog, _ ->
                dialog?.dismiss()
                result.cancel()
            }
            .create()
            .show()
        return true
    }

}