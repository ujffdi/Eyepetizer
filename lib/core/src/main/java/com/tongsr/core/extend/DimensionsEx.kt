package com.tongsr.core.extend

import android.content.res.Resources
import android.util.TypedValue
import com.tongsr.core.util.Utils

/**
 * @author Tongsr
 * @version 1.0
 * @date 2021/11/24
 * @email ujffdtfivkg@gmail.com
 * @description Dimensions
 */

/*
 *    public static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
 */


inline val Int.dp: Float get() = toFloat().dp

inline val Long.dp: Float get() = toFloat().dp

inline val Double.dp: Float get() = toFloat().dp

inline val Float.dp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

inline val Int.sp: Float get() = toFloat().sp

inline val Long.sp: Float get() = toFloat().sp

inline val Double.sp: Float get() = toFloat().sp

inline val Float.sp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)

fun Int.pxToDp(): Int = toFloat().pxToDp()

fun Long.pxToDp(): Int = toFloat().pxToDp()

fun Double.pxToDp(): Int = toFloat().pxToDp()

fun Float.pxToDp(): Int =
    (this / Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun Int.pxToSp(): Int = toFloat().pxToSp()

fun Long.pxToSp(): Int = toFloat().pxToSp()

fun Double.pxToSp(): Int = toFloat().pxToSp()

fun Float.pxToSp(): Int =
    (this / Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()

fun Int.dpToPx(): Int = toFloat().dpToPx()

fun Long.dpToPx(): Int = toFloat().dpToPx()

fun Double.dpToPx(): Int = toFloat().dpToPx()

fun Float.dpToPx(): Int =
    (this * Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()

/**
 * smallestWidth 适配方案，从 dimen 文件里获取 px 值
 * 如果从 getDimension() 拿到值后转成 Dp 可调用 SizeUtils.pxToDp() 或者本类的 pxToDp()
 */
inline val Int.pixel: Float get() = Utils.getApp().resources.getDimension(this)





