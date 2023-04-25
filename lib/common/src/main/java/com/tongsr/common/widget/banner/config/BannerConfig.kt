package com.tongsr.common.widget.banner.config

import androidx.annotation.IntDef
import com.tongsr.core.extend.dp

/**
 * @author: tongsr
 * @date: 2023/3/14
 * @description Banner 配置参数
 */

const val IS_AUTO_LOOP = true
const val IS_INFINITE_LOOP = true
const val LOOP_TIME = 3000
const val SCROLL_TIME: Float = 600F
const val INDICATOR_NORMAL_COLOR = -0x77000001
const val INDICATOR_SELECTED_COLOR = -0x78000000
const val INCREASE_COUNT = 2
val INDICATOR_NORMAL_WIDTH = 5.dp.toInt()
val INDICATOR_SELECTED_WIDTH = 7.dp.toInt()
val INDICATOR_SPACE = 5.dp.toInt()
val INDICATOR_MARGIN = 5.dp.toInt()

val INDICATOR_HEIGHT = 3.dp.toInt()
val INDICATOR_RADIUS = 3.dp.toInt()

@IntDef(Direction.LEFT, Direction.CENTER, Direction.RIGHT)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class Direction {
    companion object {
        const val LEFT = 0
        const val CENTER = 1
        const val RIGHT = 2
    }
}

