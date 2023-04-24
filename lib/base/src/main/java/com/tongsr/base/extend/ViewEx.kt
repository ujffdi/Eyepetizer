package com.tongsr.base.extend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat

/**
 * @author Tongsr
 * @version 1.0
 * @date 2022/3/21
 * @email ujffdtfivkg@gmail.com
 * @description 基础的扩展函数
 */

fun TextView.setColor(resId: Int) {
    this.setTextColor(ContextCompat.getColor(this.context, resId))
}

fun TextView.clearDrawable() {
    setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
}

/**
 * 设置左侧图标
 * @param resId 资源Id
 */
fun TextView.setDrawableLeft(@DrawableRes resId: Int) {
    val drawable = ContextCompat.getDrawable(this.context, resId)
    setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
}

/**
 * 设置右侧图标
 * @param resId 资源Id
 */
fun TextView.setDrawableRight(@DrawableRes resId: Int) {
    val drawable = ContextCompat.getDrawable(this.context, resId)
    setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
}

/**
 * 设置顶部图标
 * @param resId 资源Id
 */
fun TextView.setDrawableTop(@DrawableRes resId: Int) {
    val drawable = ContextCompat.getDrawable(this.context, resId)
    setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null)
}

/**
 * 设置底部图标
 * @param resId 资源Id
 */
fun TextView.setDrawableBottom(@DrawableRes resId: Int) {
    val drawable = ContextCompat.getDrawable(this.context, resId)
    setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, drawable)
}

inline fun <reified V : View> ViewGroup.inflate(
    @LayoutRes layoutRes: Int,
    attachToRoot: Boolean = false
): V = LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot) as V

