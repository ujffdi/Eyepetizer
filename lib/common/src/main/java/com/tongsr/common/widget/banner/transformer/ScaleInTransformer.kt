package com.tongsr.common.widget.banner.transformer

import android.view.View

/**
 * @author: tongsr
 * @date: 2023/3/14
 * @description ScaleInTransformer
 */
class ScaleInTransformer @JvmOverloads constructor(private val minScale: Float = DEFAULT_MIN_SCALE) :
    BasePageTransformer() {

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val pageHeight = view.height
        view.pivotY = (pageHeight / 2).toFloat()
        view.pivotX = (pageWidth / 2).toFloat()
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.scaleX = minScale
            view.scaleY = minScale
            view.pivotX = pageWidth.toFloat()
        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            if (position < 0) {
                //1-2:1[0,-1] ;2-1:1[-1,0]
                val scaleFactor = (1 + position) * (1 - minScale) + minScale
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
                view.pivotX = pageWidth * (DEFAULT_CENTER + DEFAULT_CENTER * -position)
            } else {
                //1-2:2[1,0] ;2-1:2[0,1]
                val scaleFactor = (1 - position) * (1 - minScale) + minScale
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
                view.pivotX = pageWidth * ((1 - position) * DEFAULT_CENTER)
            }
        } else { // (1,+Infinity]
            view.pivotX = 0f
            view.scaleX = minScale
            view.scaleY = minScale
        }
    }

    companion object {

        private const val DEFAULT_MIN_SCALE = 0.85f

    }

}