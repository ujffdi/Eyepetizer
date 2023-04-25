package com.tongsr.common.widget.banner.transformer

import android.view.View
import kotlin.math.abs

/**
 * @author: tongsr
 * @date: 2023/3/14
 * @description ZoomOutPageTransformer
 */
class ZoomOutPageTransformer @JvmOverloads constructor(
    private val minScale: Float = DEFAULT_MIN_SCALE,
    private val minAlpha: Float = DEFAULT_MIN_ALPHA
) : BasePageTransformer() {

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val pageHeight = view.height
        when {
            position < -1 -> { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.alpha = 0f
            }
            position <= 1 -> { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                val scaleFactor = minScale.coerceAtLeast(1 - abs(position))
                val vertMargin = pageHeight * (1 - scaleFactor) / 2
                val horzMargin = pageWidth * (1 - scaleFactor) / 2
                if (position < 0) {
                    view.translationX = horzMargin - vertMargin / 2
                } else {
                    view.translationX = -horzMargin + vertMargin / 2
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor

                // Fade the page relative to its size.
                view.alpha = minAlpha +
                        (scaleFactor - minScale) /
                        (1 - minScale) * (1 - minAlpha)
            }
            else -> { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.alpha = 0f
            }
        }
    }

    companion object {
        private const val DEFAULT_MIN_SCALE = 0.85f
        private const val DEFAULT_MIN_ALPHA = 0.5f
    }

}