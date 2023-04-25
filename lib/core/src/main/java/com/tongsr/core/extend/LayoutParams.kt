package com.tongsr.core.extend

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams

/**
 * @author: tongsr
 * @date: 2023/3/14
 * @description View LayoutParams 设置
 */

@JvmName("updateMargin")
inline fun <reified T : ViewGroup.MarginLayoutParams> View.updateMargin(
    left: Int? = null,
    top: Int? = null,
    right: Int? = null,
    bottom: Int? = null
) {
    (layoutParams as? T)?.let { _ ->
        updateLayoutParams<ViewGroup.MarginLayoutParams> {
            left?.let {
                marginStart = left
            }

            right?.let {
                marginEnd = right
            }

            top?.let {
                topMargin = top
            }

            bottom?.let {
                bottomMargin = bottom
            }
        }
    }
}