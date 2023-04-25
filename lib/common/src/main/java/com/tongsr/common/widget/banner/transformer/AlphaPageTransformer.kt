package com.tongsr.common.widget.banner.transformer

import android.view.View

/**
 * @author: tongsr
 * @date: 2023/3/14
 * @description AlphaPageTransformer
 */
class AlphaPageTransformer @JvmOverloads constructor(private val minAlpha: Float = DEFAULT_MIN_ALPHA) :
    BasePageTransformer() {

    override fun transformPage(view: View, position: Float) {
        view.scaleX = 0.999f //hack
        if (position < -1) { // [-Infinity,-1)
            view.alpha = minAlpha
        } else if (position <= 1) { // [-1,1]
            //[0，-1]
            if (position < 0) {
                //[1,min]
                val factor = minAlpha + (1 - minAlpha) * (1 + position)
                view.alpha = factor
            } else { //[1，0]
                //[min,1]
                val factor = minAlpha + (1 - minAlpha) * (1 - position)
                view.alpha = factor
            }
        } else { // (1,+Infinity]
            view.alpha = minAlpha
        }
    }

    companion object {
        private const val DEFAULT_MIN_ALPHA = 0.5f
    }
}