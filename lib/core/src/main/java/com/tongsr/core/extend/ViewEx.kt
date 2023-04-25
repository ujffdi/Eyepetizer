package com.tongsr.core.extend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

fun TextView.setNullDrawable() {
    this.setCompoundDrawables(null, null, null, null)
}

/**
 * 设置左侧图标
 * @param resId 资源Id
 */
fun TextView.setDrawableLeft(resId: Int) {
    val drawable = ContextCompat.getDrawable(this.context, resId)
    drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(drawable, null, null, null)
}

/**
 * 设置右侧图标
 * @param resId 资源Id
 */
fun TextView.setDrawableRight(resId: Int) {
    val drawable = ContextCompat.getDrawable(this.context, resId)
    drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, null, drawable, null)
}

/**
 * 设置顶部图标
 * @param resId 资源Id
 */
fun TextView.setDrawableTop(resId: Int) {
    val drawable = ContextCompat.getDrawable(this.context, resId)
    drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, drawable, null, null)
}

/**
 * 设置底部图标
 * @param resId 资源Id
 */
fun TextView.setDrawableBottom(resId: Int) {
    val drawable = ContextCompat.getDrawable(this.context, resId)
    drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, null, null, drawable)
}

inline fun <reified V : View> ViewGroup.inflate(
    @LayoutRes layoutRes: Int,
    attachToRoot: Boolean = false
): V = LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot) as V

