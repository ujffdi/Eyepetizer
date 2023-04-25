package com.tongsr.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.lifecycle.LifecycleOwner
import coil.Coil
import coil.ImageLoader
import coil.decode.*
import coil.disk.DiskCache
import coil.load
import coil.memory.MemoryCache
import coil.request.*
import coil.transform.Transformation
import com.tongsr.core.extend.ifNotNull

/**
 * @author Tongsr
 * @version 1.0
 * @date 2023/2/23
 * @email ujffdtfivkg@gmail.com
 * @description 针对 Coil 的封装
 */

object CoilUtils {

    fun init(context: Context) {
        val imageLoader = ImageLoader.Builder(context)
            // 简单判断大于 Android 10 使用 ARGB_8888
//            .bitmapConfig(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .allowRgb565(true)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                // memoryCache 和 diskCache 基本和系统设置相差不大。可自定义!
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .components {
                // 自动检测格式加载，如果无法加载要显式调用
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
                add(SvgDecoder.Factory())
                add(VideoFrameDecoder.Factory())
            }
            .build()
        Coil.setImageLoader(imageLoader)
    }

}


// 不使用 inline ：会当成 CoilUtils 的静态方法。字节码：CoilUtilsKt.load()
// 使用 inline：外壳被去除，变成调用处的内部代码

/**
 * 加载图片
 *
 * @param owner activity or fragment
 * @param url url
 * @param placeholderDrawable 加载中占位图 Drawable
 * @param placeholderResId 加载中占位图 ResId
 * @param errorDrawable 失败占位图 Drawable
 * @param errorResId 失败占位图 ResId
 * @param fallbackDrawable url 为 null 时占位图 Drawable
 * @param fallbackResId url 为 null 时占位图 ResId
 * @param crossFade 淡入淡出效果 true or false
 * @param crossFadeDurationMillis crossFade 的时间
 * @param useExplicitSvgDecoder 显式调用 Svg
 * @param useExplicitVideoDecoder 显式调用 Video
 * @param transformations transformations
 * @param onStart 开始回调
 * @param onCancel 取消回调
 * @param onSuccess 成功回调
 * @param onError 失败回调
 */
@JvmOverloads
fun ImageView.loadPicture(
    url: Any?,
    owner: LifecycleOwner? = null,
    transformations: List<Transformation>? = null,
    @DrawableRes placeholderResId: Int? = null,
    placeholderDrawable: Drawable? = null,
    @DrawableRes errorResId: Int? = null,
    errorDrawable: Drawable? = null,
    @DrawableRes fallbackResId: Int? = null,
    fallbackDrawable: Drawable? = null,
    crossFade: Boolean = true,
    crossFadeDurationMillis: Int = 1000,
    useExplicitSvgDecoder: Boolean = false,
    useExplicitVideoDecoder: Boolean = false,
    onStart: (request: ImageRequest) -> Unit = {},
    onCancel: (request: ImageRequest) -> Unit = {},
    onSuccess: (request: ImageRequest, result: SuccessResult) -> Unit = { _, _ -> },
    onError: (request: ImageRequest, result: ErrorResult) -> Unit = { _, _ -> }
): Disposable = this.load(url) {
    ifNotNull(placeholderDrawable) { placeholder(it) }
    ifNotNull(placeholderResId) { placeholder(it) }
    ifNotNull(errorDrawable) { error(it) }
    ifNotNull(errorResId) { error(it) }
    ifNotNull(fallbackDrawable) { fallback(it) }
    ifNotNull(fallbackResId) { fallback(it) }
    ifNotNull(transformations) { transformations(it) }
    ifNotNull(owner) { lifecycle(it)}
    crossfade(crossFade)
    crossfade(crossFadeDurationMillis)
    // 只有无法自动检测情况下，显式调用
    // 没人会这两个值都填 true 吧？
    if (useExplicitVideoDecoder) {
        decoderFactory { result, options, _ -> VideoFrameDecoder(result.source, options) }
    }
    if (useExplicitSvgDecoder) {
        decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
    }
    listener(onStart = {
        onStart(it)
    }, onCancel = {
        onCancel(it)
    }, onSuccess = { request: ImageRequest, result: SuccessResult ->
        onSuccess(request, result)
    }, onError = { request: ImageRequest, result: ErrorResult ->
        onError(request, result)
    })
}


