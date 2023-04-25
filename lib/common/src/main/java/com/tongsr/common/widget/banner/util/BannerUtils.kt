package com.tongsr.common.widget.banner.util

import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi

/**
 * @author: tongsr
 * @date: 2023/3/14
 * @description BannerUtils
 */


/**
 * 获取真正的位置
 *
 * @param isIncrease 首尾是否有增加
 * @param position  当前位置
 * @param realCount 真实数量
 * @return position
 */
fun getRealPosition(isIncrease: Boolean, position: Int, realCount: Int): Int {
    if (!isIncrease) {
        return position
    }
    val realPosition = when (position) {
        0 -> realCount - 1
        realCount + 1 -> 0
        else -> position - 1
    }
    return realPosition
}

/**
 * 设置view圆角
 *
 * @param view view
 * @param radius radius
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
fun setBannerRound(view: View, radius: Float) {
    view.outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(0, 0, view.width, view.height, radius)
        }
    }
    view.clipToOutline = true
}