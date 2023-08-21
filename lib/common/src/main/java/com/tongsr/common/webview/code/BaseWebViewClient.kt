package com.tongsr.common.webview.code

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import coil.Coil
import coil.request.ImageRequest
import com.tongsr.common.webview.BaseWebView
import com.tongsr.common.webview.utils.HttpUtils
import com.tongsr.common.webview.utils.WebUtils
import kotlinx.coroutines.*
import okio.ByteString.Companion.encodeUtf8
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class BaseWebViewClient : WebViewClient() {

    companion object {

        private const val TAG = "BaseWebViewClient"

    }

    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        if (view is BaseWebView) {
            view.postBlankMonitorRunnable()
        }
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        if (view is BaseWebView) {
            view.removeBlankMonitorRunnable()
        }
    }

    /**
     * 证书校验错误
     */
    @SuppressLint("WebViewClientOnReceivedSslError")
    override fun onReceivedSslError(
        view: WebView,
        handler: SslErrorHandler,
        error: SslError
    ) {
        AlertDialog.Builder(view.context)
            .setTitle("提示")
            .setMessage("当前网站安全证书已过期或不可信\n是否继续浏览?")
            .setPositiveButton("继续浏览") { dialog, _ ->
                dialog?.dismiss()
                handler.proceed()
            }
            .setNegativeButton("返回上一页") { dialog, _ ->
                dialog?.dismiss()
                handler.cancel()
            }
            .create()
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceivedError(
        view: WebView,
        request: WebResourceRequest,
        error: WebResourceError
    ) {
        if (request.isForMainFrame) {
            onReceivedError(
                view,
                error.errorCode,
                error.description.toString(),
                request.url.toString()
            )
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "super.onReceivedError(view, errorCode, description, failingUrl)",
        "android.webkit.WebViewClient"
    )
    )
    override fun onReceivedError(
        view: WebView?,
        errorCode: Int,
        description: String?,
        failingUrl: String?
    ) {
        super.onReceivedError(view, errorCode, description, failingUrl)
    }

    override fun shouldOverrideUrlLoading(
        view: WebView,
        request: WebResourceRequest
    ): Boolean {
        return shouldOverrideUrlLoading(view, request.url.toString())
    }

    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        val scheme = Uri.parse(url).scheme ?: return false
        when (scheme) {
            "本公司 scheme", "https" -> view.loadUrl(url)
            // 处理其他协议
            //"tel" ->  {}
        }
        return true
    }

    override fun shouldInterceptRequest(
        view: WebView,
        request: WebResourceRequest
    ): WebResourceResponse? {
        Timber.tag(TAG).d("拦截资源URL：${request.url}")
        Timber.tag(TAG).d("拦截资源HEADER：${request.requestHeaders}")

        var webResourceResponse: WebResourceResponse? = null

        // 如果是 assets 目录下的文件
        if (isAssetsResource(request)) {
            webResourceResponse = assetsResourceRequest(view.context, request)
        }

        // 如果是可以缓存的文件
        if (isCacheResource(request)) {
            webResourceResponse = cacheResourceRequest(view.context, request)
        }

        if (webResourceResponse == null) {
            webResourceResponse = super.shouldInterceptRequest(view, request)
        }
        return webResourceResponse
    }

    private fun isAssetsResource(webRequest: WebResourceRequest): Boolean {
        val url = webRequest.url.toString()
        return url.startsWith("file:///android_asset/")
    }

    /**
     * assets 文件请求
     */
    private fun assetsResourceRequest(
        context: Context,
        webRequest: WebResourceRequest
    ): WebResourceResponse? {
        val url = webRequest.url.toString()
        try {
            val filenameIndex = url.lastIndexOf("/") + 1
            val filename = url.substring(filenameIndex)
            val suffixIndex = url.lastIndexOf(".")
            val suffix = url.substring(suffixIndex + 1)
            val webResourceResponse = WebResourceResponse(
                getMimeTypeFromUrl(url),
                "UTF-8",
                context.assets.open("$suffix/$filename")
            )
            webResourceResponse.responseHeaders = mapOf("access-control-allow-origin" to "*")
            return webResourceResponse
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 判断是否是可以被缓存等资源
     */
    private fun isCacheResource(webRequest: WebResourceRequest): Boolean {
        val url = webRequest.url.toString()
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        return extension == "ico" || extension == "bmp" || extension == "gif"
                || extension == "jpeg" || extension == "jpg" || extension == "png"
                || extension == "svg" || extension == "webp" || extension == "css"
                || extension == "js" || extension == "json" || extension == "eot"
                || extension == "otf" || extension == "ttf" || extension == "woff"
    }

    /**
     * 可缓存文件请求
     */
    private fun cacheResourceRequest(
        context: Context,
        webRequest: WebResourceRequest
    ): WebResourceResponse? {
        val url = webRequest.url.toString()
        val mimeType = getMimeTypeFromUrl(url)

        // WebView 中的图片利用 Coil 加载(能够和App其他页面共用缓存)
        if (isImageResource(webRequest)) {
            return try {
                // TODO webView里的图片用 Coil 加载，但是未测试该代码
                val inputStream = getInputStream(context, url)
                val webResourceResponse = WebResourceResponse(mimeType, "UTF-8", inputStream)
                webResourceResponse.responseHeaders = mapOf("access-control-allow-origin" to "*")
                webResourceResponse
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        /**
         * 其他文件缓存逻辑
         * 1.寻找缓存文件，本地有缓存直接返回缓存文件
         * 2.无缓存，下载到本地后返回
         * 注意！！！
         * 一定要确保文件下载完整，我这里采用下载完成后给文件加 "success-" 前缀的方法
         */
        val webCachePath = WebUtils.getWebViewCachePath(context)
        val cacheFilePath =
            webCachePath + File.separator + "success-" + url.encodeUtf8().md5().hex() // 自定义文件命名规则
        val cacheFile = File(cacheFilePath)
        if (!cacheFile.exists() || !cacheFile.isFile) { // 本地不存在 则开始下载
            // 下载文件
            val sourceFilePath = webCachePath + File.separator + url.encodeUtf8().md5().hex()
            val sourceFile = File(sourceFilePath)
            runBlocking {
                try {
                    HttpUtils.apiService.download(url, webRequest.requestHeaders).use {
                        it.byteStream().use { inputStream ->
                            sourceFile.writeBytes(inputStream.readBytes())
                        }
                    }
                    // 下载完成后增加 "success-" 前缀 代表文件无损 【防止io流被异常中断导致文件损坏 无法判断】
                    sourceFile.renameTo(cacheFile)
                } catch (e: Exception) {
                    e.printStackTrace()
                    // 发生异常删除文件
                    sourceFile.deleteOnExit()
                    cacheFile.deleteOnExit()
                }
            }
        }

        // 缓存文件存在则返回
        if (cacheFile.exists() && cacheFile.isFile) {
            val webResourceResponse =
                WebResourceResponse(mimeType, "UTF-8", cacheFile.inputStream())
            webResourceResponse.responseHeaders = mapOf("access-control-allow-origin" to "*")
            return webResourceResponse
        }
        return null
    }

    /**
     * 判断是否是图片
     * 有些文件存储没有后缀，也可以根据自家服务器域名等等
     */
    private fun isImageResource(webRequest: WebResourceRequest): Boolean {
        val url = webRequest.url.toString()
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        return extension == "ico" || extension == "bmp" || extension == "gif"
                || extension == "jpeg" || extension == "jpg" || extension == "png"
                || extension == "svg" || extension == "webp"
    }

    /**
     * 获取图片的流，使用 runBlocking 是不推荐的
     */
    private fun getInputStream(context: Context, url: String): InputStream {
        return runBlocking(Dispatchers.IO) {
            val bitmap = Coil.imageLoader(context).execute(
                ImageRequest.Builder(context)
                    .data(url)
                    .build()
            ).drawable?.toBitmap()
            val outputStream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream) ?: false
            val inputStream = ByteArrayInputStream(outputStream.toByteArray())
            inputStream
        }
    }

    /**
     * 根据 url 获取文件类型
     */
    private fun getMimeTypeFromUrl(url: String): String {
        try {
            val extension = MimeTypeMap.getFileExtensionFromUrl(url)
            if (extension.isNotBlank() && extension != "null") {
                if (extension == "json") {
                    return "application/json"
                }
                return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: "*/*"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "*/*"
    }

}